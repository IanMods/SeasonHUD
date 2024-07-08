package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.event.ClientEvents;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.neoforged.fml.config.ModConfig;

public class SeasonHUDClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, Config.GENERAL_SPEC,
        "SeasonHUD-client.toml");
    ClientEvents.register();
  }
}
