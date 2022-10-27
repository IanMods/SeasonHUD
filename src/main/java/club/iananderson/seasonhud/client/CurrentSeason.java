package club.iananderson.seasonhud.client;

import net.minecraft.client.Minecraft;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import javax.annotation.Nullable;
import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public class CurrentSeason {
    Minecraft mc = Minecraft.getInstance();
    private static Season currentSeason;
    private static String seasonCap;
    private static String seasonLower;
    private static String seasonName;


    public static void setCurrentSeason(Season currentSeason) {
        CurrentSeason.currentSeason = currentSeason;
    }

    public static Season getCurrentSeason() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            currentSeason = SeasonHelper.getSeasonState(mc.level).getSeason();
            return currentSeason;
        }
        return currentSeason;
        }


    public static void setSeasonCap(String seasonCap) {
        CurrentSeason.seasonCap = seasonCap;
    }

    public static String getSeasonCap() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            seasonCap = currentSeason.name();
            return seasonCap;
        }
        return seasonCap;
    }


    public static void setSeasonLower(String seasonLower) {
        CurrentSeason.seasonLower = seasonLower;
    }

    public static String getSeasonLower() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            seasonLower = seasonCap.toLowerCase();
            return seasonLower;
        }
        return seasonLower;
    }


    public static void setSeasonFinalName(String seasonName) {
        CurrentSeason.seasonName = seasonName;
    }

    public static String getSeasonFinalName() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            seasonName = seasonLower.substring(0, 1).toUpperCase() + seasonLower.substring(1);
            return seasonName;
        }
        return seasonName;
    }

}
