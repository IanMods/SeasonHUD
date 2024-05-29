package club.iananderson.seasonhud.client.gui.screens;


import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.client.gui.components.ColorButton;
import club.iananderson.seasonhud.client.gui.components.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.sliders.BlueSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.GreenSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.RedSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.RgbSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.Rgb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ColorScreen extends Screen {

  private int x;
  private int y;
  private int widgetHeight;
  private static final int MENU_PADDING_FULL = 50;
  private static final int PADDING = 6;
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BOX_PADDING = ColorEditBox.PADDING;
  private static final int BOX_WIDTH = ColorEditBox.BOX_WIDTH;
  private static final int BOX_HEIGHT = ColorEditBox.BOX_HEIGHT;
  private static final int SLIDER_PADDING = 2;
  private static final int DEFAULT_BUTTON_HEIGHT = ColorButton.BUTTON_HEIGHT;
  private static final int DEFAULT_BUTTON_ROW_SIZE = DEFAULT_BUTTON_HEIGHT + PADDING;
  private static final int WIDGET_START_Y =
      MENU_PADDING_FULL + DEFAULT_BUTTON_ROW_SIZE + BOX_PADDING + BOX_HEIGHT;
  private final Screen lastScreen;
  public static Button doneButton;
  public static Button cancelButton;
  public static CycleButton<Boolean> seasonNameColorButton;
  private static final Component TITLE = Component.translatable("menu.seasonhud.color.title");
  private static final Component ENABLE_SEASON_NAME_COLOR = Component.translatable(
      "menu.seasonhud.color.button.enableSeasonNameColor");
  private final List<ColorEditBox> seasonBoxes = new ArrayList<>();
  private final List<AbstractWidget> widgets = new ArrayList<>();


  public ColorScreen(Screen screen) {
    super(TITLE);
    this.lastScreen = screen;
  }

  public static void open(Screen screen) {
    mc.setScreen(new ColorScreen(screen));
  }

  public boolean isPauseScreen() {
    return true;
  }

  public int getWidth() {
    return mc.getWindow().getGuiScaledWidth();
  }

  public int getHeight() {
    return mc.getWindow().getGuiScaledHeight();
  }

  @Override
  public void tick() {
    seasonBoxes.forEach(EditBox::tick);
    super.tick();
  }

  private void onDone() {
    seasonBoxes.forEach(colorEditBox -> {
      if (Integer.parseInt(colorEditBox.getValue()) != colorEditBox.getColor()) {
        colorEditBox.save();
      }
    });

    mc.setScreen(this.lastScreen);
  }

  private void onCancel() {
    mc.setScreen(this.lastScreen);
  }

  private List<SeasonList> seasons() {
    List<SeasonList> seasons = new ArrayList<>(
        Arrays.asList(SeasonList.SPRING, SeasonList.SUMMER, SeasonList.AUTUMN, SeasonList.WINTER));

    if (Config.showTropicalSeason.get() && Services.PLATFORM.getPlatformName().equals("Forge")) {
      seasons.add(SeasonList.DRY);
      seasons.add(SeasonList.WET);
    }

    return seasons;
  }


  //Todo fix size for small screens
  private List<AbstractWidget> seasonWidget(int x, int y, SeasonList season) {
    ColorEditBox colorBox;
    RedSlider redSlider;
    GreenSlider greenSlider;
    BlueSlider blueSlider;
    List<AbstractWidget> seasonWidgetList = new ArrayList<>();

    colorBox = new ColorEditBox(this.font, x, y, season);
    seasonBoxes.add(colorBox);
    seasonWidgetList.add(colorBox);
    y += ColorEditBox.BOX_HEIGHT + PADDING;

    x -= 1;
    y += ColorButton.BUTTON_HEIGHT + SLIDER_PADDING;

    redSlider = new RedSlider(x, y, colorBox);
    seasonWidgetList.add(redSlider);
    y += RgbSlider.SLIDER_HEIGHT + SLIDER_PADDING;

    greenSlider = new GreenSlider(x, y, colorBox);
    seasonWidgetList.add(greenSlider);
    y += RgbSlider.SLIDER_HEIGHT + SLIDER_PADDING;

    blueSlider = new BlueSlider(x, y, colorBox);
    seasonWidgetList.add(blueSlider);

    y -= (2 * (RgbSlider.SLIDER_HEIGHT + SLIDER_PADDING) + (ColorButton.BUTTON_HEIGHT
        + SLIDER_PADDING));
    seasonWidgetList.add(new ColorButton(x, y, season, colorBox, button -> {
      int defaultColorInt = season.getDefaultColor();

      if (colorBox.getNewColor() != defaultColorInt) {
        redSlider.setSliderValue(defaultColorInt);
        greenSlider.setSliderValue(defaultColorInt);
        blueSlider.setSliderValue(defaultColorInt);
        colorBox.setValue(String.valueOf(defaultColorInt));

        Rgb.setRgb(season, defaultColorInt);
      }
    }));

    return seasonWidgetList;
  }

  @Override
  public void init() {
    this.widgets.clear();

    int BUTTON_X_LEFT = (getWidth() / 2) - (BUTTON_WIDTH + PADDING);
    int BUTTON_X_RIGHT = (getWidth() / 2) + PADDING;
    int WIDGET_WIDTH = BOX_WIDTH + PADDING;
    int TOP_WIDGET_WIDTH = (4 * WIDGET_WIDTH) - PADDING;
    int BOTTOM_WIDGET_WIDTH = (2 * WIDGET_WIDTH) - PADDING;

    widgetHeight = BOX_HEIGHT + PADDING + (3 * SLIDER_PADDING);
    seasonWidget(0, 0, SeasonList.SPRING).forEach(widget -> {
      widgetHeight += widget.getHeight();
    });

    this.x = this.getWidth() / 2 - TOP_WIDGET_WIDTH / 2;
    this.y = WIDGET_START_Y;
    this.seasons().forEach(season -> {
      if (season.getId() <= 3) {
        this.widgets.addAll(seasonWidget(this.x, this.y, season));
        this.x += WIDGET_WIDTH;
      } else {
        this.y = WIDGET_START_Y + widgetHeight;
        this.widgets.addAll(
            seasonWidget(this.x - TOP_WIDGET_WIDTH / 2 - BOTTOM_WIDGET_WIDTH / 2 - PADDING, this.y,
                season));
        this.x += WIDGET_WIDTH;
      }

    });

    //Buttons
    seasonNameColorButton = CycleButton.onOffBuilder(Config.enableSeasonNameColor.get())
        .create(BUTTON_X_LEFT, MENU_PADDING_FULL, BUTTON_WIDTH, BUTTON_HEIGHT,
            ENABLE_SEASON_NAME_COLOR,
            (b, enableColor) -> Config.setEnableSeasonNameColor(enableColor));
    this.widgets.add(seasonNameColorButton);

    CycleButton<Boolean> dummy = CycleButton.onOffBuilder(Config.enableSeasonNameColor.get())
        .create(BUTTON_X_RIGHT, MENU_PADDING_FULL, BUTTON_WIDTH, BUTTON_HEIGHT,
            ENABLE_SEASON_NAME_COLOR,
            (b, enableColor) -> Config.setEnableSeasonNameColor(enableColor));
    this.widgets.add(dummy);

    doneButton = Button.builder(CommonComponents.GUI_DONE, button -> onDone())
        .bounds(BUTTON_X_LEFT, (getHeight() - BUTTON_HEIGHT - PADDING), BUTTON_WIDTH, BUTTON_HEIGHT)
        .build();
    this.widgets.add(doneButton);

    cancelButton = Button.builder(CommonComponents.GUI_CANCEL, button -> this.onCancel())
        .bounds(BUTTON_X_RIGHT, (getHeight() - BUTTON_HEIGHT - PADDING), BUTTON_WIDTH,
            BUTTON_HEIGHT)
        .build();
    this.widgets.add(cancelButton);

    this.widgets.forEach(this::addRenderableWidget);
  }

  @Override
  public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics);
    guiGraphics.drawCenteredString(font, TITLE, getWidth() / 2, PADDING, 16777215);

    super.render(guiGraphics, mouseX, mouseY, partialTicks);
  }
}