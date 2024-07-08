package club.iananderson.seasonhud;

import static club.iananderson.seasonhud.Common.LOG;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.curios.CuriosCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Common.MOD_ID)
public class SeasonHUD {

  public SeasonHUD(IEventBus modEventBus, ModContainer modContainer) {
    modEventBus.addListener(this::commonSetup);

    Common.init();

    modContainer.registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC, "SeasonHUD-client.toml");
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    if (Common.curiosLoaded()) {
      LOG.info("Talking to Curios");
      new CuriosCompat().setup(event);
    }
  }
}