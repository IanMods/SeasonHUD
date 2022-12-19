package club.iananderson.seasonhud.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class SeasonHUDScreen extends Screen{
    private static final int COLUMNS = 2;
    private static final int MENU_PADDING_TOP = 50;
    private static final int PADDING = 6;
    private static final int BUTTON_WIDTH_FULL = 204;
    private static final int BUTTON_WIDTH_HALF = 98;

    private Screen seasonScreen;
    private Button showDay, showSubSeason, doneButton, cancelButton;

    public SeasonHUDScreen(Screen seasonScreen){
        super(Component.translatable("menu.title.seasonhud"));
        this.seasonScreen = seasonScreen;
    }

    public int getGuiScale(){
        return (int) minecraft.getWindow().getGuiScale();
    }
    public Font getFont() {
        return super.font;
    }

    public void render(PoseStack seasonGUI){
        showDay = Button.builder("menu.seasonhud.button.showDay",);
        showSubSeason = Button.builder("menu.seasonhud.button.showSubSeason",);

    }

    public boolean isPauseScreen() {
        return true;
    }

    public void init()
    {

    }
}
