package club.iananderson.seasonhud.client.minimaps;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.Anchoring;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;

public class MapAtlases implements IGuiOverlay {

  protected final int BG_SIZE = 64;
    private static final Minecraft mc = Minecraft.getInstance();

  private static void drawStringWithLighterShadow(PoseStack poseStack, Font font, MutableComponent text, float x, float y) {
    mc.font.draw(poseStack, text, x + 1.0F, y + 1.0F, 5855577);
    mc.font.draw(poseStack, text, x, y, 14737632);
  }

  public static void drawScaledComponent(PoseStack context, Font font, int x, int y, MutableComponent text, float textScaling, int maxWidth, int targetWidth) {
        float textWidth = (float)font.width(text);
        float scale = Math.min(1.0F, (float)maxWidth * textScaling / textWidth);
        scale *= textScaling;
        float centerX = (float)x + (float)targetWidth / 2.0F;
        context.pushPose();
    context.translate(centerX, (float) (y + 4), 5.0F);
    context.scale(scale, scale, 1.0F);
    context.translate(-textWidth / 2.0F, -4.0F, 0.0F);
    drawStringWithLighterShadow(context, font, text, 0, 0);
    context.popPose();
  }

  public static void drawMapComponentSeason(PoseStack poseStack, Font font, int x, int y, int targetWidth,
                                            float textScaling) {
    if (CurrentMinimap.minimapLoaded("map_atlases")) {
      MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
                                                               getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE),
                                                               getSeasonHudName().get(1).copy());

      float globalScale = (float) (double) MapAtlasesClientConfig.miniMapScale.get();
      drawScaledComponent(poseStack, font, x, y, seasonCombined, textScaling / globalScale, targetWidth,
                          (int) (targetWidth / globalScale));
    }
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (CurrentMinimap.minimapLoaded("map_atlases")) {
      if (mc.level == null || mc.player == null) {
        return false;
      } else if (mc.options.renderDebug) {
        return false;
      } else if (!MapAtlasesClientConfig.drawMiniMapHUD.get()) {
        return false;
      } else if (MapAtlasesClientConfig.hideWhenInHand.get() && (
          mc.player.getMainHandItem().is(MapAtlasesMod.MAP_ATLAS.get()) || mc.player.getOffhandItem()
                                                                                    .is(MapAtlasesMod.MAP_ATLAS.get()))) {
        return false;
      } else {
        return !MapAtlasesClient.getCurrentActiveAtlas().isEmpty();
      }
    } else {
      return false;
    }
  }

  @Override
  public void render(ForgeGui gui, PoseStack seasonStack, float partialTick, int screenWidth, int screenHeight) {
    if (CurrentMinimap.minimapLoaded("map_atlases") && shouldDraw(mc)) {
      float textScaling = (float) (double) MapAtlasesClientConfig.minimapCoordsAndBiomeScale.get();

      int textHeightOffset = 2;
      float globalScale = (float) (double) MapAtlasesClientConfig.miniMapScale.get();
      int actualBgSize = (int) (BG_SIZE * globalScale);

      LocalPlayer player = mc.player;

      seasonStack.pushPose();
      seasonStack.scale(globalScale, globalScale, 1);

      Anchoring anchorLocation = MapAtlasesClientConfig.miniMapAnchoring.get();
      int x = anchorLocation.isLeft ? 0 : (int) (screenWidth / globalScale) - BG_SIZE;
      int y = anchorLocation.isUp ? 0 : (int) (screenHeight / globalScale) - BG_SIZE;
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
          textHeightOffset += (10 * textScaling);
        }

//                if (MapAtlasesClientConfig.drawMinimapChunkCoords.get()) {
//                    textHeightOffset += (10 * textScaling);
//                }

        if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
          textHeightOffset += (10 * textScaling);
        }

        drawMapComponentSeason(seasonStack, font, (int) (x), (int) (y + BG_SIZE + (textHeightOffset / globalScale)),
                               actualBgSize, textScaling);
        seasonStack.popPose();
      }
    }
  }
}