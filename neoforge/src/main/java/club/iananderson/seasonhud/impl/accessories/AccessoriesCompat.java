package club.iananderson.seasonhud.impl.accessories;

import club.iananderson.seasonhud.impl.accessories.item.AccessoriesCalendar;
import club.iananderson.seasonhud.platform.Services;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    AccessoriesAPI.registerAccessory(Services.SEASON.calendar(), new AccessoriesCalendar());
  }

}