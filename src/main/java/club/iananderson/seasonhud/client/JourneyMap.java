package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.systems.RenderSystem;
import journeymap.client.JourneymapClient;
import journeymap.client.api.display.Context;
import journeymap.client.api.model.TextProperties;
import journeymap.client.io.ThemeLoader;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.MiniMap;
import journeymap.client.ui.minimap.Position;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.Theme.Minimap.MinimapSpec;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.EnumSet;

import static club.iananderson.seasonhud.CurrentSeason.getSeasonLower;
import static club.iananderson.seasonhud.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.SeasonHUD.MODID;
import static club.iananderson.seasonhud.SeasonHUD.mc;


public class JourneyMap extends TextProperties {
    protected EnumSet<Context.UI> activeUIs;
    protected EnumSet<Context.MapType> activeMapTypes;
     protected float scale = getScale();

    //private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);

    public static boolean jouneymapLoaded () {return ModList.get().isLoaded("journeymap");}

    public static final IGuiOverlay JOURNEYMAP_SEASON = (ForgeGui, seasonStack, partialTick, scaledWidth, scaledHeight) -> {
        if (jouneymapLoaded()) {
            JourneyMap map = new JourneyMap();
            Theme.LabelSpec label = new Theme.LabelSpec();
            Theme theme = new Theme();
            TextProperties text = new TextProperties();


            JourneymapClient jm = JourneymapClient.getInstance();
            Font fontRenderer = mc.font;
            MiniMap minimap = UIManager.INSTANCE.getMiniMap();
            Position positionName = jm.getActiveMiniMapProperties().position.get();
            MinimapSpec minimapSpec = theme.minimap.square;


            String MINIMAP_TEXT_SEASON = getSeasonName();

            float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
            float compassScale = jm.getActiveMiniMapProperties().compassFontScale.get();
            float guiSize = (float)mc.getWindow().getGuiScale();
            float alpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
            float posX = jm.getActiveMiniMapProperties().positionX.get();
            float posY = jm.getActiveMiniMapProperties().positionY.get();

            int infoLabelCount = 4; //Will be count of how many labels are currently on eventually
            int labelColor = ThemeLoader.getCurrentTheme().fullscreen.statusLabel.highlight.getColor();
            int labelWidth = mc.font.width((MINIMAP_TEXT_SEASON))-2;
            int labelHeight = (int)((DrawUtil.getLabelHeight(fontRenderer, label.shadow)) * (fontScale));

            int minimapSize = jm.getActiveMiniMapProperties().getSize();
            int minimapWidth = minimap.getDisplayVars().minimapWidth;
            int minimapHeight = minimap.getDisplayVars().minimapHeight;
            double centerPointX = minimap.getDisplayVars().centerPoint.getX();
            double centerPointY = minimap.getDisplayVars().centerPoint.getY();

            int halfHeight = minimapHeight/2;
            int halfWidth = minimapWidth/2;


            int marginX = minimap.getDisplayVars().marginX;
            int marginY = minimap.getDisplayVars().marginY;


            int textColor = ThemeLoader.getCurrentTheme().minimap.square.bottom.getColor();
            int screenWidth = mc.getWindow().getWidth();
            int screenHeight = mc.getWindow().getHeight();
            int textureX = 0;
            int textureY = 0;
            int translateX = 0;
            int translateY = 0;


            boolean fontShadow = map.hasFontShadow();

            double vPad = fontRenderer.isBidirectional() ? 0.0 : (fontShadow ? 6.0 : 4.0);
            double bgHeight = (labelHeight * infoLabelCount)+(vPad)+(1*fontScale);//1 is to offset for lack of background alpha in size


            MiniMapProperties miniMapProperties = new MiniMapProperties(jm.getActiveMinimapId());
            //ThemeMinimapFrame minimapFrame = new ThemeMinimapFrame(theme, minimapSpec, miniMapProperties, minimapWidth, minimapHeight);

            //double frameWidth = minimapFrame.getWidth();
            //double frameBorder = frameWidth-minimapWidth;

            //Season
            //String MINIMAP_TEXT_SEASON = getSeasonName();

            //String MINIMAP_TEXT_SEASON = String.valueOf(fontScale);

            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(MODID,
                    "textures/season/" + getSeasonLower() + ".png");


            //Values

            if (!minimapSpec.labelBottomInside) {
                marginY += labelHeight;
            }

            seasonStack.pushPose();
            //seasonStack.translate(0,0,0);
            //seasonStack.scale(1/fontScale*guiSize, 1/fontScale*guiSize, 1.0F);
            seasonStack.scale(1/(float)guiSize, 1/(float)guiSize, 1.0F);


           if(positionName == Position.TopRight) {
               textureX = screenWidth;
               textureY = 0;
               translateX = halfWidth + marginX;
               translateY = minimapHeight + marginY + (int) bgHeight;


               float labelX = textureX-translateX;
               float labelY = textureY + translateY;

               DrawUtil.drawLabel(seasonStack, MINIMAP_TEXT_SEASON, labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);
           }
           else if(positionName == Position.TopLeft){
               textureX = 0;
               textureY = 0;
               translateX = halfWidth + marginX;
               translateY = minimapHeight + marginY+(int)bgHeight;

               float labelX = textureX+translateX;
               float labelY = textureY+translateY;

               DrawUtil.drawLabel(seasonStack, MINIMAP_TEXT_SEASON, labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);
           }
           else if (positionName==Position.Custom) {
//               textureX = (int)(screenWidth*posX);
//               textureY = (int)(screenHeight*posY);
//               translateX = halfWidth;
//               translateY = minimapSize + (int)bgHeight - (2*labelHeight);

               textureX = (int)centerPointX;
               textureY = (int)centerPointY;
               translateX = 0;
               translateY = halfHeight + (int)bgHeight - (2*labelHeight);


               float labelX = textureX+translateX;
               float labelY = textureY+translateY;


               DrawUtil.drawLabel(seasonStack, MINIMAP_TEXT_SEASON, labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);

           }

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
 * Test only one formula
 * Need to make int for how many labels are displayed currently (x out of 4)
 * Setup if statement for each of top left and top right (on the drawShadow)
 * Add icon again next to season
 * Add background alpha color (float alpha?)
 * Need to figure out how to get color from theme????

 */