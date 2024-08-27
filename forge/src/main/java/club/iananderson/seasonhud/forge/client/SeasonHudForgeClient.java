package club.iananderson.seasonhud.forge.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SeasonHudForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
      Common.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit();
    }
  }
}
