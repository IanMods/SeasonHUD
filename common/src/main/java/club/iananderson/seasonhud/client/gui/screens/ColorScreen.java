package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.buttons.CycleButton;
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
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class ColorScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = new TranslatableComponent("menu.seasonhud.color.title");
  private final List<ColorEditBox> seasonBoxes = new ArrayList<>();
  private int x;
  private int y;
  private boolean seasonColor;

  public ColorScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    this.BUTTON_WIDTH = 170;
    loadConfig();
  }

  public static ColorScreen getInstance(Screen parentScreen) {
    return new ColorScreen(parentScreen);
  }

  private static EnumSet<Seasons> seasonListSet() {
    EnumSet<Seasons> set = Seasons.SEASONS_ENUM_LIST.clone();

    if (!Config.getShowTropicalSeason() || Common.fabricSeasonsLoaded()) {
      set.remove(Seasons.DRY);
      set.remove(Seasons.WET);
    }

    return set;
  }

  public void loadConfig() {
    seasonColor = Config.getEnableSeasonNameColor();
  }

  @Override
  public void onDone() {
    seasonBoxes.forEach(editBox -> {
      if (Integer.parseInt(editBox.getValue()) != editBox.getColor()) {
        editBox.save();
      }
    });

    super.onDone();
  }

  @Override
  public void onClose() {
    Config.setEnableSeasonNameColor(seasonColor);
    super.onClose();
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

    return new ArrayList<>(Arrays.asList(colorBox, defaultButton, redSlider, greenSlider, blueSlider));
  }

  @Override
  public void init() {
    super.init();

    int widgetWidth = getBoxWidth() + BUTTON_PADDING;
    int totalWidgetWidth = (seasonListSet().size() * widgetWidth) - BUTTON_PADDING;

    this.x = (this.width / 2) - (totalWidgetWidth / 2);
    this.y = MENU_PADDING + BUTTON_HEIGHT + BUTTON_PADDING + BUTTON_HEIGHT;

    seasonListSet().forEach(season -> {
      this.widgets.addAll(seasonWidget(this.x, this.y, season));
      this.x += widgetWidth;
    });

    //Buttons
    CycleButton<Boolean> seasonColorButton = CycleButton.onOffBuilder(Config.getEnableSeasonNameColor())
        .create(leftButtonX, MENU_PADDING, BUTTON_WIDTH, BUTTON_HEIGHT,
                new TranslatableComponent("menu.seasonhud.color.enableSeasonNameColor.button"), (b, val) -> {
              Config.setEnableSeasonNameColor(val);
              rebuildWidgets();
            });

    this.widgets.add(seasonColorButton);
    this.widgets.forEach(this::addButton);
  }

  @Override
  public void tick() {
    seasonBoxes.forEach(EditBox::tick);
    super.tick();
  }

}