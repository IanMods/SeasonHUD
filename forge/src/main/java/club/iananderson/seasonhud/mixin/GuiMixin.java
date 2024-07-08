package club.iananderson.seasonhud.mixin;

import club.iananderson.seasonhud.event.ClientEvents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
  @Inject(method = "render", at = @At("RETURN"))
  private void render(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
    ClientEvents.ClientModBusEvents.registerGuiOverlays(guiGraphics, partialTick);
    ClientEvents.ClientModBusEvents.registerJourneyMapOverlay(guiGraphics, partialTick);
    ClientEvents.ClientModBusEvents.registerMapAtlasesOverlay(guiGraphics, partialTick);
  }
}
