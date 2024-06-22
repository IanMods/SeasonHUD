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
  private final Level level;
  private final Player player;

  public CurrentSeason(Minecraft mc) {
    this.level = mc.level;
    this.player = mc.player;
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  //Convert Season to lower case (for localized names)
  public String getSeasonStateLower() {
    if (Config.showSubSeason.get()) {
      return Services.SEASON.getCurrentSubSeason(level, player).toLowerCase();
    } else {
      return Services.SEASON.getCurrentSeason(level, player).toLowerCase();
    }
  }

  //Get the current season and match it to the icon for the font
  public String getSeasonIcon(String seasonFileName) {
    for (SeasonList season : SeasonList.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getIconChar();
      }
    }
    return "";
  }

  //Localized name for the hud with icon
  public Component getText() {
    int seasonDate = Services.SEASON.getDate(level, player);
    Component season = Component.translatable("desc.seasonhud." + getSeasonStateLower());

    return switch (Config.showDay.get()) {
      case NONE -> Component.translatable(ShowDay.NONE.getKey(), season);
      case SHOW_DAY -> Component.translatable(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
      case SHOW_WITH_TOTAL_DAYS -> Component.translatable(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), season, seasonDate,
          Services.SEASON.seasonDuration(level, player));
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
  public int getTextColor(String seasonFileName) {
    for (SeasonList season : SeasonList.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getSeasonColor();
      }
    }
    return 16777215;
  }

  public MutableComponent getSeasonHudText() {
    Component seasonIcon = Component.translatable("desc.seasonhud.icon",
        getSeasonIcon(Services.SEASON.getSeasonFileName(level, player))).withStyle(Common.SEASON_STYLE);

    MutableComponent seasonText = getText().copy();

    Style SEASON_FORMAT = Style.EMPTY;

    if (Config.enableSeasonNameColor.get()) {
      SEASON_FORMAT = Style.EMPTY.withColor(getTextColor(Services.SEASON.getSeasonFileName(level, player)));
    }


    return Component.translatable("desc.seasonhud.combined", seasonIcon, seasonText.withStyle(SEASON_FORMAT));
  }

  public MutableComponent getSeasonHudTextNoFormat(){
    Component seasonIcon = Component.translatable("desc.seasonhud.icon",
        getSeasonIcon(Services.SEASON.getSeasonFileName(level, player))).withStyle(Common.SEASON_STYLE);

    MutableComponent seasonText = getText().copy();

    return Component.translatable("desc.seasonhud.combined", seasonIcon, seasonText);
  }
}