package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.CurrentSeason;
import com.mojang.blaze3d.systems.RenderSystem;
import journeymap.client.JourneymapClient;
import journeymap.client.api.display.Context;
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
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.Theme.Minimap.MinimapSpec;
import journeymap.client.ui.theme.ThemeLabelSource;
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
    static final JourneymapClient jm = JourneymapClient.getInstance();


    public static boolean jouneymapLoaded () {return ModList.get().isLoaded("journeymap");}

    public static final IGuiOverlay JOURNEYMAP_SEASON = (ForgeGui, seasonStack, partialTick, scaledWidth, scaledHeight) -> {
        JourneyMap map = new JourneyMap();
        if (jouneymapLoaded()) {
            //INIMAP_TEXT_LIST.clear();

            //Season
            String[] MINIMAP_TEXT_LIST = new String[3];
            MINIMAP_TEXT_LIST[0] = getSeasonName();

            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(MODID,
                    "textures/season/" + getSeasonLower() + ".png");

            //Values
            Font fontRenderer = mc.font;
            MinimapSpec minimapSpec;
            MiniMapProperties miniMapProperties;


            int minimapSize = jm.getActiveMiniMapProperties().getSize();

            boolean fontShadow = map.hasFontShadow();
            float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
            float alpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
            Theme.LabelSpec label = new Theme.LabelSpec();
            int labelColor = ThemeLoader.getCurrentTheme().fullscreen.statusLabel.highlight.getColor();
            int textColor = ThemeLoader.getCurrentTheme().minimap.square.bottom.getColor();

            double guiSize = mc.getWindow().getGuiScale();
            int width = mc.getWindow().getWidth();
            int height = mc.getWindow().getHeight();


            float posX = jm.getActiveMiniMapProperties().positionX.get();
            float posY = jm.getActiveMiniMapProperties().positionY.get();


            float x = width*posX;
            float y = height*posY;


            int infoLabelCount = 2;//Will be count of how many labels are currently on

            int labelHeight = (int)((double)(DrawUtil.getLabelHeight(fontRenderer, label.shadow)) * (fontScale));
            int labelWidth = mc.font.width(Arrays.toString(MINIMAP_TEXT_LIST));
            double vpad = fontRenderer.isBidirectional() ? 0.0 : (fontShadow ? 6.0 : 4.0);
            double bgHeight = (labelHeight * infoLabelCount)+(vpad/2)+1;//1 is to offset for lack of background alpha in size

            MINIMAP_TEXT_LIST[1] = String.valueOf(labelHeight);
            MINIMAP_TEXT_LIST[2] = String.valueOf(vpad);

            float labelX = (x+((float)minimapSize/2));
            float labelY = (y+minimapSize)+(float)(bgHeight);




            if (!(MINIMAP_TEXT_LIST.length == 0)) {
                seasonStack.pushPose();
                //seasonStack.translate(0,0,0);
                seasonStack.scale(fontScale/(float)guiSize, fontScale/(float)guiSize, 1.0F);
                //for (Component s : MINIMAP_TEXT_LIST) {
                //mc.font.drawShadow(seasonStack, Arrays.toString(MINIMAP_TEXT_LIST), labelX, labelY, -1);
                DrawUtil.drawLabel(seasonStack, Arrays.toString(MINIMAP_TEXT_LIST), (double)labelX, (double)labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, alpha, textColor, alpha, fontScale, fontShadow);
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

        }
    };
}

/*Todo
 * Need to make int for how many labels are displayed currently (x out of 4)
 * Setup if statement for each of top left and top right (on the drawShadow)
 * Add icon again next to season
 * incorporate "scale" for if minimap size is changed?
 * Add background alpha color (float alpha?)
 * Need to figure out how to get color from theme????

 */