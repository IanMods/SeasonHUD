package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.buttons.DefaultColorButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.BlueSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.GreenSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.RedSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.rgb.RgbSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class ColorScreen extends Screen {
  public static final int WIDGET_PADDING = 6;
  private static final int MENU_PADDING_FULL = 25;
  private static final int TITLE_PADDING = 10;
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 20;
  private static final Component SCREEN_TITLE = new TranslatableComponent("menu.seasonhud.title.color");
  private static final Component ENABLE_SEASON_NAME_COLOR = new TranslatableComponent(
      "menu.seasonhud.button.color.enableSeasonNameColor");
  public static MenuButton doneButton;
  private final Screen lastScreen;
  private final List<ColorEditBox> seasonBoxes = new ArrayList<>();
  private final List<DefaultColorButton> defaultColorButtons = new ArrayList<>();
  private final List<RgbSlider> colorSliders = new ArrayList<>();
  private final List<AbstractWidget> widgets = new ArrayList<>();
  private int x;
  private int y;

  public ColorScreen(Screen screen) {
    super(SCREEN_TITLE);
    this.lastScreen = screen;
    this.widgets.toArray().clone();
  }

  public static void open(Screen screen) {
    Minecraft.getInstance().setScreen(new ColorScreen(screen));
  }

  private static EnumSet<Seasons> seasonListSet() {
    EnumSet<Seasons> set = Seasons.SEASONS_ENUM_LIST.clone();

    if (!Config.getShowTropicalSeason() || !Services.PLATFORM.getPlatformName().equals("Forge")) {
      set.remove(Seasons.DRY);
      set.remove(Seasons.WET);
    }

    return set;
  }

  private void onDone() {
    seasonBoxes.forEach(editBox -> {
      if (Integer.parseInt(editBox.getValue()) != editBox.getColor()) {
        editBox.save();
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

  private List<AbstractWidget> seasonWidget(int x, int y, Seasons season) {
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
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(graphics);
    drawCenteredString(graphics, font, SCREEN_TITLE, getWidth() / 2, TITLE_PADDING, 16777215);
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
                                                                  this.clearWidgets();
          this.init();
                                                                });

    doneButton = MenuButton.builder(MenuButtons.DONE, press -> this.onDone())
        .withPos(leftButtonX, (getHeight() - BUTTON_HEIGHT - WIDGET_PADDING))
        .build();

    MenuButton cancelButton = MenuButton.builder(MenuButtons.CANCEL, press -> this.onCancel())
        .withPos(rightButtonX, (getHeight() - BUTTON_HEIGHT - WIDGET_PADDING))
        .build();

    this.widgets.addAll(Arrays.asList(seasonColorButton, doneButton, cancelButton));
    this.widgets.forEach(this::addRenderableWidget);
  }

  @Override
  public void tick() {
    seasonBoxes.forEach(EditBox::tick);
    super.tick();
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }
}