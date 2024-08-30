package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import java.time.LocalDateTime;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricSeasonHelper implements ISeasonHelper {

  @Override
  public boolean isTropicalSeason(Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
  }

  @Override
  public String getCurrentSubSeason(Player player) {
    if (currentSeasonState(player).toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    } else {
      return currentSeasonState(player).toString();
    }
  }

  @Override
  public String getCurrentSeason(Player player) {
    if (currentSeasonState(player).toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    } else {
      return currentSeasonState(player).toString();
    }
  }

  @Override
  public String getSeasonFileName(Player player) {
    return getCurrentSeason(player).toLowerCase();
  }

  @Override
  public int getDate(Player player) {
    // Get the current day of month from the system. Used with fabric seasons' system time tied with season option
    if (isSeasonTiedWithSystemTime()) {
      return LocalDateTime.now().getDayOfMonth();
    } else {
      int seasonLength = FabricSeasons.CONFIG.getSeasonLength();
      int worldTime = Math.toIntExact(player.level.getDayTime());

      return ((worldTime - (worldTime / seasonLength * seasonLength)) % seasonLength / 24000) + 1;
    }
  }

  @Override
  public int seasonDuration(Player player) {
    return FabricSeasons.CONFIG.getSeasonLength() / 24000;
  }

  @Override
  public Item calendar() {
    return Registry.ITEM.get(new ResourceLocation("seasons", "season_calendar"));
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

  private Season currentSeasonState(Player player) {
    return FabricSeasons.getCurrentSeason(player.level);
  }
}
