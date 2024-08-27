package club.iananderson.seasonhud.fabric;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import net.fabricmc.api.ModInitializer;

public class SeasonHudFabric implements ModInitializer {

  public SeasonHudFabric() {
  }

  /**
   * Runs the mod initializer.
   */
  @Override
  public void onInitialize() {
    Common.init();

    if (Common.accessoriesLoaded() && Common.extrasLoaded()) {
      Common.LOG.info("Talking to Accessories");
      AccessoriesCompat.init();
    }
  }

}