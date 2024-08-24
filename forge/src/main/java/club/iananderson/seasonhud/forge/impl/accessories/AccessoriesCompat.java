package club.iananderson.seasonhud.forge.impl.accessories;

import club.iananderson.seasonhud.forge.impl.accessories.item.AccessoriesCalendar;
import club.iananderson.seasonhud.platform.Services;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    AccessoriesAPI.registerAccessory(Services.SEASON.calendar(), new AccessoriesCalendar());
  }

}