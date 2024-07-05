package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.buttons.DefaultColorButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
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
import java.util.EnumSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ColorScreen extends Screen {
  public static final int WIDGET_PADDING = 6;
  private static final int MENU_PADDING_FULL = 25;
  private static final int TITLE_PADDING = 10;
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 20;
  private static final Component TITLE = Component.translatable("menu.seasonhud.color.title");
  private static final Component ENABLE_SEASON_NAME_COLOR = Component.translatable(
      "menu.seasonhud.color.button.enableSeasonNameColor");
  public static MenuButton doneButton;
  private final Screen lastScreen;
  private final List<ColorEditBox> seasonBoxes = new ArrayList<>();
  private final List<DefaultColorButton> defaultColorButtons = new ArrayList<>();
  private final List<RgbSlider> colorSliders = new ArrayList<>();
  private final List<AbstractWidget> widgets = new ArrayList<>();
  private int x;
  private int y;

  public ColorScreen(Screen screen) {
    super(TITLE);
    this.lastScreen = screen;
    this.widgets.toArray().clone();
  }

  public static void open(Screen screen) {
    Minecraft.getInstance().setScreen(new ColorScreen(screen));
  }

  private static EnumSet<SeasonList> seasonListSet() {
    EnumSet<SeasonList> set = SeasonList.seasons.clone();

    if (!Config.getShowTropicalSeason() || !Services.PLATFORM.getPlatformName().equals("Forge")) {
      set.remove(SeasonList.DRY);
      set.remove(SeasonList.WET);
    }

    return set;
  }

  private void onDone() {
    seasonBoxes.forEach(seasonBoxes -> {
      if (Integer.parseInt(seasonBoxes.getValue()) != seasonBoxes.getColor()) {
        seasonBoxes.save();
      }
    });

    Minecraft.getInstance().setScreen(this.lastScreen);
  }

  private void onCancel() {
    Minecraft.getInstance().setScreen(this.lastScreen);
  }

  public int getWidth() {
    return Minecraft.getInstance().getWindow().getGuiScaledWidth();
  }

  public int getHeight() {
    return Minecraft.getInstance().getWindow().getGuiScaledHeight();
  }

  public int getBoxWidth() {
    int widgetCount = seasonListSet().size();
    int widgetTotalSize = ((80 + WIDGET_PADDING) * widgetCount);
    int scaledWidth = this.getWidth();

    int boxWidth;
    if (scaledWidth < widgetTotalSize) {
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

  private List<AbstractWidget> seasonWidget(int x, int y, SeasonList season) {
    ColorEditBox colorBox;
    RedSlider redSlider;
    GreenSlider greenSlider;
    BlueSlider blueSlider;
    DefaultColorButton defaultButton;

    colorBox = new ColorEditBox(this.font, x, y, getBoxWidth(), BUTTON_HEIGHT, season);
    y += colorBox.getHeight() + WIDGET_PADDING;

    x -= 1;
    y += BUTTON_HEIGHT + RgbSlider.SLIDER_PADDING;

    redSlider = new RedSlider(x, y, colorBox);
    y += redSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    greenSlider = new GreenSlider(x, y, colorBox);
    y += greenSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    blueSlider = new BlueSlider(x, y, colorBox);
    y -= (greenSlider.getHeight() + redSlider.getHeight() + RgbSlider.SLIDER_PADDING + BUTTON_HEIGHT
        + RgbSlider.SLIDER_PADDING);

    defaultButton = new DefaultColorButton(x, y, season, colorBox, button -> {
      int defaultColorInt = season.getDefaultColor();

      if (colorBox.getNewColor() != defaultColorInt) {
        redSlider.setSliderValue(defaultColorInt);
        greenSlider.setSliderValue(defaultColorInt);
        blueSlider.setSliderValue(defaultColorInt);
        colorBox.setValue(String.valueOf(defaultColorInt));

        Rgb.setRgb(season, defaultColorInt);
      }
    });

    seasonBoxes.add(colorBox);
    defaultColorButtons.add(defaultButton);
    colorSliders.addAll(Arrays.asList(redSlider, blueSlider, greenSlider));

    return new ArrayList<>(Arrays.asList(colorBox, defaultButton, redSlider, blueSlider, greenSlider));
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(graphics);
    graphics.drawCenteredString(font, TITLE, getWidth() / 2, TITLE_PADDING, 16777215);
    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    this.widgets.clear();
    int scaledWidth = this.getWidth();

    int leftButtonX = (getWidth() / 2) - (BUTTON_WIDTH + WIDGET_PADDING);
    int rightButtonX = (getWidth() / 2) + WIDGET_PADDING;
    int widgetWidth = getBoxWidth() + WIDGET_PADDING;
    int totalWidgetWidth = (seasonListSet().size() * widgetWidth) - WIDGET_PADDING;

    this.x = scaledWidth / 2 - totalWidgetWidth / 2;
    this.y = MENU_PADDING_FULL + BUTTON_HEIGHT + WIDGET_PADDING + BUTTON_HEIGHT;

    seasonListSet().forEach(season -> {
      this.widgets.addAll(seasonWidget(this.x, this.y, season));
      this.x += widgetWidth;
    });

    //Buttons
    CycleButton<Boolean> seasonColorButton = CycleButton.onOffBuilder(Config.getEnableSeasonNameColor())
                                                        .create(leftButtonX, MENU_PADDING_FULL, BUTTON_WIDTH,
                                                                BUTTON_HEIGHT, ENABLE_SEASON_NAME_COLOR,
                                                                (b, enable) -> {
                                                                  Config.setEnableSeasonNameColor(enable);
                                                                  this.rebuildWidgets();
                                                                });
    this.widgets.add(seasonColorButton);

    doneButton = new MenuButton(leftButtonX, (getHeight() - BUTTON_HEIGHT - WIDGET_PADDING), MenuButtons.DONE,
                                press -> this.onDone());
    this.widgets.add(doneButton);

    MenuButton cancelButton = new MenuButton(rightButtonX, (getHeight() - BUTTON_HEIGHT - WIDGET_PADDING),
                                             MenuButtons.CANCEL, press -> this.onCancel());
    this.widgets.add(cancelButton);

    this.widgets.forEach(this::addRenderableWidget);
  }

  @Override
  public void tick() {
    seasonBoxes.forEach(EditBox::tick);
    super.tick();
  }

  public boolean isPauseScreen() {
    return true;
  }
}