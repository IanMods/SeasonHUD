package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import java.time.LocalDateTime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class CurrentSeason {
  private final String currentSeason;
  private final String currentSubSeason;
  private final String seasonFileName;
  private final int seasonDate;
  private final int seasonDuration;
  private Style seasonFormat;

  public CurrentSeason(Minecraft mc) {
    Player player = mc.player;
    this.seasonFormat = Style.EMPTY;
    this.currentSeason = Services.SEASON.getCurrentSeason(player);
    this.currentSubSeason = Services.SEASON.getCurrentSubSeason(player);
    this.seasonFileName = Services.SEASON.getSeasonFileName(player);
    this.seasonDate = Services.SEASON.getDate(player);
    this.seasonDuration = Services.SEASON.seasonDuration(player);
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  //Convert Season to lower case (for localized names)
  public String getSeasonStateLower() {
    if (Config.getShowSubSeason() && currentSubSeason.contains("_")) {
      String lowerSubSeason = currentSubSeason.toLowerCase();
      return currentSeason.toLowerCase() + "." + lowerSubSeason.substring(0, lowerSubSeason.indexOf("_"));
    } else {
      return currentSeason.toLowerCase();
    }
  }

  //Get the current season and match it to the icon for the font
  public String getSeasonIcon() {
    for (Seasons season : Seasons.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getIconChar();
      }
    }
    return "Icon Error";
  }

  //Localized name for the hud with icon
  public Component getText() {
    Component season = Component.translatable("desc.seasonhud.season." + getSeasonStateLower());
    Component text = Component.literal("");

    switch (Config.getShowDay()) {
      case NONE:
        text = Component.translatable(ShowDay.NONE.getKey(), season);
        break;

      case SHOW_DAY:
        text = Component.translatable(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
        break;

      case SHOW_WITH_TOTAL_DAYS:
        text = Component.translatable(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), season, seasonDate, seasonDuration);
        break;

      case SHOW_WITH_MONTH:
        if (Services.SEASON.isSeasonTiedWithSystemTime()) {
          String systemMonth = LocalDateTime.now().getMonth().name().toLowerCase();
          Component currentMonth = Component.translatable("desc.seasonhud.month." + systemMonth);

          text = Component.translatable(ShowDay.SHOW_WITH_MONTH.getKey(), season, currentMonth, seasonDate);
        } else {
          text = Component.translatable(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
        }
        break;
    }
    return text;
  }

  //Get the current season and match it to the icon for the font
  public int getTextColor() {
    for (Seasons season : Seasons.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getSeasonColor();
      }
    }
    return 16777215;
  }

  public MutableComponent getSeasonHudTextNoFormat() {
    Component seasonIcon = Component.translatable("desc.seasonhud.hud.icon", getSeasonIcon())
                                    .withStyle(Common.SEASON_ICON_STYLE);
    MutableComponent seasonText = getText().copy();

    return Component.translatable("desc.seasonhud.hud.combined", seasonIcon, seasonText);
  }

  public MutableComponent getSeasonHudText() {
    MutableComponent seasonIcon = Component.translatable("desc.seasonhud.hud.icon", getSeasonIcon());
    MutableComponent seasonText = getText().copy();

    if (Config.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(getTextColor());
    }

    return Component.translatable("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                  seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getSeasonMenuText(Seasons season, int newRgb, boolean seasonShort) {
    MutableComponent seasonIcon = Component.translatable("desc.seasonhud.hud.icon", season.getIconChar());
    MutableComponent seasonText = Component.translatable(ShowDay.NONE.getKey(), season.getSeasonName());

    if (Config.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(newRgb);
    }

    if (season == Seasons.DRY && seasonShort) {
      seasonText = Component.translatable("menu.seasonhud.editbox.color.dry");
    }

    if (season == Seasons.WET && seasonShort) {
      seasonText = Component.translatable("menu.seasonhud.editbox.color.wet");
    }

    return Component.translatable("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                  seasonText.withStyle(seasonFormat));
  }
}