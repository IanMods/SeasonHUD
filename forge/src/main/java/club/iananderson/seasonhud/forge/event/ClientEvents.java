package club.iananderson.seasonhud.forge.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.MainConfigScreen;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHUDOverlay;
import net.minecraft.client.DeltaTracker;
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
        MainConfigScreen.getInstance().open();
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

    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}
