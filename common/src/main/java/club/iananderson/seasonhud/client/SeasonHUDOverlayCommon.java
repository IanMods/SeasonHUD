package club.iananderson.seasonhud.client;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.Location;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class SeasonHUDOverlayCommon {

  public static void render(GuiGraphics seasonStack) {
    MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
                                                             CurrentSeason.getSeasonHudName().get(0).copy()
                                                                          .withStyle(SEASON_STYLE),
                                                             CurrentSeason.getSeasonHudName().get(1).copy());

    float guiSize = (float) mc.getWindow().getGuiScale();

    int screenWidth = mc.getWindow().getGuiScaledWidth();
    int screenHeight = mc.getWindow().getGuiScaledHeight();

    int xOffset = (int) (Config.hudX.get() / guiSize);
    int yOffset = (int) ((Config.hudY.get()) / guiSize);
    int x = 1;
    int y = 1;
    int offsetDim = 2;

    int stringWidth = mc.font.width(seasonCombined);

    if (Config.enableMod.get() && (CurrentMinimap.noMinimapLoaded() || (Services.MINIMAP.currentMinimapHidden()
        && Config.showDefaultWhenMinimapHidden.get()) || !Config.enableMinimapIntegration.get())) {
      Location hudLoc = Config.hudLocation.get();

      switch (hudLoc) {
        case TOP_LEFT -> {
          x = offsetDim;
          y = 0;
        }
        case TOP_CENTER -> {
          x = screenWidth / 2 - stringWidth / 2;
          y = 0;
        }
        case TOP_RIGHT -> {
          x = screenWidth - stringWidth - offsetDim;
          y = 0;
        }
        case BOTTOM_LEFT -> {
          x = offsetDim;
          y = screenHeight - (2 * offsetDim);
        }
        case BOTTOM_RIGHT -> {
          x = screenWidth - stringWidth - offsetDim;
          y = screenHeight - (2 * offsetDim);
        }
      }

      if (Common.vanillaShouldDrawHud() && !mc.player.isScoping() && Calendar.calendarFound()) {
        seasonStack.pose().pushPose();
        seasonStack.pose().scale(1F, 1F, 1F);

        //Text
        int iconX = x + xOffset;
        int iconY = y + yOffset + offsetDim;

        seasonStack.drawString(mc.font, seasonCombined, iconX, iconY, 0xffffff);
        seasonStack.pose().popPose();
      }
    }
  }
}
