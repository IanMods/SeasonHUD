package club.iananderson.seasonhud.fabric;

import club.iananderson.seasonhud.Common;
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
    }
}