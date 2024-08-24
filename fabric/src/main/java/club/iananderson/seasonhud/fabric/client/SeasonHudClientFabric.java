package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, Config.GENERAL_SPEC,
                                          "SeasonHUD-client.toml");
    ClientEvents.register();
  }
}
