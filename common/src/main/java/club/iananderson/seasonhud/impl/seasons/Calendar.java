package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class Calendar {
  private Calendar() {
  }

  public static boolean calendarFound() {
    Minecraft mc = Minecraft.getInstance();
    Item calendar = Services.SEASON.calendar();

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    return findCalendar(mc.player.getInventory(), calendar) || !Config.getNeedCalendar();
  }

  private static boolean findCalendar(Inventory inv, Item item) {
    return inv.contains(item.getDefaultInstance());
  }
}