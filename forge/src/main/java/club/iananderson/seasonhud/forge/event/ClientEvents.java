package club.iananderson.seasonhud.forge.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.SeasonHUDScreen;
import club.iananderson.seasonhud.forge.client.overlays.JourneyMap;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHUDOverlay;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents {
    @SubscribeEvent
    public static void renderSeasonHUDOverlay(RenderGameOverlayEvent.Post event) {
      if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
        Minecraft mc = Minecraft.getInstance();
        PoseStack graphics = event.getMatrixStack();
        float partialTicks = event.getPartialTicks();

        if (mc.level == null || mc.player == null) {
          return;
        }

        if (CurrentMinimap.journeyMapLoaded()) {
          JourneyMap.init(mc);
          JourneyMap.HUD_INSTANCE.render(graphics, partialTicks);
        }

        SeasonHUDOverlay.init(mc);
        SeasonHUDOverlay.HUD_INSTANCE.render(graphics, partialTicks);
      }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
      if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
        SeasonHUDScreen.open();
      }
    }
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {
    //Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(FMLClientSetupEvent event) {
      ClientRegistry.registerKeyBinding(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}