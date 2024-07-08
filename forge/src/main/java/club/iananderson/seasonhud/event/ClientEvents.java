package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.SeasonHUDScreen;
import net.minecraft.client.DeltaTracker;
import club.iananderson.seasonhud.client.overlays.JourneyMap;
import club.iananderson.seasonhud.client.overlays.MapAtlases;
import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlay;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key Event) {
      if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
        SeasonHUDScreen.open();
      }
    }
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {
    //Overlays
    public static void registerGuiOverlays(GuiGraphics graphics, DeltaTracker deltaTracker) {
      SeasonHUDOverlay.init();
      SeasonHUDOverlay.HUD_INSTANCE.render(graphics, deltaTracker);
    }

    public static void registerJourneyMapOverlay(GuiGraphics graphics, DeltaTracker deltaTracker) {
      if (CurrentMinimap.journeyMapLoaded()) {
        JourneyMap.init();
        JourneyMap.HUD_INSTANCE.render(graphics, deltaTracker);
      }
    }

    public static void registerMapAtlasesOverlay(GuiGraphics graphics, DeltaTracker deltaTracker) {
      if (CurrentMinimap.mapAtlasesLoaded()) {
        MapAtlases.init();
        MapAtlases.HUD_INSTANCE.render(graphics, deltaTracker);
      }
    }

    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}
