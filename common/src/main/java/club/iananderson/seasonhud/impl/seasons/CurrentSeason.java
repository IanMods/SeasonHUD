package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.time.LocalDateTime;

public class CurrentSeason {
    private final String currentSeason;
    private final String currentSubSeason;
    private final String seasonFileName;
    private final int seasonDate;
    private final int seasonDuration;
    private Style seasonFormat;

    public CurrentSeason(Minecraft mc) {
        Level level = mc.level;
        Player player = mc.player;
        this.seasonFormat = Style.EMPTY;
        this.currentSeason = Services.SEASON.getCurrentSeason(level, player);
        this.currentSubSeason = Services.SEASON.getCurrentSubSeason(level, player);
        this.seasonFileName = Services.SEASON.getSeasonFileName(level, player);
        this.seasonDate = Services.SEASON.getDate(level, player);
        this.seasonDuration = Services.SEASON.seasonDuration(level, player);
    }

    public static CurrentSeason getInstance(Minecraft mc) {
        return new CurrentSeason(mc);
    }

    //Convert Season to lower case (for localized names)
    public String getSeasonStateLower() {
        if (Config.getShowSubSeason() && currentSubSeason.contains("_")) {
            String lowerSubSeason = currentSubSeason.toLowerCase();
            return currentSeason.toLowerCase() + "." + lowerSubSeason.substring(0, lowerSubSeason.indexOf("_"));
        } else {
            return currentSeason.toLowerCase();
        }
    }

    //Get the current season and match it to the icon for the font
    public String getSeasonIcon() {
        for (Seasons season : Seasons.values()) {
            if (season.getFileName().equals(seasonFileName)) {
                return season.getIconChar();
            }
        }
        return "Icon Error";
    }

    //Localized name for the hud with icon
    public Component getText() {
        Component season = new TranslatableComponent("desc.seasonhud.season." + getSeasonStateLower());
        Component text = new TextComponent("");

        switch (Config.getShowDay()) {
            case NONE:
                text = new TranslatableComponent(ShowDay.NONE.getKey(), season);
                break;

            case SHOW_DAY:
                text = new TranslatableComponent(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
                break;

            case SHOW_WITH_TOTAL_DAYS:
                text = new TranslatableComponent(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), season, seasonDate, seasonDuration);
                break;

            case SHOW_WITH_MONTH:
                if (Services.SEASON.isSeasonTiedWithSystemTime()) {
                    String systemMonth = LocalDateTime.now().getMonth().name().toLowerCase();
                    Component currentMonth = new TranslatableComponent("desc.seasonhud.month." + systemMonth);

                    text = new TranslatableComponent(ShowDay.SHOW_WITH_MONTH.getKey(), season, currentMonth, seasonDate);
                } else {
                    text = new TranslatableComponent(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
                }
                break;
        }

        return text;
    }

    //Get the current season and match it to the icon for the font
    public TextColor getTextColor() {
        for (Seasons season : Seasons.values()) {
            if (season.getFileName().equals(seasonFileName)) {
                return TextColor.fromRgb(season.getSeasonColor());
            }
        }
        return TextColor.fromRgb(16777215);
    }

    public MutableComponent getSeasonHudTextNoFormat() {
        Component seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", getSeasonIcon()).withStyle(
                Common.SEASON_ICON_STYLE);
        MutableComponent seasonText = getText().copy();

        return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon, seasonText);
    }

    public MutableComponent getSeasonHudText() {
        MutableComponent seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", getSeasonIcon());
        MutableComponent seasonText = getText().copy();

        if (Config.getEnableSeasonNameColor()) {
            seasonFormat = Style.EMPTY.withColor(getTextColor());
        }

        return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                seasonText.withStyle(seasonFormat));
    }

    public MutableComponent getSeasonMenuText(Seasons season, TextColor newRgb, boolean seasonShort) {
        MutableComponent seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", season.getIconChar());
        MutableComponent seasonText = new TranslatableComponent(ShowDay.NONE.getKey(), season.getSeasonName());

        if (Config.getEnableSeasonNameColor()) {
            seasonFormat = Style.EMPTY.withColor(newRgb);
        }

        if (season == Seasons.DRY && seasonShort) {
            seasonText = new TranslatableComponent("menu.seasonhud.editbox.color.dry");
        }

        if (season == Seasons.WET && seasonShort) {
            seasonText = new TranslatableComponent("menu.seasonhud.editbox.color.wet");
        }

        return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                seasonText.withStyle(seasonFormat));
    }
}