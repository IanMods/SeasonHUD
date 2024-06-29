package club.iananderson.seasonhud.impl.seasons;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class Calendar {

  public static boolean calendarLoaded = (Services.PLATFORM.getPlatformName().equals("Forge") || (
      Services.PLATFORM.getPlatformName().equals("Fabric") && Common.extrasLoaded()));

  public static boolean calendarFound() {
    LocalPlayer player = mc.player;

    if (Config.needCalendar.get() && calendarLoaded && player != null) {
      Inventory inv = player.getInventory();
      Item calendar = Services.SEASON.calendar();
      int slot = findCalendar(inv, calendar) + Services.SEASON.findCuriosCalendar(player, calendar);

      return (slot >= 0);
    } else {
      return true;
    }
  }

  private static int findCalendar(Inventory inv, Item item) {
    for (int i = 0; i < inv.items.size(); ++i) {
      if ((!inv.items.get(i).isEmpty() && inv.items.get(i).is(item)) || (!inv.offhand.get(0).isEmpty()
          && inv.offhand.get(0).is(item))) {
        return i;
      }
    }
    return -1;
  }
}