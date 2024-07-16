package club.iananderson.seasonhud.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.api.SSItems;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class NeoForgeSeasonHelper implements ISeasonHelper {
  @Override
  public boolean isTropicalSeason(Level level, Player player) {
    boolean showTropicalSeasons = Config.getShowTropicalSeason();
    boolean isInTropicalSeason = SeasonHelper.usesTropicalSeasons(level.getBiome(player.getOnPos()));

    return showTropicalSeasons && isInTropicalSeason;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public String getCurrentSubSeason(Level level, Player player) {
    if (isTropicalSeason(level, player)) {
      return currentSeasonState(level).getTropicalSeason().toString();
    } else {
      return currentSeasonState(level).getSubSeason().toString();
    }
  }

  @Override
  public String getCurrentSeason(Level level, Player player) {
    if (isTropicalSeason(level, player)) {
      // Removes the "Early", "Mid", "Late" from the tropical season.
      String currentSubSeason = getCurrentSubSeason(level, player);
      return currentSubSeason.substring(currentSubSeason.length() - 3);
    } else {
      return currentSeasonState(level).getSeason().toString();
    }
  }

  @Override
  public String getSeasonFileName(Level level, Player player) {
    return getCurrentSeason(level, player).toLowerCase();
  }

  @Override
  public int getDate(Level level, Player player) {
    int seasonDay = currentSeasonState(level).getDay(); //Current day out of the year (Default 24 days * 4 = 96 days)
    int subSeasonDuration = ModConfig.seasons.subSeasonDuration; //In case the default duration is changed
    int subSeasonDate = (seasonDay % subSeasonDuration) + 1; //Default 8 days in each sub-season (1 week)
    int seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; //Default 24 days in a season (8 days * 3)

    if (Config.getShowSubSeason()) {
      if (isTropicalSeason(level, player)) {
        // Default 16 days in each tropical "sub-season".
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        subSeasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 2)) + 1;
      }
      return subSeasonDate;
    } else {
      if (isTropicalSeason(level, player)) {
        // Default 48 days in each tropical season.
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        seasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 6)) + 1;
      }
      return seasonDate;
    }
  }

  @Override
  public int seasonDuration(Level level, Player player) {
    int seasonDuration = ModConfig.seasons.subSeasonDuration * 3;

    if (isTropicalSeason(level, player)) {
      seasonDuration *= 2; //Tropical seasons are twice as long (Default 48 days)
    }

    if (Config.getShowSubSeason()) {
      seasonDuration /= 3; //3 sub-seasons per season
    }

    return seasonDuration;
  }

  @Override
  public Item calendar() {
    return SSItems.CALENDAR;
  }

  @Override
  public int findCuriosCalendar(Player player, Item item) {
    int slot = 0;

    if (Common.curiosLoaded()) {
      ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
      if (curiosInventory.findFirstCurio(item).isPresent()) {
        slot += 1;
      }
    }
    if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
      AccessoriesContainer accessoriesContainer = AccessoriesCapability.get(player).getContainers().get("charm");
      slot += accessoriesContainer.getAccessories().countItem(item);
    }
    return slot;
  }

  private ISeasonState currentSeasonState(Level level) {
    return SeasonHelper.getSeasonState(level);
  }
}