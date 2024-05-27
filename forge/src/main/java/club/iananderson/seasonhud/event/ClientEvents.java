package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.minimaps.JourneyMap;
import club.iananderson.seasonhud.client.minimaps.MapAtlases;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents{
    @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key Event) {
            if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
                SeasonHUDScreen.open();
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        //Overlays
        public static void registerOverlays(GuiGraphics guiGraphics, float partialTick) {
            new SeasonHUDOverlay().render(guiGraphics, partialTick);
            new JourneyMap().render(guiGraphics, partialTick);
            new MapAtlases().render(guiGraphics,partialTick);
        }

        //Key Bindings
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBindings.seasonhudOptionsKeyMapping);
        }
    }
}
