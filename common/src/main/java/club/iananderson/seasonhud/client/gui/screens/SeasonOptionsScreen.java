package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.ShowDay;
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
  private ShowDay showDay;
  private boolean seasonColor;
  private boolean showSubSeason;
  private boolean showTropicalSeason;
  private boolean needCalendar;

  public SeasonOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
  }

  public static SeasonOptionsScreen getInstance(Screen parentScreen) {
    return new SeasonOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    showDay = Config.getShowDay();
    seasonColor = Config.getEnableSeasonNameColor();
    showSubSeason = Config.getShowSubSeason();
    showTropicalSeason = Config.getShowTropicalSeason();
    needCalendar = Config.getNeedCalendar();
  }

  public void saveConfig() {
    Config.setNeedCalendar(needCalendar);
  }

  @Override
  public void onDone() {
    saveConfig();
    super.onDone();
  }

  @Override
  public void onClose() {
    Config.setShowDay(showDay);
    Config.setEnableSeasonNameColor(seasonColor);
    Config.setShowSubSeason(showSubSeason);
    Config.setShowTropicalSeason(showTropicalSeason);
    super.onClose();
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);
    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();

    graphics.drawString(font, seasonCombined, 5, 5, 0xffffff);
  }

  @Override
  public void init() {
    super.init();

    row = 0;
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

    row = 1;
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

    row = 2;

    widgets.addAll(Arrays.asList(showDayButton, seasonColorButton, showSubSeasonButton, showTropicalSeasonButton));

    widgets.forEach(this::addRenderableWidget);
  }
}
