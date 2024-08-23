package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;

public class SeasonHUDOverlayCommon {
  private SeasonHUDOverlayCommon() {
  }

  public static void render(PoseStack graphics) {
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
        case TOP_LEFT:
          x = xOffset;
          y = yOffset;
          break;

        case TOP_CENTER:
          x = (screenWidth / 2) - (stringWidth / 2);
          y = yOffset;
          break;

        case TOP_RIGHT:
          x = screenWidth - stringWidth - xOffset;
          y = yOffset;
          break;

        case BOTTOM_LEFT:
          x = xOffset;
          y = screenHeight - stringHeight - yOffset;
          break;

        case BOTTOM_RIGHT:
          x = screenWidth - stringWidth - xOffset;
          y = screenHeight - stringHeight - yOffset;
          break;
      }

      //Text
      graphics.pushPose();
      graphics.scale(1F, 1F, 1F);
      mc.font.drawShadow(graphics, seasonCombined, x, y, 0xffffff);
      graphics.popPose();
    }
  }
}
