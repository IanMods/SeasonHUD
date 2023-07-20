//package club.iananderson.seasonhud.client.minimaps;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import journeymap.client.JourneymapClient;
//import journeymap.client.io.ThemeLoader;
//import journeymap.client.render.draw.DrawUtil;
//import journeymap.client.ui.UIManager;
//import journeymap.client.ui.minimap.DisplayVars;
//import journeymap.client.ui.theme.Theme;
//import journeymap.common.properties.config.StringField;
//import net.minecraft.client.Minecraft;
//import club.iananderson.seasonhud.config.Location;
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
//import net.minecraft.client.gui.Font;
//import net.minecraft.client.gui.GuiComponent;
//import net.minecraft.client.gui.screens.Screen;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import org.lwjgl.opengl.GL11;
//
//
//import java.util.ArrayList;
//
//import static club.iananderson.seasonhud.config.ModConfig.*;
//import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.noMinimap;
//import static club.iananderson.seasonhud.impl.fabricseasons.Calendar.calendar;
//import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.*;
//
//import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.loadedMinimap;
//
//
//public class JourneyMap implements HudRenderCallback{
//    public static JourneyMap HUD_INSTANCE;
//    private boolean needDisableBlend = false;
//
//    public static void init()
//    {
//        HUD_INSTANCE = new JourneyMap();
//        HudRenderCallback.EVENT.register(HUD_INSTANCE);
//    }
//
//    private void enableAlpha(float alpha)
//    {
//        needDisableBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
//        RenderSystem.enableBlend();
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//    }
//
//    private void disableAlpha(float alpha)
//    {
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        if (needDisableBlend)
//            RenderSystem.disableBlend();
//    }
//
//    @Override
//    public void onHudRender(PoseStack seasonStack, float alpha)
//    {
//        ArrayList<Component> MINIMAP_TEXT_SEASON= getSeasonName();
//        Minecraft mc = Minecraft.getInstance();
//        Font font = mc.font;
//
//        if (loadedMinimap("journeymap")) {
//            Theme.LabelSpec label = new Theme.LabelSpec();
//            DisplayVars vars = UIManager.INSTANCE.getMiniMap().getDisplayVars();
//
//            JourneymapClient jm = JourneymapClient.getInstance();
//            Font fontRenderer = font;
//            int getLabelHeight = 9 + 6;
//
//            String emptyLabel = "jm.theme.labelsource.blank";
//            String info3Label = jm.getActiveMiniMapProperties().info3Label.getLabel();
//            String info4Label = jm.getActiveMiniMapProperties().info4Label.getLabel();
//
//            float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
//            float guiSize = (float) mc.getWindow().getGuiScale();
//
//            boolean fontShadow = label.shadow;
//
//            double labelHeight = ((DrawUtil.getLabelHeight(fontRenderer, fontShadow)) * (fontScale));
//            double labelWidth = font.width(MINIMAP_TEXT_SEASON.get(0))*fontScale;
//
//            int minimapHeight = vars.minimapHeight;
//            //int minimapWidth = vars.minimapWidth;
//
//            int halfHeight = minimapHeight / 2;
//            //int halfWidth = minimapWidth / 2;
//
//            Theme.LabelSpec currentTheme = ThemeLoader.getCurrentTheme().minimap.square.labelBottom;
//            int labelColor = currentTheme.background.getColor();
//            int textColor = currentTheme.foreground.getColor();
//            float labelAlpha = currentTheme.background.alpha;
//            float textAlpha = currentTheme.foreground.alpha;
//            int frameWidth = ThemeLoader.getCurrentTheme().minimap.square.right.width/2;
//
//            int infoLabelCount = 0;
//            if (!info3Label.equals(emptyLabel)) {infoLabelCount++;}
//            if (!info4Label.equals(emptyLabel)) {infoLabelCount++;}
//
//            int vPad = (int)(((labelHeight/fontScale) - 8) / 2.0);
//            double bgHeight = (labelHeight * infoLabelCount) + (vPad) + frameWidth;
//
//            //Values
//            if (!mc.isPaused()) {
//                enableAlpha(alpha);
//                RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
//                seasonStack.pushPose();
//
//                seasonStack.scale(1 / guiSize, 1 / guiSize, 1.0F);
//
//                //Icon
//                int iconDim = (int) (mc.font.lineHeight*fontScale);
//                double labelPad = 1*fontScale;
//                double totalIconSize = (iconDim+(labelPad));
//
//                double textureX = vars.centerPoint.getX();
//                double textureY = vars.centerPoint.getY();
//                double translateX = totalIconSize/2;
//                double translateY = halfHeight + bgHeight+(fontScale < 1.0 ? 0.5 : 0.0);
//
//                double labelX = (textureX + translateX);
//                double labelY = (textureY + translateY);
//
//                double totalRectWidth = labelWidth+totalIconSize;
//                double iconRectX = (float)(textureX-Math.max(1.0,totalRectWidth)/2-(fontScale > 1.0 ? 0.0 : 0.5));
//                //basically half the label width from the center
//
//                //double labelIconX = textureX;
//                double labelIconX = (float)(textureX - totalRectWidth / 2.0 - (fontScale > 1.0 ? 0.0 : 0.5));
//                //half the label width
//                double labelIconY = labelY+(labelHeight/2)-(iconDim/2.0);
//                //moves the icon to  the vertical center of the label
//
//                for (Component s : MINIMAP_TEXT_SEASON) {
//                    DrawUtil.drawLabel(seasonStack, s.getString(), labelX, labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, labelAlpha, textColor, textAlpha, fontScale, fontShadow);
//                    //No touchy. Season label offset by icon+padding
//                }
//                DrawUtil.drawRectangle(seasonStack,iconRectX-(2*labelPad),labelY,totalRectWidth-labelWidth,labelHeight,labelColor,labelAlpha);
//                //Rectangle for the icon
//
//                ResourceLocation SEASON = getSeasonResource();
//                RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//                RenderSystem.setShaderTexture(0, SEASON);
//                GuiComponent.blit(seasonStack,(int)(labelIconX),(int)(labelIconY),0,0,iconDim,iconDim,iconDim,iconDim);
//                //DrawUtil.drawImage(seasonStack, 0,labelX,labelY,false,fontScale,0);
//                seasonStack.popPose();
//                RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
//                disableAlpha(alpha);
//            }
//        }
//    };
//}