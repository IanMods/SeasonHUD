package club.iananderson.seasonhud.platform;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.services.ISeasonHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.lucaargolo.seasons.FabricSeasons;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricSeasonHelper implements ISeasonHelper {

  @Override
  public boolean isTropicalSeason() {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
  }

  @Override
  public String getCurrentSeasonState() {
    String seasonState = FabricSeasons.getCurrentSeason(Objects.requireNonNull(mc.level)).toString();

    if (seasonState.equalsIgnoreCase("fall")) {
      return "Autumn";
    } else {
      return seasonState;
    }
  }

  @Override
  public String getSeasonFileName() {
    return getCurrentSeasonState().toLowerCase();
  }

  @Override
  public int getDate() {
    // Get the current day of month from the system; used with fabric seasons' system time tied with season option
    if (isSeasonTiedWithSystemTime()) {
      return LocalDateTime.now().getDayOfMonth();
    } else {
      int seasonLength = FabricSeasons.CONFIG.getSeasonLength();
      int worldTime = Math.toIntExact(Objects.requireNonNull(mc.level).getDayTime());

      return ((worldTime - (worldTime / seasonLength * seasonLength)) % seasonLength / 24000) + 1;
    }
  }

  @Override
  public int seasonDuration() {
    return FabricSeasons.CONFIG.getSeasonLength() / 24000;
  }

  @Override
  public Item calendar() {
    return Registry.ITEM.get(new ResourceLocation("seasons", "season_calendar"));
  }

  @Override
  public int findCuriosCalendar(Player player, Item item) {
    if (Common.curiosLoaded() && Common.extrasLoaded()) {
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
}
