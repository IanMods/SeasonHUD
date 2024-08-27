package club.iananderson.seasonhud.forge.impl.curios;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.forge.impl.curios.item.CuriosCalendar;

public class CuriosCompat {
  public CuriosCompat() {
  }

  public static void init() {
    if (Common.curiosLoaded()) {
      CuriosCalendar.init();
    }
  }
}
