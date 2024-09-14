package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
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
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.title");
  private static final Component JOURNEYMAP = Component.translatable("menu.seasonhud.title.journeymap");
  private final List<AbstractWidget> optionButtons = new ArrayList<>();
  private CycleButton<Boolean> enableModButton;
  private CycleButton<Location> hudLocationButton;
  private HudOffsetSlider xOffsetSlider;
  private HudOffsetSlider yOffsetSlider;
  private CycleButton<ShowDay> showDayButton;
  private CycleButton<Boolean> showSubSeasonButton;
  private CycleButton<Boolean> showTropicalSeasonButton;
  private CycleButton<Boolean> needCalendarButton;
  private CycleButton<Boolean> showMinimapHiddenButton;
  private CycleButton<Boolean> enableMinimapIntegrationButton;
  private CycleButton<Boolean> journeyMapAboveMapButton;
  private CycleButton<Boolean> journeyMapMacOSButton;

  public MainConfigScreen() {
    super(null, SCREEN_TITLE);
  }

  public static MainConfigScreen getInstance() {
    return new MainConfigScreen();
  }

  public void saveConfig() {
    Config.setEnableMod(enableModButton.getValue());
    Config.setHudLocation(hudLocationButton.getValue());
    Config.setHudX(xOffsetSlider.getValueInt());
    Config.setHudY(yOffsetSlider.getValueInt());
    Config.setShowDay(showDayButton.getValue());
    Config.setShowSubSeason(showSubSeasonButton.getValue());
    Config.setShowTropicalSeason(showTropicalSeasonButton.getValue());
    Config.setNeedCalendar(needCalendarButton.getValue());
    Config.setShowDefaultWhenMinimapHidden(showMinimapHiddenButton.getValue());
    Config.setEnableMinimapIntegration(enableMinimapIntegrationButton.getValue());
    if (CurrentMinimap.journeyMapLoaded()) {
      Config.setEnableMod(journeyMapAboveMapButton.getValue());
      Config.setEnableMod(journeyMapMacOSButton.getValue());
    }
  }

  @Override
  public void onDone() {
    saveConfig();
    super.onDone();
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
    xOffsetSlider.active = hudLocationButton.getValue() == Location.TOP_LEFT && Common.drawDefaultHud();
    yOffsetSlider.active = hudLocationButton.getValue() == Location.TOP_LEFT && Common.drawDefaultHud();
  }

  @Override
  public void init() {
    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();

    double componentWidth = this.font.width(seasonCombined);
    double componentHeight = this.font.lineHeight;

    super.init();

    int buttonStartY = MENU_PADDING;
    int yOffset = BUTTON_HEIGHT + BUTTON_PADDING;

    int row = 0;
    //noinspection ConstantValue
    enableModButton = CycleButton.onOffBuilder(Config.getEnableMod())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMod")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMod"));

    MenuButton colorButton = MenuButton.builder(MenuButtons.COLORS, press -> ColorScreen.open(this))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.color")))
        .withPos(rightButtonX, (buttonStartY + (row * yOffset)))
        .withWidth(BUTTON_WIDTH)
        .build();

    row = 1;
    hudLocationButton = CycleButton.builder(Location::getLocationName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.hudLocation")))
        .withValues(Location.TOP_LEFT, Location.TOP_CENTER, Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                    Location.BOTTOM_RIGHT)
        .withInitialValue(Config.getHudLocation())
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.hudLocation"));

    xOffsetSlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud.slider.xOffset"))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.xOffset")))
        .withValueRange(0, this.width - componentWidth)
        .withInitialValue(Config.getHudX())
        .withDefaultValue(Config.DEFAULT_X_OFFSET)
        .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING,
                    BUTTON_HEIGHT)
        .build();

    yOffsetSlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud" + ".slider.yOffset"))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.yOffset")))
        .withValueRange(0, this.height - componentHeight)
        .withInitialValue(Config.getHudY())
        .withDefaultValue(Config.DEFAULT_Y_OFFSET)
        .withBounds(rightButtonX + BUTTON_WIDTH / 2 + BasicSlider.SLIDER_PADDING, (buttonStartY + (row * yOffset)),
                    BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING, BUTTON_HEIGHT)
        .build();

    row = 2;
    showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showDay")))
        .withValues(ShowDay.getValues())
        .withInitialValue(Config.getShowDay())
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showDay"));

    showSubSeasonButton = CycleButton.onOffBuilder(Config.getShowSubSeason())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showSubSeason")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showSubSeason"));

    row = 3;
    showTropicalSeasonButton = CycleButton.onOffBuilder(Config.getShowTropicalSeason())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showTropicalSeason")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showTropicalSeason"));

    needCalendarButton = CycleButton.onOffBuilder(Config.getNeedCalendar())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.needCalendar")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.needCalendar"));

    row = 4;
    showMinimapHiddenButton = CycleButton.onOffBuilder(Config.getShowDefaultWhenMinimapHidden())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showMinimapHidden")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showMinimapHidden"));

    enableMinimapIntegrationButton = CycleButton.onOffBuilder(Config.getEnableMinimapIntegration())
        .withTooltip(
            object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMinimapIntegration")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMinimapIntegration"));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2; //6
      journeyMapAboveMapButton = CycleButton.onOffBuilder(Config.getJourneyMapAboveMap())
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapAboveMap")))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapAboveMap"));

      journeyMapMacOSButton = CycleButton.onOffBuilder(Config.getJourneyMapMacOS())
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapMacOS")))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapMacOS"));

      widgets.add(journeyMapAboveMapButton);
      widgets.add(journeyMapMacOSButton);
    }
    widgets.addAll(
        Arrays.asList(enableModButton, colorButton, hudLocationButton, xOffsetSlider, yOffsetSlider, showDayButton,
                      showSubSeasonButton, showTropicalSeasonButton, needCalendarButton, showMinimapHiddenButton,
                      enableMinimapIntegrationButton));

    widgets.forEach(this::addRenderableWidget);
  }
}