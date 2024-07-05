package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
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
  private static final Component TITLE = Component.translatable("menu.seasonhud.title");
  private static final Component JOURNEYMAP = Component.translatable("menu.seasonhud.journeymap");
  private static final SeasonHUDScreen instance = new SeasonHUDScreen();
  private BasicSlider xOffsetSlider;
  private BasicSlider yOffsetSlider;

  public SeasonHUDScreen() {
    super(TITLE);
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
  public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(stack);
    stack.drawCenteredString(font, TITLE, this.width / 2, TITLE_PADDING, 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      stack.drawCenteredString(font, JOURNEYMAP, this.width / 2,
                               MENU_PADDING + (6 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                   + BUTTON_PADDING), 16777215);
    }

    super.render(stack, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    super.init();
    int leftButtonX = (this.width / 2) - (BUTTON_WIDTH + BUTTON_PADDING);
    int rightButtonX = (this.width / 2) + BUTTON_PADDING;
    int buttonStartY = MENU_PADDING;
    int yOffset = BUTTON_HEIGHT + BUTTON_PADDING;
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();

    //Buttons
    MenuButton doneButton = new MenuButton(this.width / 2 - BUTTON_WIDTH / 2,
                                           (this.height - BUTTON_HEIGHT - BUTTON_PADDING), BUTTON_WIDTH, BUTTON_HEIGHT,
                                           MenuButtons.DONE, press -> onDone());

    int row = 0;
    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(Config.getEnableMod())
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
                                                         .withInitialValue(Config.getHudLocation())
                                                         .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                 BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                 "menu.seasonhud.button.hudLocation"),
                                                                 (b, location) -> Config.setHudLocation(location));

    xOffsetSlider = new HudOffsetSlider(rightButtonX, (buttonStartY + (row * yOffset)),
                                        BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING, BUTTON_HEIGHT,
                                        Component.translatable("menu.seasonhud.slider.xoffset"), 0,
                                        mc.getWindow().getGuiScaledWidth() - mc.font.width(seasonCombined),
                                        Config.getHudX(), Config.defaultXOffset, true);

    yOffsetSlider = new HudOffsetSlider(rightButtonX + BUTTON_WIDTH / 2 + BasicSlider.SLIDER_PADDING,
                                        (buttonStartY + (row * yOffset)), BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING,
                                        BUTTON_HEIGHT, Component.translatable("menu.seasonhud.slider.yoffset"), 0,
                                        mc.getWindow().getGuiScaledHeight() - mc.font.lineHeight, Config.getHudY(),
                                        Config.defaultYOffset, true);

    row += 1;
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName).withValues(ShowDay.getValues())
                                                    .withInitialValue(Config.getShowDay())
                                                    .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH,
                                                            BUTTON_HEIGHT,
                                                            Component.translatable("menu.seasonhud.button.showDay"),
                                                            (b, showDay) -> Config.setShowDay(showDay));

    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(Config.getShowSubSeason())
                                                          .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                  BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                  "menu.seasonhud.button.showSubSeason"),
                                                                  (b, Off) -> Config.setShowSubSeason(Off));
    row += 1;
    CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(Config.getShowTropicalSeason())
                                                               .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                       BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                       Component.translatable(
                                                                           "menu.seasonhud.button.showTropicalSeason"),
                                                                       (b, Off) -> Config.setShowTropicalSeason(Off));

    CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(Config.getNeedCalendar())
                                                         .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                 BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable(
                                                                 "menu.seasonhud.button.needCalendar"),
                                                                 (b, Off) -> Config.setNeedCalendar(Off));

    row += 1;
    CycleButton<Boolean> showMinimapHiddenButton = CycleButton.onOffBuilder(Config.getShowDefaultWhenMinimapHidden())
                                                              .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                      BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                      Component.translatable(
                                                                          "menu.seasonhud.button.showMinimapHidden"),
                                                                      (b, Off) -> Config.setShowDefaultWhenMinimapHidden(
                                                                          Off));

    CycleButton<Boolean> enableMinimapIntegrationButton = CycleButton.onOffBuilder(Config.getEnableMinimapIntegration())
                                                                     .create(rightButtonX,
                                                                             (buttonStartY + (row * yOffset)),
                                                                             BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                             Component.translatable(
                                                                                 "menu.seasonhud.button.enableMinimapIntegration"),
                                                                             (b, Off) -> Config.setEnableMinimapIntegration(
                                                                                 Off));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      CycleButton<Boolean> journeyMapAboveMapButton = CycleButton.onOffBuilder(Config.getJourneyMapAboveMap())
                                                                 .create(leftButtonX, (buttonStartY + (row * yOffset)),
                                                                         BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                         Component.translatable(
                                                                             "menu.seasonhud.button.journeyMapAboveMap"),
                                                                         (b, Off) -> Config.setJourneyMapAboveMap(Off));

      CycleButton<Boolean> journeyMapMacOSButton = CycleButton.onOffBuilder(Config.getJourneyMapMacOS())
                                                              .create(rightButtonX, (buttonStartY + (row * yOffset)),
                                                                      BUTTON_WIDTH, BUTTON_HEIGHT,
                                                                      Component.translatable(
                                                                          "menu.seasonhud.button.journeyMapMacOS"),
                                                                      (b, Off) -> Config.setJourneyMapMacOS(Off));

      this.addRenderableWidget(journeyMapAboveMapButton);
      this.addRenderableWidget(journeyMapMacOSButton);
    }

    this.addRenderableWidget(enableModButton);
    this.addRenderableWidget(seasonhudColors);
    this.addRenderableWidget(hudLocationButton);
    this.addRenderableWidget(xOffsetSlider);
    this.addRenderableWidget(yOffsetSlider);
    this.addRenderableWidget(showDayButton);
    this.addRenderableWidget(showSubSeasonButton);
    this.addRenderableWidget(showTropicalSeasonButton);
    this.addRenderableWidget(needCalendarButton);
    this.addRenderableWidget(showMinimapHiddenButton);
    this.addRenderableWidget(enableMinimapIntegrationButton);
    this.addRenderableWidget(doneButton);
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }
}