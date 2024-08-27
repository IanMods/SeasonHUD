package club.iananderson.seasonhud.forge.impl.curios.item;

import club.iananderson.seasonhud.platform.Services;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosCalendar implements ICurioItem {
  public CuriosCalendar() {
  }

  public static void init() {
    CuriosApi.registerCurio(Services.SEASON.calendar(), new CuriosCalendar());
  }
}