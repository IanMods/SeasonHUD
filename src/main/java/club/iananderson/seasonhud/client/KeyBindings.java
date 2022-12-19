package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final String KEY_CATEGORIES_SEASONHUD = "key.categories.seasonhud";
    public static final String KEY_SEASONHUD_OPTIONS = "key.seasonhud.seasonhudOptions";

    public static final KeyMapping seasonhudOptionsKeyMapping= new KeyMapping(KEY_SEASONHUD_OPTIONS, KeyConflictContext.IN_GAME,
            InputConstants.getKey("key.keyboard.h"), KEY_CATEGORIES_SEASONHUD);
}

