package club.iananderson.seasonhud.forge.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.MainConfigScreen;
import club.iananderson.seasonhud.forge.client.overlays.JourneyMap;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHUDOverlay;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key Event) {
      if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
        MainConfigScreen.getInstance().open();
      }
    }
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {

    //Overlays
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
      SeasonHUDOverlay.init();
      event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "seasonhud", SeasonHUDOverlay.HUD_INSTANCE);
    }

    @SubscribeEvent
    public static void registerJourneyMapOverlay(RegisterGuiOverlaysEvent event) {
      if (CurrentMinimap.journeyMapLoaded()) {
        JourneyMap.init();
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "journeymap", JourneyMap.HUD_INSTANCE);
      }
    }

    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}
