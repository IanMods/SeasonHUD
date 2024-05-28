package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.minimaps.JourneyMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.ForgeIngameGui.FROSTBITE_ELEMENT;

public class ClientEvents{
    @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
                SeasonHUDScreen.open();
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        //Overlays
        @SubscribeEvent
        public static void init(FMLClientSetupEvent event) {
            OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "season", new SeasonHUDOverlay());
            OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "journeymap", new JourneyMap());
            MinecraftForge.EVENT_BUS.addListener(ClientForgeEvents::onKeyInput);
            KeyBindings.init();
        }
    }
}

