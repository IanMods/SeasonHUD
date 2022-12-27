package club.iananderson.seasonhud.data;

import club.iananderson.seasonhud.SeasonHUD;
import club.iananderson.seasonhud.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static club.iananderson.seasonhud.data.CurrentLocale.getCurrentLocale;
import static club.iananderson.seasonhud.data.CurrentLocale.supportedLanguages;


public class CurrentSeason {

    //Get the current season in Season type
    public static boolean isTropicalSeason(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.usesTropicalSeasons(Objects.requireNonNull(mc.level).getBiome(Objects.requireNonNull(mc.player).getOnPos()));
    }

    public static String getCurrentSeasonState(){
        Minecraft mc = Minecraft.getInstance();
        if (isTropicalSeason()) {
            return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getTropicalSeason().name();
        } else if (Config.showSubSeason.get()) {
            return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSubSeason().name();
        }
        else return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason().name();
    }

    public static String getSubSeasonPeriod(){
        Minecraft mc = Minecraft.getInstance();
        String subSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSubSeason().name();
        String subSeasonPeriod = subSeason.split("_")[0].toUpperCase();
        //System.out.println(subSeasonPeriod);
        return subSeasonPeriod;
    }


    //Convert Season to lower case (for file names)
    public static String getSeasonFileName(){
        Minecraft mc = Minecraft.getInstance();
        if (isTropicalSeason() || !Config.showSubSeason.get()) {
            return getCurrentSeasonState().toLowerCase();
        }
        else return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason().name().toLowerCase();
    }

    //Convert Season to lower case (for localized names)
    public static String getSeasonStateLower(){
        return getCurrentSeasonState().toLowerCase();
    }

    public static String getCurrentSeasonNameLower(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason().name().toLowerCase();
    }


    //Get the current date of the season
    public static int getDate() {
        Minecraft mc = Minecraft.getInstance();
        ISeasonState seasonState = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level));

        int seasonDay = seasonState.getDay(); //total day out of 24 * 4 = 96
        int seasonDayDur = seasonState.getDayDuration(); //24000 ticks
        int time = seasonDay * seasonDayDur; // current tick count

        int subSeasonDur = seasonDayDur * 8; //how long a subseason is in ticks
        int seasonDur = seasonDayDur * 24; // how long a season is in ticks
        int tropicalSeasonDur = seasonDayDur * 16; //how long a tropical season is in ticks



        int seasonDate = (seasonDay%24)+1; //correct
        int subDate = (seasonDay % 8) + 1;
        int subTropDate = ((time/subSeasonDur+ 11) / 2 + 5) % 6; //currently displaying the TropicalSeason enum index
                                                                // need to get the math for TropicalSeason day to work
        System.out.println(subTropDate);

        if(isTropicalSeason()){
            return subTropDate;
        }
        else if(Config.showSubSeason.get()){
            return subDate;
        }
        else return seasonDate;//subDate+(getSubSeasonPeriod().equals("EARLY") ? 0 : (getSubSeasonPeriod().equals("MID") ? 8:16));

    }

   //Localized name for the hud
    public static ArrayList<Component> getSeasonName() {
        //System.out.println(getCurrentLocale());
        ArrayList<Component> text = new ArrayList<>();
        if (supportedLanguages().contains(getCurrentLocale())) {
            if (Config.showDay.get()) {
                  text.add(Component.translatable("desc.seasonhud.detailed", Component.translatable("desc.seasonhud." + getSeasonStateLower()), getDate()));
                }
            else {
                text.add(Component.translatable("desc.seasonhud.summary", Component.translatable("desc.seasonhud." + getSeasonStateLower())));
            }
        } else if(Config.showDay.get()) {
            text.add(Component.translatable("desc.seasonhud.detailed", Component.translatable("desc.sereneseasons." + getCurrentSeasonNameLower()), getDate()));
        }
        else
            text.add(Component.translatable("desc.seasonhud.summary",Component.translatable("desc.sereneseasons."+ getCurrentSeasonNameLower())));

        return text;
    }
}


