package club.iananderson.seasonhud;

import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.minimaps.FTBChunks;
import club.iananderson.seasonhud.client.minimaps.XaeroMinimap;
import club.iananderson.seasonhud.config.ModConfig;
import net.fabricmc.api.ModInitializer;

public class SeasonHUD implements ModInitializer {

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ModConfig.init();
        SeasonHUDOverlay.init();
        XaeroMinimap.init();
        FTBChunks.init();
        //JourneyMap.init();
    }
    public static final String MODID = "seasonhud";

}
