package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.CurrentSeason;
import com.mojang.blaze3d.systems.RenderSystem;
import journeymap.client.JourneymapClient;
import journeymap.client.api.display.Context;
import journeymap.client.api.model.TextProperties;
import journeymap.client.properties.InGameMapProperties;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.component.Label;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.Theme.Minimap.MinimapSpec;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
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


    private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);
    static final JourneymapClient jm = JourneymapClient.getInstance();

/*    public class SeasonLab extends ThemeLabelSource {
        public static ThemeLabelSource.InfoSlot SEASONNAME = create(MODID, "Season", 100L, 100L, CurrentSeason::getSeasonName);
    }*/


    public static boolean jouneymapLoaded () {return ModList.get().isLoaded("journeymap");}

    public static final IGuiOverlay JOURNEYMAP_SEASON = (ForgeGui, seasonStack, partialTick, scaledWidth, scaledHeight) -> {
        JourneyMap map = new JourneyMap();
        if (jouneymapLoaded()) {
            MINIMAP_TEXT_LIST.clear();

//            boolean biome = FTBChunksClientConfig.MINIMAP_BIOME.get();
//            boolean xyz = FTBChunksClientConfig.MINIMAP_XYZ.get();
//
//            int i = 0;
//
//            if(biome){
//                i++;
//            }
//            if(xyz){
//                i++;
//            }

            //Season
            MINIMAP_TEXT_LIST.add(Component.literal(getSeasonName()));


            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(MODID,
                    "textures/season/" + getSeasonLower() + ".png");

            //Values
            Font fontRenderer = mc.font;
            int height = mc.getWindow().getHeight();
            int width = mc.getWindow().getWidth();
            double guiSize = mc.getWindow().getGuiScale();
            MinimapSpec minimapSpec;
            MiniMapProperties miniMapProperties;


            int minimapSize = jm.getActiveMiniMapProperties().getSize();

            boolean fontShadow = map.hasFontShadow();
            float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
            float alpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
            Theme.LabelSpec label = new Theme.LabelSpec();

            float x = jm.getActiveMiniMapProperties().positionX.get();
            float y = jm.getActiveMiniMapProperties().positionY.get();

            int labelHeight = (int)((double)(DrawUtil.getLabelHeight(fontRenderer, label.shadow)) * (fontScale));

            /*Todo
                * Need to find margins and frame size for map
                    * Also margin from map to first
                * Need to make int for how many labels are displayed currently (x out of 4)
                    * labelHeight should be correct size
                * Setup if statement for each of top left and top right (on the drawShadow)
                * Add icon again next to season
                * Add background alpha color (float alpha?)

             */

            if (!MINIMAP_TEXT_LIST.isEmpty()) {
                seasonStack.pushPose();
                //seasonStack.translate(0,0,0);
                seasonStack.scale(fontScale/(float)guiSize, fontScale/(float)guiSize, 1.0F);
                for (Component s : MINIMAP_TEXT_LIST) {
                    mc.font.drawShadow(seasonStack, String.valueOf(scaledHeight), (float) ((width)-(minimapSize/2)), (float) ((0)+(minimapSize)), -1);
                }
                int iconDim = mc.font.lineHeight;
                int offsetDim = 1;
                MINIMAP_TEXT_LIST.clear();


                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, SEASON);
                //DrawUtil.drawImage(seasonStack,SEASON,);
                seasonStack.popPose();
            }

        }
    };
}

