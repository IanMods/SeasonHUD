package club.iananderson.seasonhud.neoforge.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class SeasonHudNeoForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
      Common.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit();
    }
  }
}
