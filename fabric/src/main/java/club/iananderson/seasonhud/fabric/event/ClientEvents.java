package club.iananderson.seasonhud.fabric.event;

import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.MainConfigScreen;
import club.iananderson.seasonhud.fabric.client.overlays.SeasonHUDOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientEvents {
  private ClientEvents() {
  }

  //Key Bindings
  private static void registerKeyInputs() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
        MainConfigScreen.getInstance().open();
      }
    });
  }

  private static void registerKeyMappings() {
    KeyBindings.seasonhudOptionsKeyMapping = KeyBindingHelper.registerKeyBinding(
        KeyBindings.seasonhudOptionsKeyMapping);
  }

  private static void registerHud() {
    SeasonHUDOverlay.init();
  }

  public static void register() {
    registerKeyMappings();
    registerKeyInputs();
    registerHud();
  }
}

