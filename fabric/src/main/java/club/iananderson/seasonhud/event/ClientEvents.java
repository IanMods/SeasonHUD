package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.SeasonHUDScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientEvents {
  //Key Bindings
  public static void onKeyInput() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
        SeasonHUDScreen.open();
      }
    });
  }

  public static void onKeyRegister() {
    KeyBindings.seasonhudOptionsKeyMapping = KeyBindingHelper.registerKeyBinding(
        KeyBindings.seasonhudOptionsKeyMapping);
  }

  public static void register() {
    onKeyRegister();
    onKeyInput();
  }
}

