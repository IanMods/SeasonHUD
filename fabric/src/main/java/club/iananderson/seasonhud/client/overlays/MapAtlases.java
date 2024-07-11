package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;

public class MapAtlases implements HudRenderCallback {
  private static final Minecraft mc = Minecraft.getInstance();
  public static MapAtlases HUD_INSTANCE;

  //Todo test this
  public static void init() {
    HUD_INSTANCE = new MapAtlases();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      if (mc.player == null) {
        return false;
      } else if (MapAtlasesMod.CONFIG != null && !MapAtlasesMod.CONFIG.drawMiniMapHUD) {
        return false;
      } else if (mc.getDebugOverlay().showDebugScreen()) {
        return false;
      } else {
        ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player);
        if (atlas.isEmpty()) {
          return false;
        } else if (MapAtlasesClient.currentMapStateId == null) {
          return false;
        } else {
          return false;
//          return atlas.getTag() != null && atlas.getTag().contains("maps") && Arrays.stream(
//              atlas.getTag().getIntArray("maps")).anyMatch((i) -> {
//            return i == MapAtlasesAccessUtils.getMapIntFromString(MapAtlasesClient.currentMapStateId);
//          });
        }
      }
    } else {
      return false;
    }
  }

  @Override
  public void onHudRender(GuiGraphics graphics, DeltaTracker tickCounter) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      if (mc.level != null && mc.player != null) {
        int mapBgScaledSize = (int) Math.floor(0.2 * (double) mc.getWindow().getGuiScaledHeight());
        if (MapAtlasesMod.CONFIG != null) {
          mapBgScaledSize = (int) Math.floor(
              (double) MapAtlasesMod.CONFIG.forceMiniMapScaling / 100.0 * (double) mc.getWindow().getGuiScaledHeight());
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
            return p.getEffect().value().isBeneficial();
          });
          boolean hasNegative = mc.player.getActiveEffects().stream().anyMatch((p) -> {
            return !p.getEffect().value().isBeneficial();
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

        if (Config.getEnableMod() && shouldDraw(mc)) {
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

          MapAtlasesCommon.drawMapComponentSeasonOld(graphics, x, y, mapBgScaledSize, textHeightOffset, textScaling);
        }
      }
    }
  }
}