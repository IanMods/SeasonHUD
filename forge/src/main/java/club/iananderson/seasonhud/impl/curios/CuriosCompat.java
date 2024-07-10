package club.iananderson.seasonhud.impl.curios;

import club.iananderson.seasonhud.impl.curios.item.CuriosCalendar;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosCompat {
  public CuriosCompat() {
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
  }

  public static void registerSlots() {
    InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder()
        .build());
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosCalendar.initCapabilities();
    CuriosCompat.registerSlots();
  }

}
