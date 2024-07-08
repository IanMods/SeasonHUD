package club.iananderson.seasonhud.impl.curios;

import club.iananderson.seasonhud.impl.curios.item.CuriosCalendar;
import club.iananderson.seasonhud.platform.Services;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {
  public CuriosCompat() {
    final IEventBus eventBus = NeoForge.EVENT_BUS;
    eventBus.addListener(this::setup);
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(Services.SEASON.calendar(), new CuriosCalendar());
  }

}
