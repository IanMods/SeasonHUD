package club.iananderson.seasonhud.event;

import club.iananderson.seasonhud.SeasonHUD;
import club.iananderson.seasonhud.client.DebugHUD;
import club.iananderson.seasonhud.client.SeasonHUDOverlay;
import club.iananderson.seasonhud.client.SeasonMinimap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ClientEvents{
    @Mod.EventBusSubscriber(modid = SeasonHUD.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(),"season", SeasonHUDOverlay.HUD_SEASON);
        }

        @SubscribeEvent
        public static void registerDebugOverlay(RegisterGuiOverlaysEvent event) {
            event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(),"xaero", SeasonMinimap.XAERO_SEASON);
        }

        @SubscribeEvent
        public static void registerMinimapOverlay(RegisterGuiOverlaysEvent event) {
            event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(),"debug", DebugHUD.DEBUG_HUD);
        }

    }
}
