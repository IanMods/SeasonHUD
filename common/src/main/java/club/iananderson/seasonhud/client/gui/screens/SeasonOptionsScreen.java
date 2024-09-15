package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import java.util.Arrays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class SeasonOptionsScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.options.season.title");
  private Location hudLocation;
  private int xSliderInt;
  private int ySliderInt;
  private ShowDay showDay;
  private boolean seasonColor;
  private boolean showSubSeason;
  private boolean showTropicalSeason;
  private CycleButton<Location> hudLocationButton;
  private HudOffsetSlider xSlider;
  private HudOffsetSlider ySlider;

  public SeasonOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
  }

  public static SeasonOptionsScreen getInstance(Screen parentScreen) {
    return new SeasonOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    hudLocation = Config.getHudLocation();
    xSliderInt = Config.getHudX();
    ySliderInt = Config.getHudY();
    showDay = Config.getShowDay();
    seasonColor = Config.getEnableSeasonNameColor();
    showSubSeason = Config.getShowSubSeason();
    showTropicalSeason = Config.getShowTropicalSeason();
  }

  public void saveConfig() {
    Config.setHudX(xSlider.getValueInt());
    Config.setHudY(ySlider.getValueInt());
  }

  @Override
  public void onDone() {
    saveConfig();
    super.onDone();
  }

  @Override
  public void onClose() {
    Config.setHudLocation(hudLocation);
    Config.setShowDay(showDay);
    Config.setEnableSeasonNameColor(seasonColor);
    Config.setShowSubSeason(showSubSeason);
    Config.setShowTropicalSeason(showTropicalSeason);
    super.onClose();
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    xSlider.active = hudLocationButton.getValue() == Location.TOP_LEFT;
    ySlider.active = hudLocationButton.getValue() == Location.TOP_LEFT;

    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();

    int componentWidth = this.font.width(seasonCombined);
    int componentHeight = this.font.lineHeight;
    int DEFAULT_X_OFFSET = Config.DEFAULT_X_OFFSET;
    int DEFAULT_Y_OFFSET = Config.DEFAULT_Y_OFFSET;
    int x = 0;
    int y = 0;

    switch (Config.getHudLocation()) {
      case TOP_LEFT:
        x = xSlider.getValueInt();
        y = ySlider.getValueInt();
        break;

      case TOP_CENTER:
        x = (width / 2) - (componentWidth / 2);
        y = DEFAULT_Y_OFFSET;
        break;

      case TOP_RIGHT:
        x = width - componentWidth - 2;
        y = DEFAULT_Y_OFFSET;
        break;

      case BOTTOM_LEFT:
        x = DEFAULT_X_OFFSET;
        y = height - componentHeight - DEFAULT_Y_OFFSET;
        break;

      case BOTTOM_RIGHT:
        x = width - componentWidth - DEFAULT_X_OFFSET;
        y = height - componentHeight - DEFAULT_Y_OFFSET;
        break;
    }

    graphics.pose().pushPose();
    graphics.pose().translate(1, 1, 50);
    graphics.drawString(font, seasonCombined, x, y, 0xffffff);
    graphics.pose().popPose();

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    super.init();

    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();
    double componentWidth = this.font.width(seasonCombined);
    double componentHeight = this.font.lineHeight;

    row = 0;
    hudLocationButton = CycleButton.builder(Location::getLocationName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.hudLocation")))
        .withValues(Location.TOP_LEFT, Location.TOP_CENTER, Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                    Location.BOTTOM_RIGHT)
        .withInitialValue(hudLocation)
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.hudLocation"), (b, val) -> Config.setHudLocation(val));

    xSlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud.slider.xOffset"))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.xOffset")))
        .withValueRange(0, this.width - componentWidth)
        .withInitialValue(xSliderInt)
        .withDefaultValue(Config.DEFAULT_X_OFFSET)
        .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING,
                    BUTTON_HEIGHT)
        .build();

    ySlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud" + ".slider.yOffset"))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.yOffset")))
        .withValueRange(0, this.height - componentHeight)
        .withInitialValue(ySliderInt)
        .withDefaultValue(Config.DEFAULT_Y_OFFSET)
        .withBounds(rightButtonX + BUTTON_WIDTH / 2 + BasicSlider.SLIDER_PADDING, (buttonStartY + (row * yOffset)),
                    BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING, BUTTON_HEIGHT)
        .build();

    row = 1;
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showDay")))
        .withValues(ShowDay.getValues())
        .withInitialValue(showDay)
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showDay"), (b, val) -> Config.setShowDay(val));

    CycleButton<Boolean> seasonColorButton = CycleButton.onOffBuilder(seasonColor)
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.color.enableSeasonNameColor"),
                (b, val) -> Config.setEnableSeasonNameColor(val));

    row = 2;
    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showSubSeason")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showSubSeason"),
                (b, val) -> Config.setShowSubSeason(val));

    CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showTropicalSeason")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showTropicalSeason"),
                (b, val) -> Config.setShowTropicalSeason(val));

    widgets.addAll(
        Arrays.asList(hudLocationButton, xSlider, ySlider, showDayButton, seasonColorButton, showSubSeasonButton,
                      showTropicalSeasonButton));

    widgets.forEach(this::addRenderableWidget);
  }
}