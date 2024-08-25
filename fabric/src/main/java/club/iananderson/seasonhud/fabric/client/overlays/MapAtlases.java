package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.MapAtlasesCommon;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.Anchoring;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
import pepjebs.mapatlases.item.MapAtlasItem;

public class MapAtlases implements HudRenderCallback {
  public static MapAtlases HUD_INSTANCE;
  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      MapAtlasItem mapAtlas = MapAtlasesMod.MAP_ATLAS.get();

      if (mc.level == null || mc.player == null || mc.options.renderDebug) {
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
  public void onHudRender(PoseStack graphics, float alpha) {
    Minecraft mc = Minecraft.getInstance();

    if (CurrentMinimap.mapAtlasesLoaded() && shouldDraw(mc)) {
      float textScaling = (float) (double) MapAtlasesClientConfig.minimapCoordsAndBiomeScale.get();
      float globalScale = MapAtlasesClientConfig.miniMapScale.get().floatValue();

      graphics.pushPose();
      graphics.scale(globalScale, globalScale, 1);

      int mapWidgetSize = 58;
      int actualBgSize = (int) (64.0F * globalScale);
      Anchoring anchorLocation = MapAtlasesClientConfig.miniMapAnchoring.get();
      int screenWidth = mc.getWindow().getGuiScaledWidth();
      int screenHeight = mc.getWindow().getGuiScaledHeight();
      int x = anchorLocation.isLeft ? 0 : (int) ((float) screenWidth / globalScale) - BG_SIZE;
      int y = anchorLocation.isUp ? 0 : (int) ((float) screenHeight / globalScale) - BG_SIZE;
      x += (int) (MapAtlasesClientConfig.miniMapHorizontalOffset.get() / globalScale);
      y += (int) (MapAtlasesClientConfig.miniMapVerticalOffset.get() / globalScale);
      int offsetForEffects;
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

        offsetForEffects = MapAtlasesClientConfig.activePotionVerticalOffset.get();
        if (hasNegative && y < 2 * offsetForEffects) {
          y += (2 * offsetForEffects - y);
        } else if (hasBeneficial && y < offsetForEffects) {
          y += (offsetForEffects - y);
        }
      }

      float textHeightOffset = 2F;

      if (MapAtlasesClientConfig.drawMinimapCoords.get()) {
        textHeightOffset += (10.0F * textScaling);
      }
      if (MapAtlasesClientConfig.drawMinimapChunkCoords.get()) {
        textHeightOffset += (10.0F * textScaling);
      }
      if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
        textHeightOffset += (10.0F * textScaling);
      }

      MapAtlasesCommon.drawMapComponentSeason(graphics, mc.font, x,
                                              (int) (y + BG_SIZE + (textHeightOffset / globalScale)), actualBgSize,
                                              textScaling, globalScale);
      graphics.popPose();
    }
  }
}
