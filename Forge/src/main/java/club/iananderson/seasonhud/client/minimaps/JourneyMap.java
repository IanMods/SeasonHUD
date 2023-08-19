package club.iananderson.seasonhud.client.minimaps;

import com.mojang.blaze3d.systems.RenderSystem;
import journeymap.client.JourneymapClient;
import journeymap.client.io.ThemeLoader;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;

import static club.iananderson.seasonhud.config.Config.journeyMapAboveMap;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.loadedMinimap;
import static club.iananderson.seasonhud.impl.minimaps.HiddenMinimap.minimapHidden;
import static club.iananderson.seasonhud.impl.sereneseasons.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.impl.sereneseasons.CurrentSeason.getSeasonResource;

public class JourneyMap {
    public static final IGuiOverlay JOURNEYMAP_SEASON = (ForgeGui, seasonStack, partialTick, scaledWidth, scaledHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        ArrayList<Component> MINIMAP_TEXT_SEASON= getSeasonName();

        if (loadedMinimap("journeymap")) {
            DisplayVars vars = UIManager.INSTANCE.getMiniMap().getDisplayVars();

            JourneymapClient jm = JourneymapClient.getInstance();
            Font fontRenderer = mc.font;

            String emptyLabel = "jm.theme.labelsource.blank";
            String info1Label = jm.getActiveMiniMapProperties().info1Label.get();
            String info2Label = jm.getActiveMiniMapProperties().info2Label.get();
            String info3Label = jm.getActiveMiniMapProperties().info3Label.get();
            String info4Label = jm.getActiveMiniMapProperties().info4Label.get();

            float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
            float stringWidth = fontRenderer.width(MINIMAP_TEXT_SEASON.get(0));
            float guiSize = (float) mc.getWindow().getGuiScale();

            int minimapHeight = vars.minimapHeight;
            int halfHeight = minimapHeight / 2;

            Theme.LabelSpec currentTheme = ThemeLoader.getCurrentTheme().minimap.square.labelBottom;
            int labelColor = currentTheme.background.getColor();
            int textColor = currentTheme.foreground.getColor();
            float labelAlpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
            float textAlpha = currentTheme.foreground.alpha;
            int frameWidth = ThemeLoader.getCurrentTheme().minimap.square.right.width/2;
            boolean fontShadow = currentTheme.shadow;

            double labelHeight = ((DrawUtil.getLabelHeight(fontRenderer, fontShadow)) * (fontScale));

            int infoLabelCount = 0;
            if (journeyMapAboveMap.get()){
                infoLabelCount = 1;

                if (!info1Label.equals(emptyLabel)) {
                    infoLabelCount++;
                }
                if (!info2Label.equals(emptyLabel)) {
                    infoLabelCount++;
                }
            }
            else{
                if (!info3Label.equals(emptyLabel)) {
                    infoLabelCount++;
                }
                if (!info4Label.equals(emptyLabel)) {
                    infoLabelCount++;
                }
            }
            int vPad = (int)(((labelHeight/fontScale) - 8) / 2.0);
            double bgHeight = (labelHeight * infoLabelCount) + (vPad) + frameWidth;

            //Values
            if ((mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen) && !mc.isPaused() && jm.getActiveMiniMapProperties().enabled.get()) {
                seasonStack.pushPose();
                seasonStack.scale(1 / guiSize, 1 / guiSize, 1.0F);

                //Icon
                int iconDim = (int) (mc.font.lineHeight*fontScale);
                double labelPad = 2*fontScale;

                double textureX = vars.centerPoint.getX();
                double textureY = vars.centerPoint.getY();
                double translateY = (journeyMapAboveMap.get() ? -1 : 1)*(halfHeight + bgHeight +(fontScale > 1.0 ? 0.0 : journeyMapAboveMap.get() ? -0.5 : 0.5)+ (journeyMapAboveMap.get() ? -labelPad : labelPad));

                double labelWidth = stringWidth*fontScale;
                double labelX = (textureX);
                double labelY = (textureY + translateY);

                double totalRectWidth = labelWidth + (2*labelPad);
                double labelRectX = (float)(labelX-(Math.max(1.0,totalRectWidth)/2)-(fontScale > 1.0 ? 0.0 : 0.5)); //basically half the label width from the center
                double labelRectY = labelY-(fontScale > 1.0 ? 0.0 : 0.5)-labelPad;

                double labelIconX = (float)(textureX - totalRectWidth / 2.0 - (fontScale > 1.0 ? 0.0 : 0.5)+(1.5*labelPad)); //half the label width
                double labelIconY = labelY;

                DrawUtil.drawRectangle(seasonStack,labelRectX,labelRectY,totalRectWidth,labelHeight,labelColor,labelAlpha); //Rectangle for the icon

                for (Component s : MINIMAP_TEXT_SEASON) {
                    DrawUtil.drawLabel(seasonStack, s.getString(), labelX, labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, 0, textColor, textAlpha, fontScale, fontShadow); //No touchy. Season label offset by icon+padding
                }

                ResourceLocation SEASON = getSeasonResource();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, SEASON);
                GuiComponent.blit(seasonStack,(int)(labelIconX),(int)(labelIconY),0,0,iconDim,iconDim,iconDim,iconDim);
                seasonStack.popPose();
            }
        }
    };
}