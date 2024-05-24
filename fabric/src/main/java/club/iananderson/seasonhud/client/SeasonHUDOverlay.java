package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.config.Location;
import club.iananderson.seasonhud.platform.Services;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.config.Config.*;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.noMinimapLoaded;
import static club.iananderson.seasonhud.impl.seasons.Calendar.calendarFound;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;
import static club.iananderson.seasonhud.platform.Services.MINIMAP;

public class SeasonHUDOverlay implements HudRenderCallback{

    public static SeasonHUDOverlay HUD_INSTANCE;

    public SeasonHUDOverlay(){
    }

    public static void init() {
        HUD_INSTANCE = new SeasonHUDOverlay();
        HudRenderCallback.EVENT.register(HUD_INSTANCE);
    }

    @Override
    public void onHudRender(GuiGraphics seasonStack, float alpha) {
        SeasonHUDOverlayCommon.render(seasonStack);
    }
}