package club.iananderson.seasonhud.forge.impl.curios;

import club.iananderson.seasonhud.forge.impl.curios.item.CuriosCalendar;
import club.iananderson.seasonhud.platform.Services;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {
  public CuriosCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(Services.SEASON.calendar(), new CuriosCalendar());
  }
}