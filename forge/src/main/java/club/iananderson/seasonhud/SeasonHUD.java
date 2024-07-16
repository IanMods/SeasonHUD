package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.curios.CuriosCompat;
import fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHUD {

  public SeasonHUD() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    MinecraftForge.EVENT_BUS.register(this);

    Common.init();
    modEventBus.addListener(ClientModEvents::commonSetup);

    NeoForgeConfigRegistry.INSTANCE.register(ModConfig.Type.CLIENT, Config.GENERAL_SPEC, "SeasonHUD-client.toml");
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
      if (Common.curiosLoaded()) {
        Common.LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
      if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
        Common.LOG.info("Talking to Accessories");
        new AccessoriesCompat().setup(event);
      }
    }
  }
}