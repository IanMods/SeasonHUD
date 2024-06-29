package club.iananderson.seasonhud.impl.curios.item;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.curios.CuriosCalendar;
import club.iananderson.seasonhud.platform.Services;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import sereneseasons.item.CalendarItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CalendarSlot extends CalendarItem implements ICurioItem {

  public CalendarSlot(Properties p_41383_) {
    super(p_41383_);
  }

  @SubscribeEvent
  public static void sendImc(InterModEnqueueEvent event) {
    CuriosCalendar.registerSlots();
  }

  public static void setup(final FMLCommonSetupEvent evt) {
    if (Common.curiosLoaded()) {
      CuriosApi.registerCurio(Services.SEASON.calendar(), new CuriosCalendar());
    }
  }

  @Override
  public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
    if (Common.curiosLoaded()) {
      return CuriosCalendar.initCapabilities();
    }
    return super.initCapabilities(stack, unused);
  }

  @Nonnull
  @Override
  public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.BOOK_PUT, 1.0f, 1.0f);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }
}

