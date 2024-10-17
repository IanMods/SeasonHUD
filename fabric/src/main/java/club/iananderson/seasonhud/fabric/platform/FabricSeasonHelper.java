package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import java.time.LocalDateTime;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import sereneseasons.api.SSItems;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;

public class FabricSeasonHelper implements ISeasonHelper {

  @Override
  public boolean isTropicalSeason(Player player) {
    boolean showTropicalSeasons = Config.getShowTropicalSeason();
    boolean isInTropicalSeason = false;

    if (Common.sereneSeasonsLoaded()) {
      isInTropicalSeason = SeasonHelper.usesTropicalSeasons(player.level.getBiome(player.getOnPos()));
    }

    return showTropicalSeasons && isInTropicalSeason;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    if (Common.fabricSeasonsLoaded()) {
      return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
    } else {
      return false;
    }
  }

  @Override
  public String getCurrentSubSeason(Player player) {
    String subSeason = "";

    if (Common.fabricSeasonsLoaded()) {
      Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level);

      if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
        subSeason = "Autumn";
      } else {
        subSeason = currentSeasonState.toString();
      }
    }

    if (Common.sereneSeasonsLoaded()) {
      ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level);

      if (isTropicalSeason(player)) {
        subSeason = currentSeasonState.getTropicalSeason().toString();
      } else {
        subSeason = currentSeasonState.getSubSeason().toString();
      }
    }
    return subSeason;
  }

  @Override
  public String getCurrentSeason(Player player) {
    String season = "";

    if (Common.fabricSeasonsLoaded()) {
      Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level);

      if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
        season = "Autumn";
      } else {
        season = currentSeasonState.toString();
      }
    }

    if (Common.sereneSeasonsLoaded()) {
      ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level);
      if (isTropicalSeason(player)) {
        // Removes the "Early", "Mid", "Late" from the tropical season.
        String currentSubSeason = getCurrentSubSeason(player);
        season = currentSubSeason.substring(currentSubSeason.length() - 3);
      } else {
        season = currentSeasonState.getSeason().toString();
      }
    }

    return season;
  }

  @Override
  public String getSeasonFileName(Player player) {
    return getCurrentSeason(player).toLowerCase();
  }

  @Override
  public int getDate(Player player) {
    int date = 0;
    int dayLength = Config.getDayLength();

    if (Common.fabricSeasonsLoaded()) {
      int seasonLength = FabricSeasons.CONFIG.getSpringLength();
      int worldTime = Math.toIntExact(player.level.getDayTime());

      date = ((worldTime - (worldTime / seasonLength * seasonLength)) % seasonLength / dayLength) + 1;

      // Get the current day of month from the system. Used with fabric seasons' system time tied with season option
      if (isSeasonTiedWithSystemTime()) {
        date = LocalDateTime.now().getDayOfMonth();
      }
    }

    if (Common.sereneSeasonsLoaded()) {
      ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level);
      int seasonDay = currentSeasonState.getDay(); //Current day out of the year (Default 24 days * 4 = 96 days)
      int subSeasonDuration = ModConfig.seasons.subSeasonDuration; //In case the default duration is changed
      int subSeasonDate = (seasonDay % subSeasonDuration) + 1; //Default 8 days in each sub-season (1 week)
      int seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; //Default 24 days in a season (8 days * 3)

      if (Config.getShowSubSeason()) {
        if (isTropicalSeason(player)) {
          // Default 16 days in each tropical "sub-season".
          // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
          subSeasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 2)) + 1;
        }
        date = subSeasonDate;
      } else {
        if (isTropicalSeason(player)) {
          // Default 48 days in each tropical season.
          // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
          seasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 6)) + 1;
        }
        date = seasonDate;
      }
    }
    return date;
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = 0;
    int dayLength = Config.getDayLength();

    if (Common.fabricSeasonsLoaded()) {
      duration = FabricSeasons.CONFIG.getSpringLength() / dayLength;
    }

    if (Common.sereneSeasonsLoaded()) {
      duration = ModConfig.seasons.subSeasonDuration * 3;

      if (isTropicalSeason(player)) {
        duration *= 2; //Tropical seasons are twice as long (Default 48 days)
      }
      if (Config.getShowSubSeason() && Calendar.validDetailedMode()) {
        duration /= 3; //3 sub-seasons per season
      }
    }

    return duration;
  }

  @Override
  public Item calendar() {
    Item calendar = null;

    if (Common.fabricSeasonsLoaded() && Common.extrasLoaded()) {
      calendar = FabricSeasonsExtras.SEASON_CALENDAR_ITEM;
    }

    if (Common.sereneSeasonsLoaded()) {
      calendar = SSItems.CALENDAR;
    }

    return calendar;
  }

  @Override
  public boolean findCuriosCalendar(Player player, Item item) {
    Minecraft mc = Minecraft.getInstance();
    boolean curioEquipped = false;

    if (mc.level == null || mc.player == null || item == null) {
      return false;
    }

    if (Common.curiosLoaded()) {
      Optional<TrinketComponent> findCalendar = TrinketsApi.getTrinketComponent(player);

      if (findCalendar.isPresent()) {
        curioEquipped = findCalendar.get().isEquipped(item);
      }
    }

    return curioEquipped;
  }
}