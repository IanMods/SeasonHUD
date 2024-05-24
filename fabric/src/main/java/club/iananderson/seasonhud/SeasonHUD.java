package club.iananderson.seasonhud;

import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.minimaps.JourneyMap;
import club.iananderson.seasonhud.client.minimaps.MapAtlases;
import club.iananderson.seasonhud.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class SeasonHUD implements ModInitializer {
    public SeasonHUD(){}

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        Common.init();

        SeasonHUDOverlay.init();
        JourneyMap.init();
        MapAtlases.init();
    }
}