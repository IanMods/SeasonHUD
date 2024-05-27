package club.iananderson.seasonhud.mixin.xaero;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.minimap.info.BuiltInInfoDisplays;
import xaero.common.minimap.info.InfoDisplayManager;

import static club.iananderson.seasonhud.impl.minimaps.XaeroInfoDisplays.SEASON;

@Mixin(BuiltInInfoDisplays.class)
public class XaeroBuiltInInfoDisplays {
    
    @Inject(
            method = "addToManager",
            at = @At("TAIL"),
            remap = false)

    private static void addToManager(InfoDisplayManager manager, CallbackInfo ci) {
        manager.add(SEASON);
    }
}