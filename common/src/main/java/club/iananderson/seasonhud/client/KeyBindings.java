package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
  public static KeyMapping seasonhudOptionsKeyMapping = new KeyMapping("desc.seasonhud.keybind.options",
                                                                       InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H,
                                                                       "desc.seasonhud.keybind.category");

  private KeyBindings() {
  }
}


