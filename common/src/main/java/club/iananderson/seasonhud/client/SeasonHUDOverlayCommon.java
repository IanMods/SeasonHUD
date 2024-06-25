package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.Location;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;

public class SeasonHUDOverlayCommon {

  public static void render(GuiGraphics graphics) {
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
    float guiSize = (float) mc.getWindow().getGuiScale();
    int screenWidth = mc.getWindow().getGuiScaledWidth();
    int screenHeight = mc.getWindow().getGuiScaledHeight();
    int offsetDim = 2;
    int x = 0;
    int y = 0;
    int xOffset = (int) ((Config.hudX.get() + offsetDim) / guiSize);
    int yOffset = (int) ((Config.hudY.get() + offsetDim) / guiSize);
    int stringWidth = mc.font.width(seasonCombined);

    if (Config.enableMod.get() && (CurrentMinimap.noMinimapLoaded() || (Services.MINIMAP.currentMinimapHidden()
        && Config.showDefaultWhenMinimapHidden.get()) || !Config.enableMinimapIntegration.get())) {
      Location hudLoc = Config.hudLocation.get();

      switch (hudLoc) {
        case TOP_LEFT -> {
          x = offsetDim;
          y = offsetDim;
        }
        case TOP_CENTER -> {
          x = (screenWidth / 2) - (stringWidth / 2);
          y = offsetDim;
        }
        case TOP_RIGHT -> {
          x = screenWidth - stringWidth - offsetDim;
          y = offsetDim;
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

      //Text
      if (Common.vanillaShouldDrawHud() && !mc.player.isScoping() && Calendar.calendarFound()) {
        graphics.pose().pushPose();
        graphics.pose().scale(1F, 1F, 1F);
        CurrentSeason.getInstance(mc).drawSeasonText(graphics, x + xOffset, y + yOffset, false);
        //graphics.drawString(mc.font, seasonCombined, x + xOffset, y + yOffset, 0xffffff);
        graphics.pose().popPose();
      }
    }
  }
}
