package club.iananderson.seasonhud.client.gui.screens;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;
import static club.iananderson.seasonhud.config.Config.enableMinimapIntegration;
import static club.iananderson.seasonhud.config.Config.enableMod;
import static club.iananderson.seasonhud.config.Config.hudLocation;
import static club.iananderson.seasonhud.config.Config.journeyMapAboveMap;
import static club.iananderson.seasonhud.config.Config.journeyMapMacOS;
import static club.iananderson.seasonhud.config.Config.needCalendar;
import static club.iananderson.seasonhud.config.Config.showDay;
import static club.iananderson.seasonhud.config.Config.showDefaultWhenMinimapHidden;
import static club.iananderson.seasonhud.config.Config.showSubSeason;
import static club.iananderson.seasonhud.config.Config.showTropicalSeason;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.Location;
import club.iananderson.seasonhud.config.ShowDay;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class SeasonHUDScreen extends Screen {

  private static final int MENU_PADDING_FULL = 50;
  private static final int PADDING = 4;
  private static final int BUTTON_WIDTH = 180;
  private static final int BUTTON_HEIGHT = 20;
  private static final Component TITLE = Component.translatable("menu.seasonhud.title");
  private static final Component JOURNEYMAP = Component.translatable("menu.seasonhud.journeymap");
  private static final SeasonHUDScreen instance = new SeasonHUDScreen();

  public SeasonHUDScreen() {
    super(TITLE);
  }

  public static SeasonHUDScreen getInstance() {
    return instance;
  }

  public static void open() {
    mc.setScreen(getInstance());
  }

  private void onDone() {
    mc.options.save();
    mc.setScreen(null);
  }

  @Override
  public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(stack);
    stack.drawCenteredString(font, TITLE, this.width / 2, PADDING, 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      stack.drawCenteredString(font, JOURNEYMAP, this.width / 2,
                               MENU_PADDING_FULL + (6 * (BUTTON_HEIGHT + PADDING)) - (font.lineHeight + PADDING),
                               16777215);
    }

    super.render(stack, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    super.init();
    int leftButtonX = (this.width / 2) - (BUTTON_WIDTH + PADDING);
    int rightButtonX = (this.width / 2) + PADDING;
    int buttonStartY = MENU_PADDING_FULL;
    int yOffset = BUTTON_HEIGHT + PADDING;

    //Buttons
    MenuButton doneButton = new MenuButton(this.width / 2 - BUTTON_WIDTH / 2, (this.height - BUTTON_HEIGHT - PADDING),
                                           BUTTON_WIDTH, BUTTON_HEIGHT, MenuButtons.DONE, press -> onDone());

    int row = 0;
    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod.get())
                                                      .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                              BUTTON_WIDTH, BUTTON_HEIGHT,
                                                              Component.translatable("menu.seasonhud.button.enableMod"),
                                                              (b, Off) -> Config.setEnableMod(Off));

    MenuButton seasonhudColors = new MenuButton(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH,
                                                BUTTON_HEIGHT, MenuButtons.COLORS, (press) -> ColorScreen.open(this));

    row += 1;
    CycleButton<Location> hudLocationButton = CycleButton.builder(Location::getLocationName)
                                                         .withValues(Location.TOP_LEFT, Location.TOP_CENTER,
                                                                     Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                                                                     Location.BOTTOM_RIGHT)
                                                         .withInitialValue(hudLocation.get())
                                                         .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                 BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                 "menu.seasonhud.button.hudLocation"),
                                                                 (b, location) -> Config.setHudLocation(location));

    CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason.get())
                                                               .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                       BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                       Component.translatable(
                                                                           "menu.seasonhud.button.showTropicalSeason"),
                                                                       (b, Off) -> Config.setShowTropicalSeason(Off));

    row += 1;
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName).withValues(ShowDay.getValues())
                                                    .withInitialValue(showDay.get())
                                                    .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH,
                                                            BUTTON_HEIGHT,
                                                            Component.translatable("menu.seasonhud.button.showDay"),
                                                            (b, showDay) -> Config.setShowDay(showDay));

    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason.get())
                                                          .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                  BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                  "menu.seasonhud.button.showSubSeason"),
                                                                  (b, Off) -> Config.setShowSubSeason(Off));

    row += 1;
    CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar.get())
                                                         .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                 BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                 "menu.seasonhud.button.needCalendar"),
                                                                 (b, Off) -> Config.setNeedCalendar(Off));

    CycleButton<Boolean> enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration.get())
                                                                     .create(rightButtonX,
                                                                             (buttonStartY + (row * yOffset)),
                                                                             BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                             Component.translatable(
                                                                                 "menu.seasonhud.button.enableMinimapIntegration"),
                                                                             (b, Off) -> Config.setEnableMinimapIntegration(
                                                                                 Off));

    row += 1;
    CycleButton<Boolean> showMinimapHiddenButton = CycleButton.onOffBuilder(showDefaultWhenMinimapHidden.get())
                                                              .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                      BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                      Component.translatable(
                                                                          "menu.seasonhud.button.showMinimapHidden"),
                                                                      (b, Off) -> Config.setShowDefaultWhenMinimapHidden(
                                                                          Off));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      CycleButton<Boolean> journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap.get())
                                                                 .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                         BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                         Component.translatable(
                                                                             "menu.seasonhud.button.journeyMapAboveMap"),
                                                                         (b, Off) -> Config.setJourneyMapAboveMap(Off));

      CycleButton<Boolean> journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS.get())
                                                              .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                      BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                      Component.translatable(
                                                                          "menu.seasonhud.button.journeyMapMacOS"),
                                                                      (b, Off) -> Config.setJourneyMapMacOS(Off));

      this.addRenderableWidget(journeyMapAboveMapButton);
      this.addRenderableWidget(journeyMapMacOSButton);
    }

    this.addRenderableWidget(enableModButton);
    this.addRenderableWidget(hudLocationButton);
    this.addRenderableWidget(showTropicalSeasonButton);
    this.addRenderableWidget(showSubSeasonButton);
    this.addRenderableWidget(showDayButton);
    this.addRenderableWidget(needCalendarButton);
    this.addRenderableWidget(enableMinimapIntegrationButton);
    this.addRenderableWidget(showMinimapHiddenButton);
    this.addRenderableWidget(seasonhudColors);
    this.addRenderableWidget(doneButton);
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }
}