package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class MainConfigScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.options.title");
  private static final Component JOURNEYMAP = Component.translatable("menu.seasonhud.options.journeymap.title");
  private final List<AbstractWidget> optionButtons = new ArrayList<>();
  private boolean enableMod;
  private Location hudLocation;
  private int xSliderInt;
  private int ySliderInt;
  private boolean showMinimapHidden;
  private boolean enableMinimapIntegration;
  private boolean needCalendar;
  private boolean journeyMapAboveMap;
  private boolean journeyMapMacOS;
  private CycleButton<Boolean> enableModButton;
  private CycleButton<Location> hudLocationButton;
  private HudOffsetSlider xSlider;
  private HudOffsetSlider ySlider;
  private CycleButton<Boolean> showMinimapHiddenButton;
  private CycleButton<Boolean> enableMinimapIntegrationButton;
  private CycleButton<Boolean> needCalendarButton;
  private CycleButton<Boolean> journeyMapAboveMapButton;
  private CycleButton<Boolean> journeyMapMacOSButton;

  public MainConfigScreen() {
    super(null, SCREEN_TITLE);
    loadConfig();
  }

  public static MainConfigScreen getInstance() {
    return new MainConfigScreen();
  }

  public void loadConfig() {
    enableMod = Config.getEnableMod();
    hudLocation = Config.getHudLocation();
    xSliderInt = Config.getHudX();
    ySliderInt = Config.getHudY();
    showMinimapHidden = Config.getShowDefaultWhenMinimapHidden();
    enableMinimapIntegration = Config.getEnableMinimapIntegration();
    needCalendar = Config.getNeedCalendar();
    if (CurrentMinimap.journeyMapLoaded()) {
      journeyMapAboveMap = Config.getEnableMod();
      journeyMapMacOS = Config.getEnableMod();
    }
  }

  public void saveConfig() {
    Config.setEnableMod(enableMod);
    Config.setHudLocation(hudLocation);
    Config.setHudX(xSliderInt);
    Config.setHudY(ySliderInt);
    Config.setShowDefaultWhenMinimapHidden(showMinimapHidden);
    Config.setNeedCalendar(needCalendar);
    if (CurrentMinimap.journeyMapLoaded()) {
      Config.setEnableMod(journeyMapAboveMap);
      Config.setEnableMod(journeyMapMacOS);
    }
  }

  @Override
  public void onDone() {
    saveConfig();
    super.onDone();
  }

  @Override
  public void onClose() {
    Config.setEnableMinimapIntegration(enableMinimapIntegration);
    super.onClose();
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      graphics.drawCenteredString(font, JOURNEYMAP, this.width / 2,
                                  MENU_PADDING + (6 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                      + BUTTON_PADDING), 16777215);
    }

    hudLocationButton.active = Common.drawDefaultHud();
    xSlider.active = hudLocationButton.getValue() == Location.TOP_LEFT && Common.drawDefaultHud();
    ySlider.active = hudLocationButton.getValue() == Location.TOP_LEFT && Common.drawDefaultHud();
  }

  @Override
  public void init() {
    super.init();

    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();
    double componentWidth = this.font.width(seasonCombined);
    double componentHeight = this.font.lineHeight;

    int row = 0;
    enableModButton = CycleButton.onOffBuilder(enableMod)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMod")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMod"), (b, val) -> enableMod = val);

    enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration)
        .withTooltip(
            object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMinimapIntegration")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMinimapIntegration"),
                (b, val) -> Config.setEnableMinimapIntegration(val));

    row = 1;
    hudLocationButton = CycleButton.builder(Location::getLocationName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.hudLocation")))
        .withValues(Location.TOP_LEFT, Location.TOP_CENTER, Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                    Location.BOTTOM_RIGHT).withInitialValue(hudLocation)
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.hudLocation"), (b, val) -> hudLocation = val);

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

    row = 2;
    MenuButton seasonButton = MenuButton.builder(MenuButtons.SEASON, b -> SeasonOptionsScreen.getInstance(this).open())
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.color")))
        .withPos(leftButtonX, (buttonStartY + (row * yOffset)))
        .withWidth(BUTTON_WIDTH)
        .build();

    MenuButton colorButton = MenuButton.builder(MenuButtons.COLORS, b -> ColorScreen.getInstance(this).open())
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.color")))
        .withPos(rightButtonX, (buttonStartY + (row * yOffset)))
        .withWidth(BUTTON_WIDTH)
        .build();

    row = 3;
    showMinimapHiddenButton = CycleButton.onOffBuilder(showMinimapHidden)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showMinimapHidden")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showMinimapHidden"), (b, val) -> showMinimapHidden = val);

    needCalendarButton = CycleButton.onOffBuilder(needCalendar)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.needCalendar")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.needCalendar"), (b, val) -> needCalendar = val);

    widgets.addAll(Arrays.asList(enableModButton, enableMinimapIntegrationButton, hudLocationButton, xSlider, ySlider,
                                 seasonButton, colorButton, showMinimapHiddenButton, needCalendarButton));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2; //6
      journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap)
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapAboveMap")))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapAboveMap"),
                  (b, val) -> journeyMapAboveMap = val);

      journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS)
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapMacOS")))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapMacOS"), (b, val) -> journeyMapMacOS = val);

      widgets.add(journeyMapAboveMapButton);
      widgets.add(journeyMapMacOSButton);
    }

    widgets.forEach(this::addRenderableWidget);
  }
}