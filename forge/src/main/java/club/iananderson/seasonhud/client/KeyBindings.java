package club.iananderson.seasonhud.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings.Type;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

  public static KeyBinding seasonhudOptionsKeyMapping;

  public static void init() {
    seasonhudOptionsKeyMapping = new KeyBinding("key.seasonhud.seasonhudOptions", KeyConflictContext.IN_GAME, Type.KEYSYM,
        GLFW.GLFW_KEY_H, "key.categories.seasonhud");
    ClientRegistry.registerKeyBinding(seasonhudOptionsKeyMapping);
  }
}

