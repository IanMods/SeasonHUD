package club.iananderson.seasonhud;

import static club.iananderson.seasonhud.Common.LOG;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.curios.CuriosCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Common.MOD_ID)
public class SeasonHUD {

  public SeasonHUD(IEventBus modEventBus) {
    Common.init();

    modEventBus.addListener(ClientModEvents::commonSetup);

    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC, "SeasonHUD-client.toml");
  }

  @EventBusSubscriber(modid = Common.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
      if (Common.curiosLoaded()) {
        LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
      if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
        LOG.info("Talking to Accessories");
        new AccessoriesCompat().setup(event);
      }
    }
  }
}