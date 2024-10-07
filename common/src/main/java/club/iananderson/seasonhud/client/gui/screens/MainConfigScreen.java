package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class MainConfigScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = new TranslatableComponent("menu.seasonhud.main.title");
  private static final Component MINIMAP_SETTINGS = new TranslatableComponent("menu.seasonhud.main.minimap.options");
  private static final Component JOURNEYMAP = new TranslatableComponent("menu.seasonhud.main.journeymap.title");
  private final List<AbstractWidget> optionButtons = new ArrayList<>();
  MenuButton seasonButton;
  MenuButton colorButton;
  CycleButton<Boolean> enableMinimapIntegrationButton;
  CycleButton<Boolean> showMinimapHiddenButton;
  CycleButton<Boolean> journeyMapAboveMapButton;
  CycleButton<Boolean> journeyMapMacOSButton;
  private boolean enableMod;
  private boolean showMinimapHidden;
  private boolean enableMinimapIntegration;
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
    if (CurrentMinimap.journeyMapLoaded()) {
      journeyMapAboveMap = Config.getJourneyMapAboveMap();
      journeyMapMacOS = Config.getJourneyMapMacOS();
    }
  }

  public void saveConfig() {
    Config.setEnableMod(enableMod);
    Config.setEnableMinimapIntegration(enableMinimapIntegration);
    Config.setShowDefaultWhenMinimapHidden(showMinimapHidden);
    if (CurrentMinimap.journeyMapLoaded()) {
      Config.setJourneyMapAboveMap(journeyMapAboveMap);
      Config.setJourneyMapMacOS(journeyMapMacOS);
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
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    GuiComponent.drawCenteredString(graphics, font, MINIMAP_SETTINGS, this.width / 2,
                                MENU_PADDING + (2 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                    + BUTTON_PADDING), 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      GuiComponent.drawCenteredString(graphics, font, JOURNEYMAP, this.width / 2,
                                      MENU_PADDING + (4 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                          + BUTTON_PADDING), 16777215);

      journeyMapAboveMapButton.active = enableMod;
      journeyMapMacOSButton.active = enableMod;
    }
    seasonButton.active = enableMod;
    colorButton.active = enableMod;
    enableMinimapIntegrationButton.active = enableMod;
    showMinimapHiddenButton.active = enableMod;
  }

  @Override
  public void init() {
    super.init();

    int enableModWidth = font.width(new TranslatableComponent("menu.seasonhud.main.enableMod.button").append(": OFF")) + 8;

    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
        .create(this.width - enableModWidth - TITLE_PADDING / 2, TITLE_PADDING / 2, enableModWidth, BUTTON_HEIGHT,
                new TranslatableComponent("menu.seasonhud.main.enableMod.button"), (b, val) -> enableMod = val);

    int row = 0;
    seasonButton = MenuButton.builder(MenuButtons.SEASON, b -> SeasonOptionsScreen.getInstance(this).open())
        .withPos(leftButtonX, (buttonStartY + (row * yOffset)))
        .withWidth(BUTTON_WIDTH)
        .build();

    colorButton = MenuButton.builder(MenuButtons.COLORS, b -> ColorScreen.getInstance(this).open())
        .withPos(rightButtonX, (buttonStartY + (row * yOffset)))
        .withWidth(BUTTON_WIDTH)
        .build();

    row = 2;
    enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration)
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                new TranslatableComponent("menu.seasonhud.main.enableMinimapIntegration.button"),
                (b, val) -> enableMinimapIntegration = val);

    showMinimapHiddenButton = CycleButton.onOffBuilder(showMinimapHidden)
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                new TranslatableComponent("menu.seasonhud.main.showMinimapHidden.button"),
                (b, val) -> showMinimapHidden = val);

    widgets.addAll(Arrays.asList(seasonButton, colorButton, enableModButton, enableMinimapIntegrationButton,
                                 showMinimapHiddenButton));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap)
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  new TranslatableComponent("menu.seasonhud.main.journeymap.aboveMap.button"),
                  (b, val) -> journeyMapAboveMap = val);

      journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS)
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  new TranslatableComponent("menu.seasonhud.main.journeymap.macOS.button"),
                  (b, val) -> journeyMapMacOS = val);

      widgets.add(journeyMapAboveMapButton);
      widgets.add(journeyMapMacOSButton);
    }

    widgets.forEach(this::addRenderableWidget);
  }
}