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

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.Location;
import club.iananderson.seasonhud.config.ShowDay;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
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

  public boolean isPauseScreen() {
    return true;
  }

  @Override
  public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(stack, mouseX, mouseY, partialTicks);
    stack.drawCenteredString(font, TITLE, this.width / 2, PADDING, 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      stack.drawCenteredString(font, JOURNEYMAP, this.width / 2,
          MENU_PADDING_FULL + (6 * (BUTTON_HEIGHT + PADDING)) - (font.lineHeight + PADDING), 16777215);
    }

    super.render(stack, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    super.init();
    boolean isForge = Services.PLATFORM.getPlatformName().equals("Forge");
    int BUTTON_X_LEFT = (this.width / 2) - (BUTTON_WIDTH + PADDING);
    int BUTTON_X_RIGHT = (this.width / 2) + PADDING;
    int BUTTON_START_Y = MENU_PADDING_FULL;
    int y_OFFSET = BUTTON_HEIGHT + PADDING;

    //Buttons
    Button doneButton = Button.builder(CommonComponents.GUI_DONE, button -> {
                                mc.options.save();
                                mc.setScreen(null);
                              }).bounds(this.width / 2 - BUTTON_WIDTH / 2, (this.height - BUTTON_HEIGHT - PADDING), BUTTON_WIDTH, BUTTON_HEIGHT)
                              .build();

    int row = 0;
    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod.get())
                                                      .create(BUTTON_X_LEFT, (BUTTON_START_Y + (row * y_OFFSET)),
                                                          BUTTON_WIDTH, BUTTON_HEIGHT,
                                                          Component.translatable("menu.seasonhud.button.enableMod"),
                                                          (b, Off) -> Config.setEnableMod(Off));

    Button seasonhudColors = Button.builder(Component.translatable("menu.seasonhud.color.title"),
        button -> ColorScreen.open(this)).bounds(BUTTON_X_RIGHT, (BUTTON_START_Y + (row * y_OFFSET)), BUTTON_WIDTH,
        BUTTON_HEIGHT).build();

    row += 1;
    CycleButton<Location> hudLocationButton = CycleButton.builder(Location::getLocationName)
                                                         .withValues(Location.TOP_LEFT, Location.TOP_CENTER,
                                                             Location.TOP_RIGHT, Location.BOTTOM_LEFT,
                                                             Location.BOTTOM_RIGHT).withInitialValue(hudLocation.get())
                                                         .create(BUTTON_X_LEFT, (BUTTON_START_Y + (row * y_OFFSET)),
                                                             BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                 "menu.seasonhud.button.hudLocation"),
                                                             (b, location) -> Config.setHudLocation(location));

    CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason.get())
                                                               .create(BUTTON_X_RIGHT,
                                                                   (BUTTON_START_Y + (row * y_OFFSET)), BUTTON_WIDTH,
                                                                   BUTTON_HEIGHT, Component.translatable(
                                                                       "menu.seasonhud.button.showTropicalSeason"),
                                                                   (b, Off) -> Config.setShowTropicalSeason(Off));

    row += 1;
    ShowDay[] showDayValuesForge = {ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS};
    ShowDay[] showDayValuesFabric = {ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS,
        ShowDay.SHOW_WITH_MONTH};

    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
                                                    .withValues(isForge ? showDayValuesForge : showDayValuesFabric)
                                                    .withInitialValue(showDay.get())
                                                    .create(BUTTON_X_LEFT, (BUTTON_START_Y + (row * y_OFFSET)),
                                                        BUTTON_WIDTH, BUTTON_HEIGHT,
                                                        Component.translatable("menu.seasonhud.button.showDay"),
                                                        (b, showDay) -> Config.setShowDay(showDay));

    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason.get())
                                                          .create(BUTTON_X_RIGHT, (BUTTON_START_Y + (row * y_OFFSET)),
                                                              BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                  "menu.seasonhud.button.showSubSeason"),
                                                              (b, Off) -> Config.setShowSubSeason(Off));

    row += 1;
    CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar.get())
                                                         .create(BUTTON_X_LEFT, (BUTTON_START_Y + (row * y_OFFSET)),
                                                             BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                 "menu.seasonhud.button.needCalendar"),
                                                             (b, Off) -> Config.setNeedCalendar(Off));

    CycleButton<Boolean> enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration.get())
                                                                     .create(BUTTON_X_RIGHT,
                                                                         (BUTTON_START_Y + (row * y_OFFSET)),
                                                                         BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                         Component.translatable(
                                                                             "menu.seasonhud.button.enableMinimapIntegration"),
                                                                         (b, Off) -> Config.setEnableMinimapIntegration(
                                                                             Off));

    row += 1;
    CycleButton<Boolean> showMinimapHiddenButton = CycleButton.onOffBuilder(showDefaultWhenMinimapHidden.get())
                                                              .create(BUTTON_X_LEFT,
                                                                  (BUTTON_START_Y + (row * y_OFFSET)), BUTTON_WIDTH,
                                                                  BUTTON_HEIGHT, Component.translatable(
                                                                      "menu.seasonhud.button.showMinimapHidden"),
                                                                  (b, Off) -> Config.setShowDefaultWhenMinimapHidden(
                                                                      Off));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      CycleButton<Boolean> journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap.get())
                                                                 .create(BUTTON_X_LEFT,
                                                                     (BUTTON_START_Y + (row * y_OFFSET)), BUTTON_WIDTH,
                                                                     BUTTON_HEIGHT, Component.translatable(
                                                                         "menu.seasonhud.button.journeyMapAboveMap"),
                                                                     (b, Off) -> Config.setJourneyMapAboveMap(Off));

      CycleButton<Boolean> journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS.get())
                                                              .create(BUTTON_X_RIGHT,
                                                                  (BUTTON_START_Y + (row * y_OFFSET)), BUTTON_WIDTH,
                                                                  BUTTON_HEIGHT, Component.translatable(
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