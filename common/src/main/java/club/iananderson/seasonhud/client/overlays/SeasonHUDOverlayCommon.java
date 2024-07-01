package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;

public class SeasonHUDOverlayCommon {
  public static void render(GuiGraphics seasonStack) {
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

    if (Config.enableMod.get() && (CurrentMinimap.noMinimapLoaded() || (Services.MINIMAP.allMinimapsHidden()
        && Config.showDefaultWhenMinimapHidden.get()))) {
      switch (Config.hudLocation.get()) {
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
      if (Common.vanillaShouldDrawHud() && Calendar.calendarFound()) {
        seasonStack.pose().pushPose();
        seasonStack.pose().scale(1F, 1F, 1F);
        seasonStack.drawString(mc.font, seasonCombined, x + xOffset, y + yOffset, 0xffffff);
        seasonStack.pose().popPose();
      }
    }
  }
}
