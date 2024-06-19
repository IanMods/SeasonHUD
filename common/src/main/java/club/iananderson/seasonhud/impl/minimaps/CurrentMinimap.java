package club.iananderson.seasonhud.impl.minimaps;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;
import static club.iananderson.seasonhud.config.Config.enableMinimapIntegration;
import static club.iananderson.seasonhud.config.Config.enableMod;
import static club.iananderson.seasonhud.platform.Services.MINIMAP;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;

public class CurrentMinimap {

  /**
   * Checks if a minimap mod is currently loaded.
   *
   * @param minimap The modid to check if the minimap is loaded.
   * @return True if the mod is loaded.
   */
  public static boolean minimapLoaded(String minimap) {
    return Services.PLATFORM.isModLoaded(minimap);
  }

  /**
   * Checks if there are currently no minimap mods loaded.
   *
   * @return True if no minimap mods are present.
   */
  public static boolean noMinimapLoaded() {
    return !minimapLoaded("xaerominimap") && !minimapLoaded("xaerominimapfair") && !minimapLoaded("journeymap")
        && !minimapLoaded("ftbchunks") && !minimapLoaded("map_atlases");
  }

  /**
   * Checks if the minimap version of the HUD should be drawn instead of the default.
   *
   * @return True if the minimap version of the HUD should be drawn
   */
  public static boolean shouldDrawMinimapHud() {
    if (mc.level == null || mc.player == null) {
      return false;
    }

    return (enableMod.get() && enableMinimapIntegration.get() && Calendar.calendarFound()
        && !MINIMAP.hideHudInCurrentDimension() && !MINIMAP.currentMinimapHidden() && Common.vanillaShouldDrawHud());
  }
}
