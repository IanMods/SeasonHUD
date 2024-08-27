package club.iananderson.seasonhud.forge.impl.curios;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.forge.impl.curios.item.CuriosCalendar;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;

public class CuriosCompat {
  public CuriosCompat() {
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
  }

  public static void registerSlots() {
    InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                         () -> new SlotTypeMessage.Builder("calendarslot").icon(Common.slotIcon).size(1)
                             .build());
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosCalendar.initCapabilities();
    CuriosCompat.registerSlots();
  }

}
