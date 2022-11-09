package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import club.iananderson.seasonhud.config.SeasonHUDClientConfigs;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.MinimapPosition;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FTBChunks extends FTBChunksClient {
    public static boolean ftbChunksLoaded() {
        return ModList.get().isLoaded("ftbchunks");
    }
    private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList(3);

    public void renderHUD(PoseStack matrixStack, float tickDelta) {
        Minecraft mc = Minecraft.getInstance();
        if (ftbChunksLoaded()) {
            //Season
            Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
            String seasonCap = currentSeason.name();
            String seasonLower = seasonCap.toLowerCase();
            String seasonName = seasonLower.substring(0, 1).toUpperCase() + seasonLower.substring(1);


            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                    "textures/season/" + seasonLower + ".png");

            if (mc.player != null && mc.level != null && MapManager.inst != null) {
                double guiScale = mc.getWindow().getGuiScale();
                int ww = mc.getWindow().getGuiScaledWidth();
                int wh = mc.getWindow().getGuiScaledHeight();
                MapDimension dim = MapDimension.getCurrent();
                if (dim != null) {
                    if (dim.dimension != mc.level.dimension()) {
                        MapDimension.updateCurrent();
                        dim = MapDimension.getCurrent();
                    }

                    if (!mc.options.renderDebug && (Boolean) FTBChunksClientConfig.MINIMAP_ENABLED.get() && (Integer) FTBChunksClientConfig.MINIMAP_VISIBILITY.get() != 0 && !(Boolean) FTBChunksWorldConfig.FORCE_DISABLE_MINIMAP.get()) {
                        float scale = (float) ((Double) FTBChunksClientConfig.MINIMAP_SCALE.get() * 4.0 / guiScale);
                        float minimapRotation = ((Boolean) FTBChunksClientConfig.MINIMAP_LOCKED_NORTH.get() ? 180.0F : -mc.player.getYRot()) % 360.0F;
                        int s = (int) (64.0 * (double) scale);
                        double s2d = (double) s / 2.0;
                        float s2f = (float) s / 2.0F;
                        MinimapPosition minimapPosition = (MinimapPosition) FTBChunksClientConfig.MINIMAP_POSITION.get();
                        int x = minimapPosition.getX(ww, s);
                        int y = minimapPosition.getY(wh, s);
                        int z = 0;
                        int offsetX = (Integer) FTBChunksClientConfig.MINIMAP_OFFSET_X.get();
                        int offsetY = (Integer) FTBChunksClientConfig.MINIMAP_OFFSET_Y.get();

                        int i;
                        double distance;
                        float iconScale;


                        MinimapPosition.MinimapOffsetConditional offsetConditional = (MinimapPosition.MinimapOffsetConditional) FTBChunksClientConfig.MINIMAP_POSITION_OFFSET_CONDITION.get();

                        if (offsetConditional.isNone() || offsetConditional.getPosition() == minimapPosition) {
                            x += minimapPosition.posX == 0 ? offsetX : -offsetX;
                            y -= minimapPosition.posY > 1 ? offsetY : -offsetY;
                        }
                        if ((Boolean)FTBChunksClientConfig.MINIMAP_BIOME.get()) {

                            MINIMAP_TEXT_LIST.add(Component.literal(seasonName));
                        }
                        if (!MINIMAP_TEXT_LIST.isEmpty()) {
                            matrixStack.pushPose();
                            matrixStack.translate((double)x + s2d, (double)(y + s) + 3.0, 0.0);
                            matrixStack.scale((float)(0.5 * (double)scale), (float)(0.5 * (double)scale), 1.0F);

                            for(i = 0; i < MINIMAP_TEXT_LIST.size(); ++i) {
                                FormattedCharSequence bs = ((Component)MINIMAP_TEXT_LIST.get(i)).getVisualOrderText();
                                int bsw = mc.font.width(bs);
                                mc.font.drawShadow(matrixStack, bs, (float)(-bsw) / 2.0F, (float)(i * 11), -1);
                            }

                            matrixStack.popPose();
                        }
                    }
                }
            }
        }
    }
}
