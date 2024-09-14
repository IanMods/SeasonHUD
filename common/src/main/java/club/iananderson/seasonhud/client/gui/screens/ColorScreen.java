package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.buttons.DefaultColorButton;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.BlueSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.GreenSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.RedSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.RgbSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.util.Rgb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ColorScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.title.color");
  private static final Component ENABLE_SEASON_NAME_COLOR = Component.translatable(
      "menu.seasonhud.button.color.enableSeasonNameColor");
  private final List<ColorEditBox> seasonBoxes = new ArrayList<>();
  private final List<DefaultColorButton> defaultColorButtons = new ArrayList<>();
  private final List<RgbSlider> colorSliders = new ArrayList<>();
  private int x;
  private int y;
  private CycleButton<Boolean> seasonColorButton;

  public ColorScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    this.widgets.toArray().clone();
  }

  public static void open(Screen parentScreen) {
    Minecraft.getInstance().setScreen(new ColorScreen(parentScreen));
  }

  private static EnumSet<Seasons> seasonListSet() {
    EnumSet<Seasons> set = Seasons.SEASONS_ENUM_LIST.clone();

    if (!Config.getShowTropicalSeason() || !Common.platformName().equals("Forge")) {
      set.remove(Seasons.DRY);
      set.remove(Seasons.WET);
    }

    return set;
  }

  @Override
  public void onDone() {
    seasonBoxes.forEach(editBox -> {
      if (Integer.parseInt(editBox.getValue()) != editBox.getColor()) {
        editBox.save();
      }
    });

    seasonColorButton.getValue();

    super.onDone();
  }

  public int getBoxWidth() {
    int widgetCount = seasonListSet().size();
    int widgetTotalSize = ((80 + BUTTON_PADDING) * widgetCount);

    int boxWidth;
    if (this.width < widgetTotalSize) {
      boxWidth = 60;
    } else {
      boxWidth = 80;
    }

    return boxWidth;
  }

  public List<ColorEditBox> getSeasonBoxes() {
    return seasonBoxes;
  }

  public List<RgbSlider> getColorSliders() {
    return colorSliders;
  }

  public List<DefaultColorButton> getDefaultColorButtons() {
    return defaultColorButtons;
  }

  private List<AbstractWidget> seasonWidget(int x, int y, Seasons season) {
    ColorEditBox colorBox;
    RedSlider redSlider;
    GreenSlider greenSlider;
    BlueSlider blueSlider;
    DefaultColorButton defaultButton;

    colorBox = new ColorEditBox(this.font, x, y, getBoxWidth(), BUTTON_HEIGHT, season);
    y += colorBox.getHeight() + BUTTON_PADDING;

    x -= 1;
    y += BUTTON_HEIGHT + RgbSlider.SLIDER_PADDING;

    redSlider = new RedSlider(x, y, colorBox);
    y += redSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    greenSlider = new GreenSlider(x, y, colorBox);
    y += greenSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    blueSlider = new BlueSlider(x, y, colorBox);
    y -= (greenSlider.getHeight() + redSlider.getHeight() + RgbSlider.SLIDER_PADDING + BUTTON_HEIGHT
        + RgbSlider.SLIDER_PADDING);

    defaultButton = DefaultColorButton.builder(colorBox, press -> {
      int defaultColorInt = season.getDefaultColor();

      if (colorBox.getNewColor() != defaultColorInt) {
        redSlider.setSliderValue(defaultColorInt);
        greenSlider.setSliderValue(defaultColorInt);
        blueSlider.setSliderValue(defaultColorInt);
        colorBox.setValue(String.valueOf(defaultColorInt));

        Rgb.setRgb(season, defaultColorInt);
      }
    }).withPos(x, y).build();

    seasonBoxes.add(colorBox);
    defaultColorButtons.add(defaultButton);
    colorSliders.addAll(Arrays.asList(redSlider, blueSlider, greenSlider));

    return new ArrayList<>(Arrays.asList(colorBox, defaultButton, redSlider, blueSlider, greenSlider));
  }

  @Override
  public void init() {
    super.init();

    int MENU_PADDING_HALF = MENU_PADDING / 2;
    int widgetWidth = getBoxWidth() + BUTTON_PADDING;
    int totalWidgetWidth = (seasonListSet().size() * widgetWidth) - BUTTON_PADDING;

    this.x = (this.width / 2) - (totalWidgetWidth / 2);
    this.y = MENU_PADDING_HALF + BUTTON_HEIGHT + BUTTON_PADDING + BUTTON_HEIGHT;

    seasonListSet().forEach(season -> {
      this.widgets.addAll(seasonWidget(this.x, this.y, season));
      this.x += widgetWidth;
    });

    //Buttons
    seasonColorButton = CycleButton.onOffBuilder(Config.getEnableSeasonNameColor())
        .create(leftButtonX, MENU_PADDING_HALF, BUTTON_WIDTH, BUTTON_HEIGHT, ENABLE_SEASON_NAME_COLOR, (b, enable) -> {
          Config.setEnableSeasonNameColor(enable);
          rebuildWidgets();
        });

    this.widgets.add(seasonColorButton);
    this.widgets.forEach(this::addRenderableWidget);
  }

  @Override
  public void tick() {
    seasonBoxes.forEach(EditBox::tick);
    super.tick();
  }

}