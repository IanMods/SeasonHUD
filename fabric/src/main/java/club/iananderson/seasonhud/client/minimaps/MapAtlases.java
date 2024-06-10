package club.iananderson.seasonhud.client.minimaps;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.minimapLoaded;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;

import club.iananderson.seasonhud.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.Anchoring;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;

public class MapAtlases implements HudRenderCallback {

  public static MapAtlases HUD_INSTANCE;

  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  private static void drawStringWithLighterShadow(GuiGraphics context, Font font,
      MutableComponent text, int x, int y) {
    context.drawString(font, text, x + 1, y + 1, 5855577, false);
    context.drawString(font, text, x, y, 14737632, false);
  }

  public static void drawScaledComponent(GuiGraphics context, Font font, int x, int y,
      MutableComponent text, float textScaling, int maxWidth, int targetWidth) {
    PoseStack pose = context.pose();
    float textWidth = (float) font.width(text);

    float scale = Math.min(1.0F, (float) maxWidth * textScaling / textWidth);
    scale *= textScaling;
    float centerX = (float) x + (float) targetWidth / 2.0F;
    pose.pushPose();
    pose.translate(centerX, (float) (y + 4), 5.0F);
    pose.scale(scale, scale, 1.0F);
    pose.translate(-textWidth / 2.0F, -4.0F, 0.0F);
    drawStringWithLighterShadow(context, font, text, 0, 0);
    pose.popPose();
  }

  public static void drawMapComponentSeason(GuiGraphics poseStack, Font font, int x, int y,
      int targetWidth, float textScaling) {
    if (minimapLoaded("map_atlases")) {
      MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
          getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE),
          getSeasonHudName().get(1).copy());

      float globalScale = (float) (double) MapAtlasesClientConfig.miniMapScale.get();
      //String seasonToDisplay = getSeasonHudName().get(0).getString();
      drawScaledComponent(poseStack, font, x, y, seasonCombined, textScaling / globalScale,
          targetWidth, (int) (targetWidth / globalScale));
    }
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (minimapLoaded("map_atlases")) {
      if (mc.level == null || mc.player == null) {
        return false;
      } else if (mc.options.renderDebug) {
        return false;
      } else if (!MapAtlasesClientConfig.drawMiniMapHUD.get()) {
        return false;
      } else if (MapAtlasesClientConfig.hideWhenInHand.get() && (
          mc.player.getMainHandItem().is(MapAtlasesMod.MAP_ATLAS.get()) ||
              mc.player.getOffhandItem().is(MapAtlasesMod.MAP_ATLAS.get()))) {
        return false;
      } else {
        return !MapAtlasesClient.getCurrentActiveAtlas().isEmpty();
      }
    } else {
      return false;
    }
  }

  @Override
  public void onHudRender(GuiGraphics seasonStack, float alpha) {
    if (minimapLoaded("map_atlases") && shouldDraw(mc)) {
      int screenWidth = mc.getWindow().getScreenWidth();
      int screenHeight = mc.getWindow().getScreenHeight();
      float textScaling = (float) (double) MapAtlasesClientConfig.minimapCoordsAndBiomeScale.get();

      float textHeightOffset = 2.0F;
      float globalScale = (float) (double) MapAtlasesClientConfig.miniMapScale.get();
      int actualBgSize = (int) (BG_SIZE * globalScale);

      LocalPlayer player = mc.player;

      seasonStack.pose().pushPose();
      seasonStack.pose().scale(globalScale, globalScale, 1);

      Anchoring anchorLocation = MapAtlasesClientConfig.miniMapAnchoring.get();
      int offset = 5;
      int x =
          anchorLocation.isLeft ? offset : (int) (screenWidth / globalScale) - (BG_SIZE + offset);
      int y =
          anchorLocation.isUp ? offset : (int) (screenHeight / globalScale) - (BG_SIZE + offset);
      x += (int) (MapAtlasesClientConfig.miniMapHorizontalOffset.get() / globalScale);
      y += (int) (MapAtlasesClientConfig.miniMapVerticalOffset.get() / globalScale);

      if (anchorLocation.isUp && !anchorLocation.isLeft) {
        boolean hasBeneficial = false;
        boolean hasNegative = false;
        for (var e : player.getActiveEffects()) {
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
      Font font = mc.font;

      if (Config.enableMod.get()) {
        if (MapAtlasesClientConfig.drawMinimapCoords.get()) {
          textHeightOffset = (textHeightOffset + 10.0F * textScaling);
        }

        if (MapAtlasesClientConfig.drawMinimapChunkCoords.get()) {
          textHeightOffset = (textHeightOffset + 10.0F * textScaling);
        }

        if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
          textHeightOffset = (textHeightOffset + 10.0F * textScaling);
        }

        drawMapComponentSeason(seasonStack, font, x,
            (int) (y + BG_SIZE + (textHeightOffset / globalScale)), actualBgSize, textScaling);
        seasonStack.pose().popPose();
      }
    }
  }
}