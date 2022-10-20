package club.iananderson.seasoninfo.event;

import club.iananderson.seasoninfo.Seasoninfo;
import club.iananderson.seasoninfo.client.SeasonHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents{
    @Mod.EventBusSubscriber(modid = Seasoninfo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("season", SeasonHudOverlay.HUD_SEASON);
        }


    }

}
