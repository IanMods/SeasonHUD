package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sereneseasons.api.SSItems;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.SeasonsConfig;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class ForgeSeasonHelper implements ISeasonHelper {
  @Override
  public boolean isTropicalSeason(Level level, Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public String getCurrentSubSeason(Level level, Player player) {
//    if (isTropicalSeason(level, player)) {
//      return currentSeasonState(level).getTropicalSeason().toString();
//    } else {
    return currentSeasonState(level).getSubSeason().toString();
//    }
  }

  @Override
  public String getCurrentSeason(Level level, Player player) {
//    if (isTropicalSeason(level, player)) {
//      // Removes the "Early", "Mid", "Late" from the tropical season.
//      String currentSubSeason = getCurrentSubSeason(level, player);
//      return currentSubSeason.substring(currentSubSeason.length() - 3);
//    } else {
    return currentSeasonState(level).getSeason().toString();
//    }
  }

  @Override
  public String getSeasonFileName(Level level, Player player) {
    return getCurrentSeason(level, player).toLowerCase();
  }

  @Override
  public int getDate(Level level, Player player) {
    int seasonDay = currentSeasonState(level).getDay(); //Current day out of the year (Default 24 days * 4 = 96 days)
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
  public int seasonDuration(Level level, Player player) {
    int seasonDuration = SeasonsConfig.subSeasonDuration.get() * 3;

//    if (isTropicalSeason(level, player)) {
//      seasonDuration *= 2; //Tropical seasons are twice as long (Default 48 days)
//    }

    if (Config.getShowSubSeason()) {
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

  private ISeasonState currentSeasonState(Level level) {
    return SeasonHelper.getSeasonState(level);
  }
}
