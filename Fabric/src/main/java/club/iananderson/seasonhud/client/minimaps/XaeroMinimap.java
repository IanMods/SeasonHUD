//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client.minimaps;

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
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import com.mojang.blaze3d.platform.GlStateManager;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.gui.IScreenBase;

import java.util.ArrayList;

import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.loadedMinimap;
import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.*;
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

        if (loadedMinimap("xaerominimap")) { //|| loadedMinimap("xaerominimapfair")) {
            //Data
            float mapSize = XaeroMinimapCore.currentSession.getModMain().getSettings().getMinimapSize();//Minimap Size

            double scale = mc.getWindow().getGuiScale();

            float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getMinimapScale();
            float mapScale = ((float) (scale / (double) minimapScale));
            float fontScale = 1 / mapScale;

            int padding = 9;

            float x = XaeroMinimapCore.currentSession.getModMain().getInterfaces().getMinimapInterface().getX();
            float y = XaeroMinimapCore.currentSession.getModMain().getInterfaces().getMinimapInterface().getY();
            float halfSize = mapSize / 2;

            float scaledX = (x * mapScale);
            float scaledY = (y * mapScale);

            boolean xBiome = BIOME.getState(); //Xaero Alpha Changes
            boolean xDim = DIMENSION.getState();
            boolean xCoords = COORDINATES.getState();
            boolean xAngles = ANGLES.getState();
            boolean xWeather = WEATHER.getState();
            int xLight = LIGHT_LEVEL.getState();
            int xTime = TIME.getState();

            int trueCount = 0;
            if (xBiome) {trueCount++;}
            if (xDim) {trueCount++;}
            if (xCoords) {trueCount++;}
            if (xAngles) {trueCount++;}
            if (xWeather) {trueCount++;}
            if (xLight > 0) {trueCount++;}
            if (xTime > 0) {trueCount++;}

            //Icon
            float stringWidth = mc.font.width(underText.get(0));
            float stringHeight = (mc.font.lineHeight)+1 ;

            int iconDim = (int) stringHeight-2;
            int offsetDim = 1;

            float totalWidth = (stringWidth + iconDim + offsetDim);

            int align = XaeroMinimapCore.currentSession.getModMain().getSettings().minimapTextAlign;

            float height = mc.getWindow().getHeight();
            float scaledHeight = (height * mapScale);
            boolean under = scaledY + mapSize / 2 < scaledHeight / 2;

            float center = (float) (padding - 0.5 + halfSize + iconDim + offsetDim - totalWidth / 2);
            float left = 6 + iconDim;
            float right = (int) (mapSize + 2 + padding - stringWidth);

            float stringX = scaledX + (align == 0 ? center : (align == 1 ? left : right));
            float stringY = scaledY + (under ? mapSize + (2 * padding) : -9) + (trueCount * stringHeight * (under ? 1 : -1));

            if ((!modMain.getSettings().hideMinimapUnderScreen || mc.screen == null || mc.screen instanceof IScreenBase || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
                    && (!modMain.getSettings().hideMinimapUnderF3 || !mc.options.renderDebug)) {

                enableAlpha(alpha);
                RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
                seasonStack.pushPose();

                seasonStack.scale(fontScale, fontScale, 1.0F);

                //Font
                for (Component s : underText) {
                    mc.font.drawShadow(seasonStack, s, stringX, stringY, -1);
                }

                underText.clear();

                //Icon
                ResourceLocation SEASON = getSeasonResource();

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, SEASON);
                GuiComponent.blit(seasonStack, (int) (stringX - iconDim - offsetDim), (int) stringY, 0, 0, iconDim, iconDim, iconDim, iconDim);
                seasonStack.popPose();
                RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
                disableAlpha(alpha);
            }
        }

    };
}


