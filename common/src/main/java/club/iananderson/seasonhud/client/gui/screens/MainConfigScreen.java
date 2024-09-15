package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class MainConfigScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.options.title");
  private static final Component JOURNEYMAP = Component.translatable("menu.seasonhud.options.journeymap.title");
  private final List<AbstractWidget> optionButtons = new ArrayList<>();
  private boolean enableMod;
  private boolean showMinimapHidden;
  private boolean enableMinimapIntegration;
  private boolean needCalendar;
  private boolean journeyMapAboveMap;
  private boolean journeyMapMacOS;

  public MainConfigScreen() {
    super(null, SCREEN_TITLE);
    loadConfig();
    this.BUTTON_WIDTH = 170;
  }

  public static MainConfigScreen getInstance() {
    return new MainConfigScreen();
  }

  public void loadConfig() {
    enableMod = Config.getEnableMod();
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
    Config.setEnableMinimapIntegration(enableMinimapIntegration);
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
  }

  @Override
  public void init() {
    super.init();

    int row = 0;
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

    row = 1;
    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMod")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMod"), (b, val) -> enableMod = val);

    CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.needCalendar")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.needCalendar"), (b, val) -> needCalendar = val);

    row = 2;
    CycleButton<Boolean> enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration)
        .withTooltip(
            object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.enableMinimapIntegration")))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.enableMinimapIntegration"),
                (b, val) -> enableMinimapIntegration = val);

    CycleButton<Boolean> showMinimapHiddenButton = CycleButton.onOffBuilder(showMinimapHidden)
        .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.showMinimapHidden")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.button.showMinimapHidden"), (b, val) -> showMinimapHidden = val);

    widgets.addAll(
        Arrays.asList(seasonButton, colorButton, enableModButton, needCalendarButton, enableMinimapIntegrationButton,
                      showMinimapHiddenButton));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2; //6
      CycleButton<Boolean> journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap)
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapAboveMap")))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapAboveMap"),
                  (b, val) -> journeyMapAboveMap = val);

      CycleButton<Boolean> journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS)
          .withTooltip(object -> Tooltip.create(Component.translatable("menu.seasonhud.tooltip.journeyMapMacOS")))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.button.journeyMapMacOS"), (b, val) -> journeyMapMacOS = val);

      widgets.add(journeyMapAboveMapButton);
      widgets.add(journeyMapMacOSButton);
    }

    widgets.forEach(this::addRenderableWidget);
  }
}