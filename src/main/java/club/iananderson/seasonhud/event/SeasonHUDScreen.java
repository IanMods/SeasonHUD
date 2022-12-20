package club.iananderson.seasonhud.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class SeasonHUDScreen extends Screen{
    private static final int COLUMNS = 2;
    private static final int MENU_PADDING_FULL = 50;
    private static final int MENU_PADDING_HALF = MENU_PADDING_FULL/2;
    private static final int PADDING = 8;
    private static final int BUTTON_WIDTH_FULL = 200;
    private static final int BUTTON_WIDTH_HALF = BUTTON_WIDTH_FULL/2;
    private static final int BUTTON_HEIGHT = 20;
    public static Screen seasonScreen;

    private final Screen lastScreen;
    private CycleButton<Boolean> showDay, showSubSeason;
    private boolean showDayInHUD = true;
    private boolean showSubSeasonInHUD = true;
    private Button doneButton, cancelButton;

    private static final Component title = Component.translatable("menu.seasonhud.title");

    public SeasonHUDScreen(Screen seasonScreen){
        super(title);
        this.lastScreen = seasonScreen;
    }

    public int getGuiScale(){
        return (int) minecraft.getWindow().getGuiScale();
    }
    public Font getFont() {
        return super.font;
    }

    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks){
        this.renderDirtBackground(0);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawCenteredString(stack, this.font, this.title, this.width / 2, PADDING, 16777215);

    }

    public boolean isPauseScreen() {
        return true;
    }


    public void init() {
        super.init();

        //Buttons
        showDay = CycleButton.onOffBuilder(this.showDayInHUD).create(this.width / 2 - BUTTON_WIDTH_FULL+PADDING, MENU_PADDING_FULL, BUTTON_WIDTH_FULL, BUTTON_HEIGHT,Component.translatable("menu.seasonhud.button.showDay"),
            (True,False) -> {
                this.showDayInHUD = false;
                System.out.println(this.showDayInHUD);
        });

        showSubSeason = CycleButton.onOffBuilder(this.showSubSeasonInHUD).create(this.width / 2 - BUTTON_WIDTH_FULL+PADDING, (MENU_PADDING_FULL+BUTTON_HEIGHT+PADDING), BUTTON_WIDTH_FULL, BUTTON_HEIGHT,Component.translatable("menu.seasonhud.button.showSubSeason"),
            (True,False)-> {
                this.showSubSeasonInHUD = false;
                System.out.println(this.showSubSeasonInHUD);
        });

        doneButton = Button.builder(Component.translatable("menu.seasonhud.button.done"),b -> {
            this.minecraft.options.save();
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(this.width / 2 + PADDING, this.height - MENU_PADDING_HALF, BUTTON_WIDTH_HALF, BUTTON_HEIGHT).build();

        cancelButton = Button.builder(Component.translatable("menu.seasonhud.button.cancel"),b -> {
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(this.width / 2 - (PADDING+BUTTON_WIDTH_HALF), this.height - MENU_PADDING_HALF, BUTTON_WIDTH_HALF, BUTTON_HEIGHT).build();

        addRenderableWidget(showDay);
        addRenderableWidget(showSubSeason);

        addRenderableWidget(doneButton);
        addRenderableWidget(cancelButton);
    }
}
