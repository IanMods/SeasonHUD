package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class Calendar {
  private Calendar() {
  }

  private static boolean findCalendar(Player player, Item item) {
    return player.getInventory().contains(item.getDefaultInstance()) || Services.SEASON.findCuriosCalendar(player,
                                                                                                           item);
  }

  public static boolean calendarFound() {
    Minecraft mc = Minecraft.getInstance();
    Item calendar = Services.SEASON.calendar();

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    boolean curio = false;

    if (Common.accessoriesLoaded() && !Common.curiosLoaded()) {
      AccessoriesContainer accessoriesContainer = AccessoriesCapability.get(mc.player)
                                                                       .getContainers()
                                                                       .get("calendarslot");
      curio = accessoriesContainer.getAccessories().countItem(calendar) > 0;
    }

    return findCalendar(mc.player, calendar) || curio || !Config.getNeedCalendar();
  }

  public static boolean calendarFoundDetailed() {
    Minecraft mc = Minecraft.getInstance();
    Item calendar = Services.SEASON.calendar();

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    return findCalendar(mc.player, calendar) && Config.getCalanderDetailMode();
  }

}