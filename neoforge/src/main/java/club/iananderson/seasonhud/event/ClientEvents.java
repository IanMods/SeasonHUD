package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.SeasonHUDScreen;
import club.iananderson.seasonhud.client.overlays.JourneyMap;
import club.iananderson.seasonhud.client.overlays.MapAtlases;
import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlay;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

  @SubscribeEvent
  public static void onKeyInput(InputEvent.Key Event) {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      SeasonHUDScreen.open();
    }
  }

  @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ModBus {
    //Overlays
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
      SeasonHUDOverlay.init();
      event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), Common.location("seasonhud"),
                          SeasonHUDOverlay.HUD_INSTANCE);
    }

    @SubscribeEvent
    public static void registerJourneyMapOverlay(RegisterGuiOverlaysEvent event) {
      if (CurrentMinimap.journeyMapLoaded()) {
        JourneyMap.init();
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), Common.location("journeymap"), JourneyMap.HUD_INSTANCE);
      }
    }

    @SubscribeEvent
    public static void registerMapAtlasesOverlay(RegisterGuiOverlaysEvent event) {
      if (CurrentMinimap.mapAtlasesLoaded()) {
        MapAtlases.init();
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), Common.location("mapatlases"), MapAtlases.HUD_INSTANCE);
      }
    }

    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }

}