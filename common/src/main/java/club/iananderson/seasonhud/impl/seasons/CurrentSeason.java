package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.config.ShowDay;
import net.minecraft.network.chat.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.platform.Services.SEASON;

public class CurrentSeason {
    //Convert Season to lower case (for localized names)
    public static String getSeasonStateLower(){
        return SEASON.getCurrentSeasonState().toLowerCase();
    }

    //Get the current season and match it to the icon for the font
    public static String getSeasonIcon(String seasonFileName){
        for(SeasonList season : SeasonList.values()){
            if(season.getFileName().equals(seasonFileName)){
                return season.getIconChar();
            }
        }
        return null;
    }

   //Localized name for the hud
    public static ArrayList<Component> getSeasonHudName() {
        ArrayList<Component> text = new ArrayList<>();
        ShowDay showDay = Config.showDay.get();

        switch(showDay){
            case NONE ->{
                text.add(Component.translatable("desc.seasonhud.icon",getSeasonIcon(SEASON.getSeasonFileName())).withStyle(SEASON_STYLE));
                text.add(Component.translatable("desc.seasonhud.summary", Component.translatable("desc.seasonhud." + getSeasonStateLower())));
            }

            case SHOW_DAY ->{
                text.add(Component.translatable("desc.seasonhud.icon", getSeasonIcon(SEASON.getSeasonFileName())).withStyle(SEASON_STYLE));
                text.add(Component.translatable("desc.seasonhud.detailed", Component.translatable("desc.seasonhud." + getSeasonStateLower()), SEASON.getDate()));
            }

            case SHOW_WITH_TOTAL_DAYS ->{
                text.add(Component.translatable("desc.seasonhud.icon",getSeasonIcon(SEASON.getSeasonFileName())).withStyle(SEASON_STYLE));
                text.add(Component.translatable("desc.seasonhud.detailed.total",Component.translatable("desc.seasonhud." + getSeasonStateLower()), SEASON.getDate(), SEASON.seasonDuration()));
            }
        }

        return text;
    }
}