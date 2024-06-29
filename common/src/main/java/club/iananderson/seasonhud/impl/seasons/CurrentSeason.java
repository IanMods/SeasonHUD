package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.ShowDay;
import club.iananderson.seasonhud.platform.Services;
import java.time.LocalDateTime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CurrentSeason {
  private final String currentSeason;
  private final String currentSubSeason;
  private final String seasonFileName;
  private final int seasonDate;
  private final int seasonDuration;

  public CurrentSeason(Minecraft mc) {
    Level level = mc.level;
    Player player = mc.player;
    this.currentSeason = Services.SEASON.getCurrentSeason(level, player);
    this.currentSubSeason = Services.SEASON.getCurrentSubSeason(level, player);
    this.seasonFileName = Services.SEASON.getSeasonFileName(level, player);
    this.seasonDate = Services.SEASON.getDate(level, player);
    this.seasonDuration = Services.SEASON.seasonDuration(level, player);
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  //Convert Season to lower case (for localized names)
  public String getSeasonStateLower() {
    if (Config.showSubSeason.get()) {
      return currentSubSeason.toLowerCase();
    } else {
      return currentSeason.toLowerCase();
    }
  }

  //Get the current season and match it to the icon for the font
  public String getSeasonIcon() {
    for (SeasonList season : SeasonList.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getIconChar();
      }
    }
    return "Icon Error";
  }

  //Localized name for the hud with icon
  public Component getText() {
    Component season = Component.translatable("desc.seasonhud." + getSeasonStateLower());

    return switch (Config.showDay.get()) {
      case NONE -> Component.translatable(ShowDay.NONE.getKey(), season);
      case SHOW_DAY -> Component.translatable(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
      case SHOW_WITH_TOTAL_DAYS ->
          Component.translatable(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), season, seasonDate, seasonDuration);
      case SHOW_WITH_MONTH -> {
        if (Services.SEASON.isSeasonTiedWithSystemTime()) {
          String currentMonth = LocalDateTime.now().getMonth().name().toLowerCase();
          yield Component.translatable(ShowDay.SHOW_WITH_MONTH.getKey(), season,
                                       Component.translatable("desc.seasonhud." + currentMonth), seasonDate);
        } else {
          yield Component.translatable(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
        }
      }
    };
  }

  //Get the current season and match it to the icon for the font
  public int getTextColor() {
    for (SeasonList season : SeasonList.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getSeasonColor();
      }
    }
    return 16777215;
  }

  public MutableComponent getSeasonHudText() {
    Component seasonIcon = Component.translatable("desc.seasonhud.icon", getSeasonIcon())
                                    .withStyle(Common.SEASON_STYLE);

    MutableComponent seasonText = getText().copy();

    Style SEASON_FORMAT = Style.EMPTY;

    if (Config.enableSeasonNameColor.get()) {
      SEASON_FORMAT = Style.EMPTY.withColor(getTextColor());
    }

    return Component.translatable("desc.seasonhud.combined", seasonIcon, seasonText.withStyle(SEASON_FORMAT));
  }

  public MutableComponent getSeasonHudTextNoFormat() {
    Component seasonIcon = Component.translatable("desc.seasonhud.icon", getSeasonIcon())
                                    .withStyle(Common.SEASON_STYLE);

    MutableComponent seasonText = getText().copy();

    return Component.translatable("desc.seasonhud.combined", seasonIcon, seasonText);
  }
}