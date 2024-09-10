package club.iananderson.seasonhud.neoforge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.impl.minimaps.SeasonComponent;
import club.iananderson.seasonhud.platform.Services;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Common.MOD_ID)
public class SeasonHudNeoForge {

  public SeasonHudNeoForge(IEventBus modEventBus, ModContainer modContainer) {
    Common.init();

    modContainer.registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC, "SeasonHUD-client.toml");

    modEventBus.addListener(SeasonHudNeoForge::onInitialize);
    modEventBus.addListener(SeasonHudNeoForge::ftbChunkSetup);
  }

  public static void onInitialize(FMLCommonSetupEvent event) {
    if (Common.accessoriesLoaded()) {
      Common.LOG.info("Talking to Accessories");
      AccessoriesCompat.init();
    }
  }

  public static void ftbChunkSetup(FMLCommonSetupEvent event) {
    if (Services.PLATFORM.isModLoaded(Minimaps.FTB_CHUNKS.getModID())) {
      Common.LOG.info("Loading FTB Chunks Season Component");
      SeasonComponent.registerFtbSeason();
    }
  }
}