package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
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

  private static boolean calendarFound() {
    Minecraft mc = Minecraft.getInstance();
    Item calendar = Services.SEASON.calendar();

    if (!Common.extrasLoaded()) {
      return true;
    }

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    return findCalendar(mc.player, calendar);
  }

  public static boolean validNeedCalendar() {
    return (Config.getNeedCalendar() && Calendar.calendarFound()) || !Config.getNeedCalendar();
  }

  public static boolean validDetailedMode() {
    return (Config.getCalanderDetailMode() && Calendar.calendarFound()) || !Config.getCalanderDetailMode();
  }
}