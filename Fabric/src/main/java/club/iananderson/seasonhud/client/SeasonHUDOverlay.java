package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.config.Location;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static club.iananderson.seasonhud.config.ModConfig.INSTANCE;
import static club.iananderson.seasonhud.impl.fabricseasons.Calendar.calendar;
import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonResource;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.noMinimap;


//HUD w/ no minimap installed
public class SeasonHUDOverlay implements HudRenderCallback{

    public static SeasonHUDOverlay HUD_INSTANCE;
    private boolean needDisableBlend = false;

    public static void init()
    {
        HUD_INSTANCE = new SeasonHUDOverlay();
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
        ArrayList<Component> seasonName = getSeasonName();

        float guiSize = (float) mc.getWindow().getGuiScale();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();


        int xOffset = (int) (INSTANCE.hudX / guiSize);
        int yOffset = (int) ((INSTANCE.hudY) / guiSize);
        int x = 1;
        int y = 1;

        Font font = mc.font;
        int stringHeight = (font.lineHeight);
        int iconDim = stringHeight-1;
        int offsetDim = 2;

        int stringWidth = font.width(seasonName.get(0)) + iconDim + offsetDim;// might need to take offsetDim out

        if (noMinimap() && INSTANCE.enableMod && calendar()) {
            Location hudLoc = INSTANCE.hudLocation;
            switch (hudLoc) {
                case TOP_LEFT -> {
                    x = offsetDim;
                    y = 0;
                }
                case TOP_CENTER -> {
                    x = screenWidth / 2 - stringWidth / 2;
                    y = 0;
                }
                case TOP_RIGHT -> {
                    x = screenWidth - stringWidth - offsetDim;
                    y = 0;
                }
                case BOTTOM_LEFT -> {
                    x = offsetDim;
                    y = screenHeight - iconDim - (2*offsetDim);
                }
                case BOTTOM_RIGHT -> {
                    x = screenWidth - stringWidth - offsetDim;
                    y = screenHeight - iconDim - (2*offsetDim);
                }
            }

            enableAlpha(alpha);
            RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
            seasonStack.pushPose();

            seasonStack.scale(1F, 1F, 1F);

            //Text
            int iconX = x + xOffset;
            int iconY = y + yOffset+offsetDim;
            float textX = (iconX + iconDim + offsetDim);
            float textY = iconY;//(iconY+(iconDim-stringHeight)); //double check this is exact
            font.drawShadow(seasonStack, seasonName.get(0),textX, textY, 0xffffffff);

            //Icon
            ResourceLocation SEASON = getSeasonResource();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, SEASON);
            GuiComponent.blit(seasonStack, iconX, iconY, 0, 0, iconDim, iconDim, iconDim, iconDim);
            seasonStack.popPose();
            RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
            disableAlpha(alpha);
        }
    }
}
