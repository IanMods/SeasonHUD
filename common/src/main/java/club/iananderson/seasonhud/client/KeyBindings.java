package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
  public static final String KEY_SEASONHUD_OPTIONS = "key.seasonhud.seasonhudOptions";
  public static final String KEY_CATEGORIES_SEASONHUD = "key.categories.seasonhud";
  public static KeyMapping seasonhudOptionsKeyMapping = new KeyMapping(KEY_SEASONHUD_OPTIONS,
                                                                       InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H,
                                                                       KEY_CATEGORIES_SEASONHUD);

  private KeyBindings() {
  }
}


