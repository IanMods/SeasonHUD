package club.iananderson.seasonhud.impl.curios;

import club.iananderson.seasonhud.impl.curios.item.CuriosCalendar;
import club.iananderson.seasonhud.platform.Services;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {
  public CuriosCompat() {
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(Services.SEASON.calendar(), new CuriosCalendar());
  }

}
