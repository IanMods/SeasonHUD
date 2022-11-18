package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.CurrentSeason;
import com.mojang.blaze3d.systems.RenderSystem;
import journeymap.client.JourneymapClient;
import journeymap.client.api.display.Context;
import journeymap.client.api.impl.ClientAPI;
import journeymap.client.api.model.TextProperties;
import journeymap.client.cartography.color.ColorManager;
import journeymap.client.cartography.color.ColorPalette;
import journeymap.client.io.ThemeLoader;
import journeymap.client.properties.InGameMapProperties;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.component.Label;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.minimap.Position;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.Theme.Minimap.MinimapSpec;
import journeymap.client.ui.theme.ThemeLabelSource;
import journeymap.client.ui.theme.ThemeMinimapFrame;
import journeymap.client.ui.theme.impl.FlatTheme;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static club.iananderson.seasonhud.CurrentSeason.getSeasonLower;
import static club.iananderson.seasonhud.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.SeasonHUD.MODID;
import static club.iananderson.seasonhud.SeasonHUD.mc;


public class JourneyMap extends TextProperties {
    protected EnumSet<Context.UI> activeUIs;
    protected EnumSet<Context.MapType> activeMapTypes;
    protected float scale;
    protected int color;
    protected int backgroundColor;
    protected float opacity;
    protected float backgroundOpacity;
    protected boolean fontShadow;
    protected int minZoom;
    protected int maxZoom;
    protected int offsetX;
    protected int offsetY;


    //private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);



    public static boolean jouneymapLoaded () {return ModList.get().isLoaded("journeymap");}

    public static final IGuiOverlay JOURNEYMAP_SEASON = (ForgeGui, seasonStack, partialTick, scaledWidth, scaledHeight) -> {
        if (jouneymapLoaded()) {
            String[] MINIMAP_TEXT_LIST = new String[1];
            JourneyMap map = new JourneyMap();
            Theme.LabelSpec label = new Theme.LabelSpec();
            Theme theme = new Theme();

            JourneymapClient jm = JourneymapClient.getInstance();
            Font fontRenderer = mc.font;
            Position positionName = jm.getActiveMiniMapProperties().position.get();
            MinimapSpec minimapSpec = theme.minimap.square;

            float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
            float guiSize = (float)mc.getWindow().getGuiScale();
            float alpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
            float posX = jm.getActiveMiniMapProperties().positionX.get();
            float posY = jm.getActiveMiniMapProperties().positionY.get();

            int infoLabelCount = 4; //Will be count of how many labels are currently on eventually
            int labelColor = ThemeLoader.getCurrentTheme().fullscreen.statusLabel.highlight.getColor();
            int labelWidth = mc.font.width(Arrays.toString(MINIMAP_TEXT_LIST))-2;
            int labelHeight = (int)((double)(DrawUtil.getLabelHeight(fontRenderer, label.shadow)) * (fontScale));

            int minimapSize,minimapWidth,minimapHeight;
                minimapSize = minimapWidth = minimapHeight = jm.getActiveMiniMapProperties().getSize();

            int halfHeight, halfWidth;
                halfHeight = halfWidth = minimapHeight/2;

            int marginX,marginY;
                marginX = marginY = 0;

            int textColor = ThemeLoader.getCurrentTheme().minimap.square.bottom.getColor();
            int screenWidth = mc.getWindow().getWidth();
            int screenHeight = mc.getWindow().getHeight();
            int textureX = 0;
            int textureY = 0;
            int translateX = 0;
            int translateY = 0;
            int centerX = 0;

            boolean fontShadow = map.hasFontShadow();

            double vPad = fontRenderer.isBidirectional() ? 0.0 : (fontShadow ? 6.0 : 4.0);
            double bgHeight = (labelHeight * infoLabelCount)+(vPad/2)+1;//1 is to offset for lack of background alpha in size


            MiniMapProperties miniMapProperties = new MiniMapProperties(jm.getActiveMinimapId());
            //ThemeMinimapFrame minimapFrame = new ThemeMinimapFrame(theme, minimapSpec, miniMapProperties, minimapWidth, minimapHeight);

            //double frameWidth = minimapFrame.getWidth();
            //double frameBorder = frameWidth-minimapWidth;

            //Season
            //MINIMAP_TEXT_LIST[0] = getSeasonName();



            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(MODID,
                    "textures/season/" + getSeasonLower() + ".png");


            //Values

            if (!minimapSpec.labelBottomInside) {
                marginY += labelHeight;
            }

            seasonStack.pushPose();
            //seasonStack.translate(0,0,0);
            seasonStack.scale(fontScale/(float)guiSize, fontScale/(float)guiSize, 1.0F);


           if(positionName == Position.TopRight) {
               if (!minimapSpec.labelTopInside) {
                   marginY = Math.max(marginY, labelHeight + 2);
                   marginX = 7;
               }
               textureX = screenWidth;
               textureY = 0;
               translateX = halfWidth + marginX;
               translateY = minimapHeight + marginY + (int) bgHeight;


               float labelX = textureX-translateX;
               float labelY = textureY + translateY;

               DrawUtil.drawLabel(seasonStack, Arrays.toString(MINIMAP_TEXT_LIST), labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);
           }
           else if(positionName == Position.TopLeft){
               if(!minimapSpec.labelTopInside) {
                   marginY = Math.max(marginY, labelHeight + 2);
                   marginX = 7;
               }
               textureX = 0;
               textureY = 0;
               translateX = halfWidth + marginX;
               translateY = minimapHeight + marginY+(int)bgHeight;

               float labelX = textureX+translateX;
               float labelY = textureY+translateY;

               DrawUtil.drawLabel(seasonStack, Arrays.toString(MINIMAP_TEXT_LIST), labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);
           }
           else if (positionName==Position.Custom) {
               textureX = (int)(screenWidth*posX);
               textureY = (int)(screenHeight*posY);
               translateX = halfWidth;
               translateY = minimapSize + (int)bgHeight - (2*labelHeight);



               float labelX = textureX+translateX;
               float labelY = textureY+translateY;


               DrawUtil.drawLabel(seasonStack, Arrays.toString(MINIMAP_TEXT_LIST), labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);

           }

           int startY = textureY + minimapHeight;
                startY += minimapSpec.labelBottomInside ? -minimapSpec.margin - labelHeight : minimapSpec.margin;


            //DrawUtil.drawLabel(seasonStack, Arrays.toString(MINIMAP_TEXT_LIST), labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);


            // }
            int iconDim = mc.font.lineHeight;
            int offsetDim = 1;
            //MINIMAP_TEXT_LIST.clear();


            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, SEASON);
            //DrawUtil.drawImage(seasonStack,SEASON,);
            seasonStack.popPose();
        }
    };
}

/*Todo
 * Issue with errors filling log
 * Need to make int for how many labels are displayed currently (x out of 4)
 * Setup if statement for each of top left and top right (on the drawShadow)
 * Add icon again next to season
 * incorporate "scale" for if minimap size is changed?
 * Add background alpha color (float alpha?)
 * Need to figure out how to get color from theme????

 */