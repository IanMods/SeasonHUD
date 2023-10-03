package club.iananderson.seasonhud.client.minimaps;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.MinimapPosition;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftbteams.api.Team;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonResource;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.loadedMinimap;
import static club.iananderson.seasonhud.impl.minimaps.HiddenMinimap.minimapHidden;


public class FTBChunks implements HudRenderCallback {
    public static FTBChunks HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new FTBChunks();
        HudRenderCallback.EVENT.register(HUD_INSTANCE);
    }

    @Override
    public void onHudRender(GuiGraphics seasonStack, float alpha) {
        Minecraft mc = Minecraft.getInstance();
        List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);
        int i = 0;

        if (loadedMinimap("ftbchunks")) {
            ChunkPos currentPlayerPos = Objects.requireNonNull(mc.player).chunkPosition();
            MapDimension dim = MapDimension.getCurrent().get();
            MapRegionData data = Objects.requireNonNull(dim).getRegion(XZ.regionFromChunk(currentPlayerPos)).getData();

            boolean biome = FTBChunksClientConfig.MINIMAP_BIOME.get();
            boolean xyz = FTBChunksClientConfig.MINIMAP_XYZ.get();
            boolean claimed = FTBChunksClientConfig.MINIMAP_ZONE.get();

            if (data != null) {
                Team team = Objects.requireNonNull(data).getChunk(XZ.of(currentPlayerPos)).getTeam().orElse((Team) null);
                if (team != null && claimed) {
                    i++;
                }
            }

            if (biome) {
                i++;
            }

            if (xyz) {
                i++;
            }

            //Season
            MINIMAP_TEXT_LIST.add(getSeasonName().get(0));

            if (!minimapHidden() && (mc.player != null && mc.level != null && !MapManager.getInstance().isEmpty() && !MapDimension.getCurrent().isEmpty())) {
                double guiScale = mc.getWindow().getGuiScale();
                int ww = mc.getWindow().getGuiScaledWidth();
                int wh = mc.getWindow().getGuiScaledHeight();

                if (dim != null) {
                    if (dim.dimension != mc.level.dimension()) {
                        MapDimension.getCurrent();
                    }

                    if (!mc.options.renderDebug && FTBChunksClientConfig.MINIMAP_ENABLED.get() && FTBChunksClientConfig.MINIMAP_VISIBILITY.get() != 0 && !(Boolean) FTBChunksWorldConfig.FORCE_DISABLE_MINIMAP.get()) {
                        float scale;
                        if ((Boolean) FTBChunksClientConfig.MINIMAP_PROPORTIONAL.get()) {
                            scale = (float) (4.0 / guiScale);
                            scale = (float) ((double) scale * (double) ((float) ww / 10.0F) / ((double) scale * 64.0) * (Double) FTBChunksClientConfig.MINIMAP_SCALE.get());
                        } else {
                            scale = (float) ((Double) FTBChunksClientConfig.MINIMAP_SCALE.get() * 4.0 / guiScale);
                        }
                        int s = (int) (64.0 * (double) scale);
                        float s1 = Math.max(1.0F, (float) Math.round(scale)) / 2.0F;
                        double halfSizeD = (double) s / 2.0;
                        float halfSizeF = (float) s / 2.0F;
                        MinimapPosition minimapPosition = FTBChunksClientConfig.MINIMAP_POSITION.get();
                        int x = minimapPosition.getX(ww, s);
                        int y = minimapPosition.getY(wh, s);
                        int offsetX = FTBChunksClientConfig.MINIMAP_OFFSET_X.get();
                        int offsetY = FTBChunksClientConfig.MINIMAP_OFFSET_Y.get();

                        float textHeight = (float) (9 + 2) * i + 1 * s1;
                        float yOff = (float) (y + s) + textHeight >= (float) wh ? -textHeight : (float) s + 2.0F;


                        MinimapPosition.MinimapOffsetConditional offsetConditional = FTBChunksClientConfig.MINIMAP_POSITION_OFFSET_CONDITION.get();

                        if (offsetConditional.test(minimapPosition)) {
                            x += minimapPosition.posX == 0 ? offsetX : -offsetX;
                            y -= minimapPosition.posY > 1 ? offsetY : -offsetY;
                        }

                        seasonStack.pose().pushPose();
                        seasonStack.pose().translate((double) x + halfSizeD, (double) ((float) y + yOff), 0.0);
                        seasonStack.pose().scale(s1, s1, 1.0F);


                        FormattedCharSequence bs = (MINIMAP_TEXT_LIST.get(0)).getVisualOrderText();
                        int bsw = mc.font.width(bs);
                        int iconDim = mc.font.lineHeight;

                        seasonStack.drawString(mc.font, bs, (int) ((float) ((-bsw) + iconDim / 2) / 2.0F), (int) (i * 11), -1);

                        //Icon
                        ResourceLocation SEASON = getSeasonResource();
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.setShaderTexture(0, SEASON);
                        seasonStack.blit(SEASON, (int) (((-bsw) + iconDim / 2) / 2.0F), (i * 11), 0, 0, iconDim, iconDim, iconDim, iconDim);
                        seasonStack.pose().popPose();
                    }
                }
            }
        }
    }
}