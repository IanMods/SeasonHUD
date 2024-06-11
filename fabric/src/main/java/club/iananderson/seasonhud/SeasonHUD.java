package club.iananderson.seasonhud;

import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.minimaps.JourneyMap;
import net.fabricmc.api.ModInitializer;

public class SeasonHUD implements ModInitializer {

  public SeasonHUD() {
  }

  /**
   * Runs the mod initializer.
   */
  @Override
  public void onInitialize() {
    Common.init();

    SeasonHUDOverlay.init();
    JourneyMap.init();
  }
}