package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class SeasonHUDClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, Config.GENERAL_SPEC,
        "SeasonHUD-client.toml");
    KeyBindings.register();
  }
}
