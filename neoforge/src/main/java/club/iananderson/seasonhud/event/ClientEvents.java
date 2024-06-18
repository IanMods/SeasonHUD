package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.gui.screens.SeasonHUDScreen;
import club.iananderson.seasonhud.client.minimaps.JourneyMap;
import club.iananderson.seasonhud.client.minimaps.MapAtlases;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, modid = Common.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ClientEvents {

  @SubscribeEvent
  public static void onKeyInput(InputEvent.Key Event) {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      SeasonHUDScreen.open();
    }
  }

  @EventBusSubscriber(value = Dist.CLIENT, modid = Common.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
  public static class ModBus {
    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(KeyBindings.seasonhudOptionsKeyMapping);
    }

    //Overlays
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
      event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, Common.location("seasonhud"), new SeasonHUDOverlay());
      event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, Common.location("journeymap"), new JourneyMap());
      event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, Common.location("mapatlases"), new MapAtlases());
    }
  }

}