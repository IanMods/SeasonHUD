package club.iananderson.seasonhud;

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
  }
}