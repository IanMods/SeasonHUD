package club.iananderson.seasonhud.client.minimaps;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.minimapLoaded;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;

import club.iananderson.seasonhud.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;

import java.util.Arrays;

public class MapAtlases implements HudRenderCallback {

  public static MapAtlases HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new MapAtlases();
        HudRenderCallback.EVENT.register(HUD_INSTANCE);
    }

    private static final Minecraft mc = Minecraft.getInstance();

    public static void drawScaledText(PoseStack poseStack, float x, float y, MutableComponent text, float textScaling, int originOffsetWidth, int originOffsetHeight) {
    float textWidth = (float) mc.font.width(text) * textScaling;
        float textX = (float) ((double) x + (double) originOffsetWidth / 2.0 - (double) textWidth / 2.0);
        float textY = (float) (y + originOffsetHeight);
    if (textX + textWidth >= (float) mc.getWindow().getGuiScaledWidth()) {
            textX = (float) mc.getWindow().getGuiScaledWidth() - textWidth;
        }

        poseStack.pushPose();
    poseStack.translate(textX, textY, 5.0F);
    poseStack.scale(textScaling, textScaling, 1.0F);
    mc.font.draw(poseStack, text, 1.0F, 1.0F, Integer.parseInt("595959", 16));
    mc.font.draw(poseStack, text, 0.0F, 0.0F, Integer.parseInt("E0E0E0", 16));
    poseStack.popPose();
  }

  public static void drawMapComponentSeason(PoseStack poseStack, int x, int y, int originOffsetWidth, int originOffsetHeight, float textScaling) {
        if (minimapLoaded("map_atlases")) {
            MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
                    getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE),
                    getSeasonHudName().get(1).copy());
            drawScaledText(poseStack, x, y, seasonCombined, textScaling, originOffsetWidth, originOffsetHeight);
    }
  }

    public static boolean shouldDraw(Minecraft mc) {
        if (minimapLoaded("map_atlases")) {
            if (mc.player == null) {
                return false;
            } else if (MapAtlasesMod.CONFIG != null && !MapAtlasesMod.CONFIG.drawMiniMapHUD) {
                return false;
            } else if (mc.options.renderDebug) {
                return false;
            } else {
                ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player);
                if (atlas.isEmpty()) {
                    return false;
                } else if (MapAtlasesClient.currentMapStateId == null) {
                    return false;
                } else {
                    return atlas.getTag() != null && atlas.getTag().contains("maps") && Arrays.stream(atlas.getTag().getIntArray("maps")).anyMatch((i) -> {
                        return i == MapAtlasesAccessUtils.getMapIntFromString(MapAtlasesClient.currentMapStateId);
                    });
                }
            }
        } else return false;
    }

  @Override
  public void onHudRender(PoseStack seasonStack, float alpha) {
        if (minimapLoaded("map_atlases")) {
            if (mc.level != null && mc.player != null) {
                int mapBgScaledSize = (int) Math.floor(0.2 * (double) mc.getWindow().getGuiScaledHeight());
                if (MapAtlasesMod.CONFIG != null) {
                    mapBgScaledSize = (int) Math.floor((double) MapAtlasesMod.CONFIG.forceMiniMapScaling / 100.0 * (double) mc.getWindow().getGuiScaledHeight());
                }

                String anchorLocation = "UpperLeft";
                if (MapAtlasesMod.CONFIG != null) {
                    anchorLocation = MapAtlasesMod.CONFIG.miniMapAnchoring;
                }
                int x = anchorLocation.contains("Left") ? 0 : mc.getWindow().getGuiScaledWidth() - mapBgScaledSize;
                int y = anchorLocation.contains("Lower") ? mc.getWindow().getGuiScaledHeight() - mapBgScaledSize : 0;
                if (MapAtlasesMod.CONFIG != null) {
                    x += MapAtlasesMod.CONFIG.miniMapHorizontalOffset;
                    y += MapAtlasesMod.CONFIG.miniMapVerticalOffset;
                }

                int textHeightOffset;
                if (anchorLocation.contentEquals("UpperRight")) {
                    boolean hasBeneficial = mc.player.getActiveEffects().stream().anyMatch((p) -> {
                        return p.getEffect().isBeneficial();
                    });
                    boolean hasNegative = mc.player.getActiveEffects().stream().anyMatch((p) -> {
                        return !p.getEffect().isBeneficial();
                    });
                    textHeightOffset = 26;
                    if (MapAtlasesMod.CONFIG != null) {
                        textHeightOffset = MapAtlasesMod.CONFIG.activePotionVerticalOffset;
                    }

              if (hasNegative && y < 2 * textHeightOffset) {
                        y += 2 * textHeightOffset - y;
                    } else if (hasBeneficial && y < textHeightOffset) {
                        y += textHeightOffset - y;
                    }
                }

                if (Config.enableMod.get() && shouldDraw(mc)) {
                    float textScaling = MapAtlasesMod.CONFIG.minimapCoordsAndBiomeScale;
          textHeightOffset = mapBgScaledSize + 4;
                    if (anchorLocation.contains("Lower")) {
                        textHeightOffset = (int) (-24.0F * textScaling);
                    }

                    if (MapAtlasesMod.CONFIG.drawMinimapCoords) {
                        textHeightOffset = (int) ((float) textHeightOffset + 12.0F * textScaling);
                    }

                    if (MapAtlasesMod.CONFIG.drawMinimapBiome) {
                        textHeightOffset = (int) ((float) textHeightOffset + 12.0F * textScaling);
                    }

                    drawMapComponentSeason(seasonStack, x, y, mapBgScaledSize, textHeightOffset, textScaling);
        }
      }
    }
  }
}