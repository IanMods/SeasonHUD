package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.SeasonHUDScreen;
import club.iananderson.seasonhud.client.overlays.JourneyMap;
import club.iananderson.seasonhud.client.overlays.MapAtlases;
import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlay;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
      if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
        SeasonHUDScreen.open();
      }
    }
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {

    //Overlays
    @SubscribeEvent
    public static void registerGuiOverlays(FMLClientSetupEvent event) {
      SeasonHUDOverlay.init();
      OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "seasonhud", SeasonHUDOverlay.HUD_INSTANCE);
    }

    @SubscribeEvent
    public static void registerJourneyMapOverlay(FMLClientSetupEvent event) {
      if (CurrentMinimap.journeyMapLoaded()) {
        JourneyMap.init();
        OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "journeymap", JourneyMap.HUD_INSTANCE);
      }
    }

    @SubscribeEvent
    public static void registerMapAtlasesOverlay(FMLClientSetupEvent event) {
      if (CurrentMinimap.mapAtlasesLoaded()) {
        MapAtlases.init();
        OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "mapatlases", MapAtlases.HUD_INSTANCE);
      }
    }

    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(FMLClientSetupEvent event) {
      ClientRegistry.registerKeyBinding(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}