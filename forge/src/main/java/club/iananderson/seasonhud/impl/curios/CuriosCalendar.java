package club.iananderson.seasonhud.impl.curios;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.Services;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosCalendar implements ICurioItem {
  public static void registerSlots() {
    InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
        () -> SlotTypePreset.CHARM.getMessageBuilder().build());
  }

  public static ICapabilityProvider initCapabilities() {
    ICurio curio = new ICurio() {
      final ItemStack stack = new ItemStack(Services.SEASON.calendar().asItem());

      @Override
      public ItemStack getStack() {
        return stack;
      }

    };
    return new ICapabilityProvider() {
      private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

      @Nonnull
      @Override
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
      }
    };
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