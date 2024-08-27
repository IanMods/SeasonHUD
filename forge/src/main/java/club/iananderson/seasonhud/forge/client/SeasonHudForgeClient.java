package club.iananderson.seasonhud.forge.client;

import club.iananderson.seasonhud.Common;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SeasonHudForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
  }
}