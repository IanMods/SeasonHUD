//package club.iananderson.seasonhud.event;
//
//
//import club.iananderson.seasonhud.config.ModConfig;
//import club.iananderson.seasonhud.config.Location;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.widget.CyclingButtonWidget;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.text.Text;
//
//import static club.iananderson.seasonhud.config.ModConfig.*;
//
//public class SeasonHUDScreen extends Screen{
//    //private static final int COLUMNS = 2;
//    private static final int MENU_PADDING_FULL = 50;
//    private static final int MENU_PADDING_HALF = MENU_PADDING_FULL/2;
//    private static final int PADDING = 4;
//    private static final int BUTTON_WIDTH_FULL = 200;
//    private static final int BUTTON_WIDTH_HALF = 150;
//    private static final int BUTTON_HEIGHT = 20;
//    public static Screen seasonScreen;
//
//    private final Screen lastScreen;
//
//    private static final Text TITLE = Text.translatable("menu.seasonhud.title");
//
//    public SeasonHUDScreen(Screen seasonScreen){
//        super(TITLE);
//        this.lastScreen = seasonScreen;
//    }
//    public boolean isPauseScreen() {
//        return true;
//    }
//
//    @Override
//    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks){
//        this.renderBackgroundTexture(0);
//        drawCenteredText(stack, font, TITLE, this.width / 2, PADDING, 16777215);
//        super.render(stack, mouseX, mouseY, partialTicks);
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        MinecraftClient mc = MinecraftClient.getInstance();
//
//        int BUTTON_START_X_LEFT = (this.width/2) - (BUTTON_WIDTH_HALF+PADDING);
//        int BUTTON_START_X_RIGHT = (this.width/2) + PADDING;
//        int BUTTON_START_Y = MENU_PADDING_FULL;
//        int y_OFFSET = BUTTON_HEIGHT + PADDING;
//
//        Location defaultLocation = INSTANCE.hudLocation;
//
//        //Buttons
//
//        int row = 0;
//        CyclingButtonWidget<Boolean> enableModButton = CyclingButtonWidget.onOffBuilder(INSTANCE.enableMod)
//                .create(BUTTON_START_X_LEFT, (BUTTON_START_Y + (row*y_OFFSET)), BUTTON_WIDTH_HALF, BUTTON_HEIGHT,
//                        Component.translatable("menu.seasonhud.button.enableMod"),
//                        (b, Off) -> ModConfig.setEnableMod(Off));
//
//        CyclingButtonWidget<Location> hudLocationButton = CyclingButtonWidget.builder(Location::getLocationName)
//                .withValues(Location.TOP_LEFT,Location.TOP_CENTER,Location.TOP_RIGHT,Location.BOTTOM_LEFT,Location.BOTTOM_RIGHT)
//                .withInitialValue(defaultLocation)
//                .create(BUTTON_START_X_RIGHT, (BUTTON_START_Y + (row*y_OFFSET)), BUTTON_WIDTH_HALF, BUTTON_HEIGHT,
//                        Component.translatable("menu.seasonhud.button.hudLocation"),
//                        (b, location) -> ModConfig.setHudLocation(location));
//
//
//        row = 1;
//        CyclingButtonWidget<Boolean> showDayButton = CyclingButtonWidget.onOffBuilder(showDay.get())
//                .create(BUTTON_START_X_LEFT, (BUTTON_START_Y + (row*y_OFFSET)), BUTTON_WIDTH_HALF, BUTTON_HEIGHT,
//                        Component.translatable("menu.seasonhud.button.showDay"),
//                        (b, Off) -> ModConfig.setShowDay(Off));
//
//        CyclingButtonWidget<Boolean> showSubSeasonButton = CyclingButtonWidget.onOffBuilder(showSubSeason.get())
//                .create(BUTTON_START_X_RIGHT, (BUTTON_START_Y + (row*y_OFFSET)), BUTTON_WIDTH_HALF, BUTTON_HEIGHT,
//                        Component.translatable("menu.seasonhud.button.showSubSeason"),
//                        (b, Off) -> ModConfig.setShowSubSeason(Off));
//
//        row = 2;
//        CyclingButtonWidget<Boolean> showTropicalSeasonButton = CyclingButtonWidget.onOffBuilder(showTropicalSeason.get())
//                .create(BUTTON_START_X_LEFT, (BUTTON_START_Y + (row*y_OFFSET)), BUTTON_WIDTH_HALF, BUTTON_HEIGHT,
//                        Component.translatable("menu.seasonhud.button.showTropicalSeason"),
//                        (b, Off) -> ModConfig.setShowTropicalSeason(Off));
//
//        CyclingButtonWidget<Boolean> needCalendarButton = CyclingButtonWidget.onOffBuilder(needCalendar.get())
//                .create(BUTTON_START_X_RIGHT, (BUTTON_START_Y + (row*y_OFFSET)), BUTTON_WIDTH_HALF, BUTTON_HEIGHT,
//                        Component.translatable("menu.seasonhud.button.needCalendar"),
//                        (b, Off) -> ModConfig.setNeedCalendar(Off));
//
//        ExtendedButton doneButton = new ExtendedButton((this.width/2 - (BUTTON_WIDTH_FULL/2)), (this.height - BUTTON_HEIGHT - PADDING), BUTTON_WIDTH_FULL, BUTTON_HEIGHT, Component.translatable("gui.done"), b -> {
//            mc.options.save();
//            mc.setScreen(this.lastScreen);
//        });
//
//        //ExtendedButton cancelButton = new ExtendedButton((this.width / 2 - (PADDING + BUTTON_WIDTH_HALF)), this.height - MENU_PADDING_HALF, BUTTON_WIDTH_HALF, BUTTON_HEIGHT, Component.translatable("gui.cancel"), b -> mc.setScreen(this.lastScreen));
//
//        addRenderableWidget(enableModButton);
//        addRenderableWidget(needCalendarButton);
//        addRenderableWidget(showDayButton);
//        addRenderableWidget(showSubSeasonButton);
//        addRenderableWidget(hudLocationButton);
//        addRenderableWidget(showTropicalSeasonButton);
//
//        addRenderableWidget(doneButton);
//        //addRenderableWidget(cancelButton);
//    }
//
//    public static void open(){
//        Minecraft.getInstance().setScreen(new SeasonHUDScreen(seasonScreen));
//    }
//
//}