package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.SeasonHUDClientConfigs;
import net.minecraft.client.Minecraft;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Objects;

public class CurrentSeason {

    //Get the current season in Season type
    public static Season getCurrentSeason(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
    }

    //Get the current season in SubSeason type
    public static Season.SubSeason getCurrentSubSeason(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSubSeason();
    }

    public static Season.TropicalSeason getCurrentTropicalSeason(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getTropicalSeason();
    }

    //Get the current date of the season
    public static int getDate(){
        Minecraft mc = Minecraft.getInstance();
        return (SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getDay() % 8) + 1;
    }

    //Convert Season to lower case (for file names)
    public static String getSeasonLower(){
        return getCurrentSeason().name().toLowerCase();
    }

    //Convert to lower case (for file names)
    public static String getSubSeasonLower(){
        return getCurrentSubSeason().name().toLowerCase();
    }

    public static String getTropicalSeasonLowered(){
        return getCurrentTropicalSeason().name().toLowerCase();
    }

    public static boolean isTropicalSeason(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.usesTropicalSeasons(Objects.requireNonNull(mc.level).getBiome(Objects.requireNonNull(mc.player).getOnPos()));
    }

   //Localized name for the hud
    public static String getSeasonName(){
        if ((SeasonHUDClientConfigs.showSubSeason.get()) && (Minecraft.getInstance().getLanguageManager().getSelected().getName().equals("English"))){
            return "desc.sereneseasons." +getSubSeasonLower();
        }
        else return "desc.sereneseasons." +getSeasonLower();
    }

}
