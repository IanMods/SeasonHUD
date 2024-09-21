package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import sereneseasons.api.SSItems;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.SeasonsConfig;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class ForgeSeasonHelper implements ISeasonHelper {
  @Override
  public boolean isTropicalSeason(Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public String getCurrentSubSeason(Player player) {
//    if (isTropicalSeason(player)) {
//      return currentSeasonState(player).getTropicalSeason().toString();
//    } else {
    return currentSeasonState(player).getSubSeason().toString();
//    }
  }

  @Override
  public String getCurrentSeason(Player player) {
//    if (isTropicalSeason(player)) {
//      // Removes the "Early", "Mid", "Late" from the tropical season.
//      String currentSubSeason = getCurrentSubSeason(player);
//      return currentSubSeason.substring(currentSubSeason.length() - 3);
//    } else {
    return currentSeasonState(player).getSeason().toString();
//    }
  }

  @Override
  public String getSeasonFileName(Player player) {
    return getCurrentSeason(player).toLowerCase();
  }

  @Override
  public int getDate(Player player) {
    int seasonDay = currentSeasonState(player).getDay(); //Current day out of the year (Default 24 days * 4 = 96 days)
    int subSeasonDuration = SeasonsConfig.subSeasonDuration.get(); //In case the default duration is changed
    int subSeasonDate = (seasonDay % subSeasonDuration) + 1; //Default 8 days in each sub-season (1 week)
    int seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; //Default 24 days in a season (8 days * 3)

    if (Config.getShowSubSeason()) {
      return seasonDate;
    } else {
      return subSeasonDate;
    }
  }

  @Override
  public int seasonDuration(Player player) {
    int seasonDuration = SeasonsConfig.subSeasonDuration.get() * 3;

//    if (isTropicalSeason(player)) {
//      seasonDuration *= 2; //Tropical seasons are twice as long (Default 48 days)
//    }

    if (Config.getShowSubSeason() && Calendar.validDetailedMode()) {
      seasonDuration /= 3; //3 sub-seasons per season
    }

    return seasonDuration;
  }

  @Override
  public ItemStack calendar() {
    return SSItems.calendar.getDefaultInstance();
  }

  @Override
  public boolean findCuriosCalendar(Player player, ItemStack item) {
    Minecraft mc = Minecraft.getInstance();
    boolean curioEquipped = false;

    if (mc.level == null || mc.player == null || item == null) {
      return false;
    }

    if (Common.curiosLoaded()) {
      List<SlotResult> findCalendar = CuriosApi.getCuriosHelper().findCurios(player, item.getItem());
      curioEquipped = !findCalendar.isEmpty();
    }
    return curioEquipped;
  }

  private ISeasonState currentSeasonState(Player player) {
    return SeasonHelper.getSeasonState(player.level);
  }
}
