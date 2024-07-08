package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.Anchoring;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
import pepjebs.mapatlases.item.MapAtlasItem;

public class MapAtlases implements IGuiOverlay {
  public static MapAtlases HUD_INSTANCE;
  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      MapAtlasItem mapAtlas = MapAtlasesMod.MAP_ATLAS.get();

      if (mc.level == null || mc.player == null || mc.getDebugOverlay().showDebugScreen()) {
        return false;
      } else if (!MapAtlasesClientConfig.drawMiniMapHUD.get()) {
        return false;
      } else if (MapAtlasesClientConfig.hideWhenInHand.get() && (mc.player.getMainHandItem().is(mapAtlas)
          || mc.player.getOffhandItem().is(mapAtlas))) {
        return false;
      } else {
        return !MapAtlasesClient.getCurrentActiveAtlas().isEmpty();
      }
    } else {
      return false;
    }
  }

  @Override
  public void render(ExtendedGui gui, GuiGraphics seasonStack, float partialTick, int screenWidth, int screenHeight) {
    Minecraft mc = Minecraft.getInstance();

    if (CurrentMinimap.mapAtlasesLoaded() && shouldDraw(mc)) {
      Anchoring anchorLocation = MapAtlasesClientConfig.miniMapAnchoring.get();
      float textScaling = (float) (double) MapAtlasesClientConfig.minimapCoordsAndBiomeScale.get();
      float globalScale = MapAtlasesClientConfig.miniMapScale.get().floatValue();
      int actualBgSize = (int) (BG_SIZE * globalScale);
      int offset = 5;
      int x = anchorLocation.isLeft ? offset : (int) (screenWidth / globalScale) - (BG_SIZE + offset);
      int y = anchorLocation.isUp ? offset : (int) (screenHeight / globalScale) - (BG_SIZE + offset);

      seasonStack.pose().pushPose();
      seasonStack.pose().scale(globalScale, globalScale, 1);

      x += (int) (MapAtlasesClientConfig.miniMapHorizontalOffset.get() / globalScale);
      y += (int) (MapAtlasesClientConfig.miniMapVerticalOffset.get() / globalScale);

      if (anchorLocation.isUp && !anchorLocation.isLeft) {
        boolean hasBeneficial = false;
        boolean hasNegative = false;
        for (var e : mc.player.getActiveEffects()) {
          MobEffect effect = e.getEffect();
          if (effect.isBeneficial()) {
            hasBeneficial = true;
          } else {
            hasNegative = true;
          }
        }
        int offsetForEffects = MapAtlasesClientConfig.activePotionVerticalOffset.get();
        if (hasNegative && y < 2 * offsetForEffects) {
          y += (2 * offsetForEffects - y);
        } else if (hasBeneficial && y < offsetForEffects) {
          y += (offsetForEffects - y);
        }
      }

      float textHeightOffset = 2.0F;

      if (MapAtlasesClientConfig.drawMinimapCoords.get()) {
        textHeightOffset += (10.0F * textScaling);
      }
      if (MapAtlasesClientConfig.drawMinimapChunkCoords.get()) {
        textHeightOffset += (10.0F * textScaling);
      }
      if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
        textHeightOffset += (10.0F * textScaling);
      }

      MapAtlasesCommon.drawMapComponentSeason(seasonStack, mc.font, x,
                                              (int) (y + BG_SIZE + (textHeightOffset / globalScale)), actualBgSize,
                                              textScaling, globalScale);
      seasonStack.pose().popPose();
    }
  }
}