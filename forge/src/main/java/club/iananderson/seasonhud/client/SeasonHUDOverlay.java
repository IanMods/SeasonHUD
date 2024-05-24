package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.config.Location;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.config.Config.*;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;

public class SeasonHUDOverlay implements IGuiOverlay{
    public SeasonHUDOverlay(){
    }

    public void render(ForgeGui gui, GuiGraphics seasonStack, float partialTick, int screenWidth, int screenHeight) {
        SeasonHUDOverlayCommon.render(seasonStack);
    }
}
