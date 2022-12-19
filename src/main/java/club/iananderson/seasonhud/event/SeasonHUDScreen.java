package club.iananderson.seasonhud.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class SeasonHUDScreen extends Screen{
    public SeasonHUDScreen(Screen seasonScreen){
        super(Component.translatable("key.categories.seasonhud"));
    }
}
