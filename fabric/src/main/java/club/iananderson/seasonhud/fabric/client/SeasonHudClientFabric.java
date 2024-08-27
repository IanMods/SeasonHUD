package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.impl.minimaps.SeasonComponent;
import club.iananderson.seasonhud.platform.Services;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.neoforged.fml.config.ModConfig;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, Config.GENERAL_SPEC,
                                             "SeasonHUD-client.toml");
    ClientEvents.register();

    if (Services.PLATFORM.isModLoaded(Minimaps.FTB_CHUNKS.getModID())) {
      SeasonComponent.registerFtbSeason();
    }

    if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
      Common.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit();
    }
  }
}
