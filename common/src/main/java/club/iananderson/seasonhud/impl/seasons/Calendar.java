package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Calendar {
  private Calendar() {
  }

  private static boolean findCalendar(Player player, ItemStack item) {
    return player.inventory.contains(item) || Services.SEASON.findCuriosCalendar(player, item);
  }

  public static boolean calendarFound() {
    Minecraft mc = Minecraft.getInstance();
    ItemStack calendar = Services.SEASON.calendar();

    if (Services.PLATFORM.getPlatformName().equals("Fabric") && !Common.extrasLoaded()) {
      return true;
    }

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    boolean inventorySlot = findCalendar(mc.player, calendar);
    boolean curioSlot = Services.SEASON.findCuriosCalendar(mc.player, calendar);

    return inventorySlot || curioSlot || !Config.getNeedCalendar();
  }
}