//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client.minimaps;

import club.iananderson.seasonhud.impl.minimaps.XaeroInfoDisplays;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.lwjgl.opengl.GL11;
import com.mojang.blaze3d.platform.GlStateManager;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.gui.IScreenBase;
import xaero.common.minimap.info.InfoDisplay;
import xaero.common.minimap.info.InfoDisplayManager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.loadedMinimap;
import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.*;
import static club.iananderson.seasonhud.impl.minimaps.XaeroInfoDisplays.SEASON;
import static club.iananderson.seasonhud.impl.opac.OpenPartiesAndClaims.inClaim;
import static xaero.common.minimap.info.BuiltInInfoDisplays.*;
import static xaero.common.settings.ModOptions.modMain;

public class XaeroMinimap implements HudRenderCallback {
    public static XaeroMinimap HUD_INSTANCE;
    private boolean needDisableBlend = false;

    public static void init()
    {
        HUD_INSTANCE = new XaeroMinimap();
        HudRenderCallback.EVENT.register(HUD_INSTANCE);
    }

    private void enableAlpha(float alpha)
    {
        needDisableBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    private void disableAlpha(float alpha)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (needDisableBlend)
            RenderSystem.disableBlend();
    }

    @Override
    public void onHudRender(PoseStack seasonStack, float alpha)
    {
        Minecraft mc = Minecraft.getInstance();
        ArrayList<Component> underText = getSeasonName();

        if (loadedMinimap("xaerominimap") || loadedMinimap("xaerominimapfair")) {
            //Data
            ResourceLocation dim = Objects.requireNonNull(mc.level).dimension().location();
            int chunkX = mc.player.chunkPosition().x;
            int chunkZ = mc.player.chunkPosition().z;
            double currentDimScale = mc.level.dimensionType().coordinateScale();

            InfoDisplayManager manager = modMain.getInterfaces().getMinimapInterface().getInfoDisplayManager();


            boolean overworldCoordState = OVERWORLD_COORDINATES.getState() && currentDimScale != 1.0;
            boolean weatherState = WEATHER.getState() && (mc.level.isRaining() || mc.level.isThundering());
            boolean highlightsState = HIGHLIGHTS.getState() && inClaim(dim,chunkX,chunkZ);
            boolean lightOverlayState = LIGHT_OVERLAY_INDICATOR.getState();
            boolean manualCaveModeState = MANUAL_CAVE_MODE_INDICATOR.getState();
            boolean customSubWorldState = CUSTOM_SUB_WORLD.getState();
            boolean seasonState = SEASON.getState();


            float mapSize = modMain.getSettings().getMinimapSize(); //Minimap Size
            double scale = mc.getWindow().getGuiScale();

            float minimapScale = modMain.getSettings().getMinimapScale();
            float mapScale = ((float) (scale / (double) minimapScale));
            float fontScale = 1 / mapScale;

            float x = XaeroMinimapCore.currentSession.getModMain().getInterfaces().getMinimapInterface().getX();
            float y = XaeroMinimapCore.currentSession.getModMain().getInterfaces().getMinimapInterface().getY();
            int framesize = (int) (4);


            float scaledX = (x * mapScale);
            float scaledY = (y * mapScale);

            // Need to fix the icon getting shifter with the InfoDisplays that can disappear, like HIGHLIGHT

            int ySeason = manager.getStream().toList().indexOf(SEASON);
            int disabledOptions = manager.getStream()
                    .filter(s -> !s.getState().equals(0))
                    .filter(s -> !s.getState().equals(false))
                    .toList() .indexOf(SEASON);
            //System.out.println(disabledOptions);

            if(highlightsState){
                disabledOptions -= 1;
            }

            //Icon
            float stringWidth = mc.font.width(underText.get(0));
            float stringHeight = (mc.font.lineHeight);

            int iconDim = (int) stringHeight;
            int yOffset = (int) (((framesize * 2) + 9) + (disabledOptions * (stringHeight+1)));


            int height = mc.getWindow().getHeight();


            int size = (int) mapSize;
            int scaledHeight = (int)((float)height * mapScale);
            int align = modMain.getSettings().minimapTextAlign;
            boolean under = (scaledY + size / 2) < scaledHeight / 2;

            int stringX = (int) (scaledX + (align == 0 ? size / 2 - stringWidth / 2 + iconDim + 1: (align == 1 ? 6 : iconDim + size - stringWidth + framesize )));
            int stringY = (int) ((scaledY) + (under ? size + yOffset: -9));

            if ((!modMain.getSettings().hideMinimapUnderScreen || mc.screen == null || mc.screen instanceof IScreenBase || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
                    && (!modMain.getSettings().hideMinimapUnderF3 || !mc.options.renderDebug)) {
                enableAlpha(alpha);
                RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
                seasonStack.pushPose();
                seasonStack.scale(fontScale, fontScale, 1.0F);

                //Icon
                ResourceLocation SEASON = getSeasonResource();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, SEASON);
                GuiComponent.blit(seasonStack, (int) (stringX), (int) stringY, 0, 0, iconDim, iconDim, iconDim, iconDim);
                seasonStack.popPose();
                RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
                disableAlpha(alpha);
            }
        }

    }
}


