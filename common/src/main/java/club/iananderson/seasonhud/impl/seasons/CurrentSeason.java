package club.iananderson.seasonhud.impl.seasons;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.platform.Services.SEASON;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.ShowDay;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class CurrentSeason {

  //Convert Season to lower case (for localized names)
  public static String getSeasonStateLower() {
    return SEASON.getCurrentSeasonState().toLowerCase();
  }

  //Get the current season and match it to the icon for the font
  public static String getSeasonIcon(String seasonFileName) {
    for (SeasonList season : SeasonList.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getIconChar();
      }
    }
    return null;
  }

  //Get the current season and match it to the icon for the font
  public static int getTextColor(String seasonFileName) {
    for (SeasonList season : SeasonList.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getSeasonColor();
      }
    }
    return 16777215;
  }

  //Localized name for the hud
  public static ArrayList<Component> getSeasonHudName() {
    ArrayList<Component> text = new ArrayList<>();
    ShowDay showDay = Config.showDay.get();
    String fileName = SEASON.getSeasonFileName();
    Style SEASON_FORMAT = Style.EMPTY;

    if (Config.enableSeasonNameColor.get()) {
      SEASON_FORMAT = Style.EMPTY.withColor(CurrentSeason.getTextColor(fileName));
    }

    switch (showDay) {
      case NONE -> {
        text.add(new TranslatableComponent("desc.seasonhud.icon", getSeasonIcon(fileName)).withStyle(SEASON_STYLE));
        text.add(new TranslatableComponent("desc.seasonhud.summary",
            new TranslatableComponent("desc.seasonhud." + getSeasonStateLower())).withStyle(SEASON_FORMAT));
      }

      case SHOW_DAY -> {
        text.add(new TranslatableComponent("desc.seasonhud.icon", getSeasonIcon(fileName)).withStyle(SEASON_STYLE));
        text.add(new TranslatableComponent("desc.seasonhud.detailed",
                              new TranslatableComponent("desc.seasonhud." + getSeasonStateLower()), SEASON.getDate())
                          .withStyle(SEASON_FORMAT));
      }

      case SHOW_WITH_TOTAL_DAYS -> {
        text.add(new TranslatableComponent("desc.seasonhud.icon", getSeasonIcon(fileName)).withStyle(SEASON_STYLE));
        text.add(new TranslatableComponent("desc.seasonhud.detailed.total",
            new TranslatableComponent("desc.seasonhud." + getSeasonStateLower()), SEASON.getDate(),
            SEASON.seasonDuration()).withStyle(SEASON_FORMAT));
      }

      case SHOW_WITH_MONTH -> {
        text.add(new TranslatableComponent("desc.seasonhud.icon", getSeasonIcon(fileName)).withStyle(SEASON_STYLE));

        if (SEASON.isSeasonTiedWithSystemTime()) {
          String currentMonth = LocalDateTime.now().getMonth().name().toLowerCase();
          text.add(new TranslatableComponent("desc.seasonhud.month",
              new TranslatableComponent("desc.seasonhud." + getSeasonStateLower()),
              new TranslatableComponent("desc.seasonhud." + currentMonth), SEASON.getDate()).withStyle(SEASON_FORMAT));
        } else {
          text.add(new TranslatableComponent("desc.seasonhud.detailed",
                                new TranslatableComponent("desc.seasonhud." + getSeasonStateLower()), SEASON.getDate())
                            .withStyle(SEASON_FORMAT));
        }
      }
    }

    return text;
  }
}