package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class SeasonHUDScreen extends Screen {
  private static final int MENU_PADDING = 50;
  private static final int TITLE_PADDING = 10;
  private static final int BUTTON_PADDING = 4;
  private static final int BUTTON_WIDTH = 180;
  private static final int BUTTON_HEIGHT = 20;
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.title");
  private static final Component JOURNEYMAP = Component.translatable("menu.seasonhud.title.journeymap");
  private static final SeasonHUDScreen instance = new SeasonHUDScreen();
  private final List<AbstractWidget> optionButtons = new ArrayList<>();
  CycleButton<Location> hudLocationButton;
  private HudOffsetSlider xOffsetSlider;
  private HudOffsetSlider yOffsetSlider;

  public SeasonHUDScreen() {
    super(SCREEN_TITLE);
  }

  public static SeasonHUDScreen getInstance() {
    return instance;
  }

  public static void open() {
    Minecraft.getInstance().setScreen(getInstance());
  }

  private void onDone() {
    Config.setHudX(xOffsetSlider.getValueInt());
    Config.setHudY(yOffsetSlider.getValueInt());
    Minecraft.getInstance().setScreen(null);
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    graphics.drawCenteredString(font, SCREEN_TITLE, this.width / 2, TITLE_PADDING, 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      graphics.drawCenteredString(font, JOURNEYMAP, this.width / 2,
                                  MENU_PADDING + (6 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                      + BUTTON_PADDING), 16777215);
    }
    hudLocationButton.active = Common.drawDefaultHud();
    xOffsetSlider.active = hudLocationButton.getValue() == Location.TOP_LEFT && Common.drawDefaultHud();
    yOffsetSlider.active = hudLocationButton.getValue() == Location.TOP_LEFT && Common.drawDefaultHud();

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    optionButtons.clear();
    Minecraft mc = Minecraft.getInstance();
    int leftButtonX = (this.width / 2) - (BUTTON_WIDTH + BUTTON_PADDING);
    int rightButtonX = (this.width / 2) + BUTTON_PADDING;
    int buttonStartY = MENU_PADDING;
    int yOffset = BUTTON_HEIGHT + BUTTON_PADDING;
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
    double scaledWidth = mc.getWindow().getGuiScaledWidth();
    double scaledHeight = mc.getWindow().getGuiScaledHeight();
    double componentWidth = mc.font.width(seasonCombined);
    double componentHeight = mc.font.lineHeight;

    super.init();
    //Buttons
    MenuButton doneButton = MenuButton.builder(MenuButtons.DONE, press -> onDone())
        .withPos(this.width / 2 - BUTTON_WIDTH / 2, this.height - BUTTON_HEIGHT - BUTTON_PADDING)
        .withWidth(BUTTON_WIDTH)
        .build();

    int row = 0;
    //noinspection ConstantValue
    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(Config.getEnableMod())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMod")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMod"), (b, set) -> Config.setEnableMod(set));

    //noinspection ConstantValue
    MenuButton colorButton = MenuButton.builder(MenuButtons.COLORS, press -> ColorScreen.open(this))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.color")))
        .withPos(rightButtonX, (buttonStartY + (row * yOffset))).withWidth(BUTTON_WIDTH)
        .build();

    row = 1;
    hudLocationButton = CycleButton.builder(Location::getLocationName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.hudLocation")))
        .withValues(Location.TOP_LEFT, Location.TOP_CENTER, Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                    Location.BOTTOM_RIGHT)
        .withInitialValue(Config.getHudLocation())
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.hudLocation"),
                (b, location) -> Config.setHudLocation(location));

    xOffsetSlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud.slider.xOffset"))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.xOffset")))
        .withValueRange(0, scaledWidth - componentWidth)
        .withInitialValue(Config.getHudX())
        .withDefaultValue(Config.DEFAULT_X_OFFSET)
        .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING,
                    BUTTON_HEIGHT)
        .build();

    yOffsetSlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud" + ".slider.yOffset"))
        .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.tooltip.yOffset")))
        .withValueRange(0, scaledHeight - componentHeight)
        .withInitialValue(Config.getHudY())
        .withDefaultValue(Config.DEFAULT_Y_OFFSET)
        .withBounds(rightButtonX + BUTTON_WIDTH / 2 + BasicSlider.SLIDER_PADDING, (buttonStartY + (row * yOffset)),
                    BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING, BUTTON_HEIGHT)
        .build();

    row = 2;
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showDay")))
        .withValues(ShowDay.getValues())
        .withInitialValue(Config.getShowDay())
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showDay"), (b, show) -> Config.setShowDay(show));

    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(Config.getShowSubSeason())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showSubSeason")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showSubSeason"),
                (b, show) -> Config.setShowSubSeason(show));

    row = 3;
    CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(Config.getShowTropicalSeason())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showTropicalSeason")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showTropicalSeason"),
                (b, show) -> Config.setShowTropicalSeason(show));

    CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(Config.getNeedCalendar())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.needCalendar")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.needCalendar"),
                (b, show) -> Config.setNeedCalendar(show));

    row = 4;
    CycleButton<Boolean> showMinimapHiddenButton = CycleButton.onOffBuilder(Config.getShowDefaultWhenMinimapHidden())
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showMinimapHidden")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showMinimapHidden"),
                (b, show) -> Config.setShowDefaultWhenMinimapHidden(show));

    CycleButton<Boolean> enableMinimapIntegrationButton = CycleButton.onOffBuilder(Config.getEnableMinimapIntegration())
        .withTooltip(
            object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMinimapIntegration")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMinimapIntegration"),
                (b, show) -> Config.setEnableMinimapIntegration(show));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2; //6
      CycleButton<Boolean> journeyMapAboveMapButton = CycleButton.onOffBuilder(Config.getJourneyMapAboveMap())
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapAboveMap")))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapAboveMap"),
                  (b, show) -> Config.setJourneyMapAboveMap(show));

      CycleButton<Boolean> journeyMapMacOSButton = CycleButton.onOffBuilder(Config.getJourneyMapMacOS())
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapMacOS")))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapMacOS"),
                  (b, show) -> Config.setJourneyMapMacOS(show));

      this.addRenderableWidget(journeyMapAboveMapButton);
      this.addRenderableWidget(journeyMapMacOSButton);
    }
    optionButtons.addAll(
        Arrays.asList(enableModButton, colorButton, hudLocationButton, xOffsetSlider, yOffsetSlider, showDayButton,
                      showSubSeasonButton, showTropicalSeasonButton, needCalendarButton, showMinimapHiddenButton,
                      enableMinimapIntegrationButton));

    optionButtons.forEach(this::addRenderableWidget);
    this.addRenderableWidget(doneButton);
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }
}