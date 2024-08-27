package club.iananderson.seasonhud.impl.accessories;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.accessories.item.AccessoriesCalendar;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public static void clientInit() {
    if (Common.accessoriesLoaded()) {
      AccessoriesCalendar.clientInit();
    }
  }

  public static void init() {
    if (Common.accessoriesLoaded()) {
      AccessoriesCalendar.init();
    }
  }
}