package club.iananderson.seasonhud.impl.accessories;

import club.iananderson.seasonhud.impl.accessories.item.AccessoriesCalendar;
import club.iananderson.seasonhud.platform.Services;
import io.wispforest.accessories.api.AccessoriesAPI;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public void setup() {
    AccessoriesAPI.registerAccessory(Services.SEASON.calendar(), new AccessoriesCalendar());
  }

}