package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
  public static KeyMapping seasonhudOptionsKeyMapping = new KeyMapping("key.seasonhud.options",
      InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, "key.seasonhud.category");

  private KeyBindings() {
  }
}


