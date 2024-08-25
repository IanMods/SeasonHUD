package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import java.time.LocalDateTime;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class FabricSeasonHelper implements ISeasonHelper {

  @Override
  public boolean isTropicalSeason(Level level, Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
  }

  @Override
  public String getCurrentSubSeason(Level level, Player player) {
    if (currentSeasonState(level).toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    } else {
      return currentSeasonState(level).toString();
    }
  }

  @Override
  public String getCurrentSeason(Level level, Player player) {
    if (currentSeasonState(level).toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    } else {
      return currentSeasonState(level).toString();
    }
  }

  @Override
  public String getSeasonFileName(Level level, Player player) {
    return getCurrentSeason(level, player).toLowerCase();
  }

  @Override
  public int getDate(Level level, Player player) {
    // Get the current day of month from the system. Used with fabric seasons' system time tied with season option
    if (isSeasonTiedWithSystemTime()) {
      return LocalDateTime.now().getDayOfMonth();
    } else {
      int seasonLength = FabricSeasons.CONFIG.getSeasonLength();
      int worldTime = Math.toIntExact(level.getDayTime());

      return ((worldTime - (worldTime / seasonLength * seasonLength)) % seasonLength / 24000) + 1;
    }
  }

  @Override
  public int seasonDuration(Level level, Player player) {
    return FabricSeasons.CONFIG.getSeasonLength() / 24000;
  }

  @Override
  public Item calendar() {
    return Registry.ITEM.get(new ResourceLocation("seasons", "season_calendar"));
  }

  @Override
  public int findCuriosCalendar(Player player, Item item) {
    if (Common.curiosLoaded()) {
      Optional<TrinketComponent> findCalendar = TrinketsApi.getTrinketComponent(player);
      if (findCalendar.isPresent()) {
        if (findCalendar.get().isEquipped(item)) {
          return 1;
        } else {
          return 0;
        }
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  private Season currentSeasonState(Level level) {
    return FabricSeasons.getCurrentSeason(level);
  }
}
