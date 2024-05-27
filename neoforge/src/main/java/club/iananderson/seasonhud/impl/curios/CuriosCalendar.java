//package club.iananderson.seasonhud.impl.curios;
//
//import club.iananderson.seasonhud.platform.Services;
//import net.minecraft.core.Direction;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.fml.InterModComms;
//import net.neoforged.neoforge.capabilities.ICapabilityProvider;
//import net.neoforged.neoforge.capabilities.ItemCapability;
//import net.neoforged.neoforge.common.util.Lazy;
//import top.theillusivec4.curios.api.CuriosCapability;
//import top.theillusivec4.curios.api.SlotTypeMessage;
//import top.theillusivec4.curios.api.SlotTypePreset;
//import top.theillusivec4.curios.api.type.capability.ICurio;
//import top.theillusivec4.curios.api.type.capability.ICurioItem;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
//public class CuriosCalendar implements ICurioItem {
//
//
//	public static void registerSlots() {
//		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());  //new SlotTypeMessage.Builder("calendar").build());
//	}
//
//	public static ICapabilityProvider initCapabilities() {
//		ICurio curio = new ICurio() {
//			final ItemStack stack = new ItemStack(Services.SEASON.calendar().asItem());
//
//			@Override
//			public ItemStack getStack() {
//				return stack;
//			}
//
//		};
//		return new ICapabilityProvider() {
//			private final Lazy<ICurio> curioOpt = Lazy.of(() -> curio);
//
//			@Nonnull
//			@Override
//			public <T> Lazy<T> getCapability(@Nonnull ItemCapability<T> cap,
//			                                         @Nullable Direction side) {
//
//				return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
//			}
//		};
//	}
//}