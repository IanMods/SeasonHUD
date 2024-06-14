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
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class SeasonHUDScreen extends Screen {

  private static final int MENU_PADDING_FULL = 50;
  private static final int PADDING = 4;
  private static final int BUTTON_WIDTH = 180;
  private static final int BUTTON_HEIGHT = 20;
  private static final Component TITLE = new TranslatableComponent("menu.seasonhud.title");
  private static final Component JOURNEYMAP = new TranslatableComponent("menu.seasonhud.journeymap");
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

  @Override
  public boolean isPauseScreen() {
    return true;
  }

  private void onDone() {
    mc.options.save();
    mc.setScreen(null);
  }

  @Override
  public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
    super.renderBackground(stack);
    drawCenteredString(stack, font, TITLE, this.width / 2, PADDING, 16777215);
    if (Services.PLATFORM.isModLoaded("journeymap")) {
      drawCenteredString(stack, font, JOURNEYMAP, this.width / 2, MENU_PADDING_FULL + (5 * (BUTTON_HEIGHT + PADDING)),
          16777215);
    }
    super.render(stack, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    super.init();
    boolean isForge = Services.PLATFORM.getPlatformName().equals("Forge");
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
                                                          new TranslatableComponent("menu.seasonhud.button.enableMod"),
                                                          (b, press) -> Config.setEnableMod(press));

    MenuButton seasonhudColors = new MenuButton(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH,
        BUTTON_HEIGHT, MenuButtons.COLORS, (press) -> ColorScreen.open(this));

    row += 1;
    CycleButton<Location> hudLocationButton = CycleButton.builder(Location::getLocationName)
                                                         .withValues(Location.TOP_LEFT, Location.TOP_CENTER,
                                                             Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                                                             Location.BOTTOM_RIGHT).withInitialValue(hudLocation.get())
                                                         .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                             BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(
                                                                 "menu.seasonhud.button.hudLocation"),
                                                             (b, location) -> Config.setHudLocation(location));

    CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason.get())
                                                               .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                   BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(
                                                                       "menu.seasonhud.button.showTropicalSeason"),
                                                                   (b, Off) -> Config.setShowTropicalSeason(Off));

    row += 1;
    ShowDay[] showDayValuesForge = {ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS};
    ShowDay[] showDayValuesFabric = {ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS,
        ShowDay.SHOW_WITH_MONTH};

    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
                                                    .withValues(isForge ? showDayValuesForge : showDayValuesFabric)
                                                    .withInitialValue(showDay.get())
                                                    .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH,
                                                        BUTTON_HEIGHT,
                                                        new TranslatableComponent("menu.seasonhud.button.showDay"),
                                                        (b, showDay) -> Config.setShowDay(showDay));

    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason.get())
                                                          .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                              BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(
                                                                  "menu.seasonhud.button.showSubSeason"),
                                                              (b, Off) -> Config.setShowSubSeason(Off));

    row += 1;
    CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar.get())
                                                         .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                             BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(
                                                                 "menu.seasonhud.button.needCalendar"),
                                                             (b, Off) -> Config.setNeedCalendar(Off));

    CycleButton<Boolean> enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration.get())
                                                                     .create(rightButtonX,
                                                                         (buttonStartY + (row * yOffset)), BUTTON_WIDTH,
                                                                         BUTTON_HEIGHT, new TranslatableComponent(
                                                                             "menu.seasonhud.button.enableMinimapIntegration"),
                                                                         (b, Off) -> Config.setEnableMinimapIntegration(
                                                                             Off));

    row += 1;
    CycleButton<Boolean> showMinimapHiddenButton = CycleButton.onOffBuilder(showDefaultWhenMinimapHidden.get())
                                                              .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                  BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(
                                                                      "menu.seasonhud.button.showMinimapHidden"),
                                                                  (b, Off) -> Config.setShowDefaultWhenMinimapHidden(
                                                                      Off));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      CycleButton<Boolean> journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap.get())
                                                                 .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                     BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                     new TranslatableComponent(
                                                                         "menu.seasonhud.button.journeyMapAboveMap"),
                                                                     (b, Off) -> Config.setJourneyMapAboveMap(Off));

      CycleButton<Boolean> journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS.get())
                                                              .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                  BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(
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
}