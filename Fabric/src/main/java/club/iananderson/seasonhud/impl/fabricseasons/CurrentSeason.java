package club.iananderson.seasonhud.impl.fabricseasons;

import club.iananderson.seasonhud.SeasonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import io.github.lucaargolo.seasons.FabricSeasons;

import java.util.ArrayList;
import java.util.Objects;

import static club.iananderson.seasonhud.data.CurrentLocale.getCurrentLocale;
import static club.iananderson.seasonhud.data.CurrentLocale.supportedLanguages;
import static club.iananderson.seasonhud.config.ModConfig.*;
import static io.github.lucaargolo.seasons.FabricSeasons.CONFIG;


public class CurrentSeason {

    //Get the current season in Season type

    public static String getCurrentSeasonState(){
        Minecraft mc = Minecraft.getInstance();
        return FabricSeasons.getCurrentSeason(Objects.requireNonNull(mc.level)).name();
    }

    //Convert Season to lower case (for file names)
    public static String getSeasonFileName(){
        Minecraft mc = Minecraft.getInstance();
        return FabricSeasons.getCurrentSeason(Objects.requireNonNull(mc.level)).name().toLowerCase();
    }

    //Convert Season to lower case (for localized names)
    public static String getSeasonStateLower(){
        return getCurrentSeasonState().toLowerCase();
    }

    public static String getCurrentSeasonNameLower(){
        Minecraft mc = Minecraft.getInstance();
        return FabricSeasons.getCurrentSeason(Objects.requireNonNull(mc.level)).name().toLowerCase();
    }

    //Get the current date of the season
    public static int getDate() {
        Minecraft mc = Minecraft.getInstance();
        int worldTime = Math.toIntExact(Objects.requireNonNull(mc.level).getDayTime());

        int seasonDate = ((int)(worldTime - (worldTime / (long)CONFIG.getSeasonLength() * (long)CONFIG.getSeasonLength())) % CONFIG.getSeasonLength() / 24000)+1;

        return seasonDate;
    }

   //Localized name for the hud
    public static ArrayList<Component> getSeasonName() {
        ArrayList<Component> text = new ArrayList<>();
        if (supportedLanguages().contains(getCurrentLocale())) {
            if (INSTANCE.showDay) {
                text.add(Component.translatable("desc.seasonhud.detailed", Component.translatable("desc.seasonhud." + getSeasonStateLower()), getDate()));
            } else
                text.add(Component.translatable("desc.seasonhud.summary", Component.translatable("desc.seasonhud." + getSeasonStateLower())));
        }
       else if(INSTANCE.showDay) {
            text.add(Component.translatable("desc.seasonhud.detailed", Component.translatable("tooltip.seasons." + getCurrentSeasonNameLower()), getDate()));
        }
        else text.add(Component.translatable("desc.seasonhud.summary",Component.translatable("tooltip.seasons."+ getCurrentSeasonNameLower())));

        return text;
    }

    public static ResourceLocation getSeasonResource() {
        return new ResourceLocation(SeasonHUD.MODID, "textures/season/" + getSeasonFileName() + ".png");
        }
}



