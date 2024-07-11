package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;

public class SeasonHUDOverlayCommon {
  private SeasonHUDOverlayCommon() {
  }

  public static void render(GuiGraphics graphics) {
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
    int screenWidth = mc.getWindow().getGuiScaledWidth();
    int screenHeight = mc.getWindow().getGuiScaledHeight();
    int x = 0;
    int y = 0;
    int xOffset = Config.getHudX();
    int yOffset = Config.getHudY();
    int stringWidth = mc.font.width(seasonCombined);
    int stringHeight = mc.font.lineHeight;

    if (Common.drawDefaultHud() && Common.vanillaShouldDrawHud() && Calendar.calendarFound()) {
      switch (Config.getHudLocation()) {
        case TOP_LEFT -> {
          x = xOffset;
          y = yOffset;
        }
        case TOP_CENTER -> {
          x = (screenWidth / 2) - (stringWidth / 2);
          y = yOffset;
        }
        case TOP_RIGHT -> {
          x = screenWidth - stringWidth - xOffset;
          y = yOffset;
        }
        case BOTTOM_LEFT -> {
          x = xOffset;
          y = screenHeight - stringHeight - yOffset;
        }
        case BOTTOM_RIGHT -> {
          x = screenWidth - stringWidth - xOffset;
          y = screenHeight - stringHeight - yOffset;
        }
      }

      //Text
      graphics.pose().pushPose();
      graphics.pose().scale(1F, 1F, 1F);
      graphics.drawString(mc.font, seasonCombined, x, y, 0xffffff);
      graphics.pose().popPose();
    }
  }
}
