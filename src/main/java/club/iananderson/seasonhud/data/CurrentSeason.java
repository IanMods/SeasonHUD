package club.iananderson.seasonhud.data;

import club.iananderson.seasonhud.config.SeasonHUDClientConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import sereneseasons.api.season.SeasonHelper;

import java.util.ArrayList;
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
        } else if (SeasonHUDClientConfigs.showSubSeason.get()) {
            return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSubSeason().name();
        }
        else return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason().name();
    }



    //Convert Season to lower case (for file names)
    public static String getSeasonFileName(){
        Minecraft mc = Minecraft.getInstance();
        if (isTropicalSeason() || !SeasonHUDClientConfigs.showSubSeason.get()) {
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
    public static int getDate(){
        Minecraft mc = Minecraft.getInstance();
        return (SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getDay() % 8) + 1;
    }

   //Localized name for the hud
    public static ArrayList<Component> getSeasonName() {
        //System.out.println(getCurrentLocale());
        ArrayList<Component> text = new ArrayList<>();
        if (supportedLanguages().contains(getCurrentLocale())) {
            if (SeasonHUDClientConfigs.showDay.get()) {
                  text.add(Component.translatable("desc.seasonhud.detailed", Component.translatable("desc.seasonhud." + getSeasonStateLower()), getDate()));
                }
            else {
                text.add(Component.translatable("desc.seasonhud.summary." + getSeasonStateLower()));
            }
        }
        else {
            text.add(Component.translatable("desc.sereneseasons."+ getCurrentSeasonNameLower()));
        }
        return text;
    }
}

