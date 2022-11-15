package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.CurrentSeason;
import com.mojang.blaze3d.systems.RenderSystem;
import journeymap.client.JourneymapClient;
import journeymap.client.properties.InGameMapProperties;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.component.Label;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

import static club.iananderson.seasonhud.CurrentSeason.getSeasonLower;
import static club.iananderson.seasonhud.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.SeasonHUD.MODID;
import static club.iananderson.seasonhud.SeasonHUD.mc;


public class JourneyMap {
    public static boolean jouneymapLoaded () {return ModList.get().isLoaded("journeymap");}
    private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);
    static final JourneymapClient jm = JourneymapClient.getInstance();

    public class SeasonLab extends ThemeLabelSource {
        public static ThemeLabelSource.InfoSlot SEASONNAME = create(MODID, "Season", 100L, 100L, CurrentSeason::getSeasonName);
    }
    public static final IGuiOverlay JOURNEYMAP_SEASON = (ForgeGui, seasonStack, partialTick, width, height) -> {
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
            double fontScale = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties().fontScale.get();
            float alpha = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties().infoSlotAlpha.get();
            //Theme.LabelSpec labelSpec = Theme.Minimap.MinimapSpec;
            //int labelHeight = UIManager.INSTANCE.getMiniMap().getDisplayVars().getInfoLabelAreaHeight(fontRenderer,Theme.LabelSpec labelSpec);

            /* ^^^^^^^Need to make this work^^^^^^ */

            if (!MINIMAP_TEXT_LIST.isEmpty()) {
                seasonStack.pushPose();
                seasonStack.translate(0,0,0);
                seasonStack.scale(1.0F, 1.0F, 1.0F);
                for (Component s : MINIMAP_TEXT_LIST) {
                    mc.font.drawShadow(seasonStack, s, (float) 10, (float) 10, -1);
                }
                int iconDim = mc.font.lineHeight;
                int offsetDim = 1;
                MINIMAP_TEXT_LIST.clear();
///Need to get icon to display next to infoslot

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, SEASON);
                //DrawUtil.drawImage(seasonStack,SEASON,);
                seasonStack.popPose();
            }

        }
    };
}

