package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.EnumSet;
import java.util.Map;

public enum Seasons {
    SPRING(0, new TranslatableComponent("desc.seasonhud.season.spring"), "spring", "\uEA00", Config.DEFAULT_SPRING_COLOR,
            Config.getSpringColor(), Rgb.seasonMap(Config.getSpringColor())),

    SUMMER(1, new TranslatableComponent("desc.seasonhud.season.summer"), "summer", "\uEA01", Config.DEFAULT_SUMMER_COLOR,
            Config.getSummerColor(), Rgb.seasonMap(Config.getSummerColor())),

    AUTUMN(2, new TranslatableComponent("desc.seasonhud.season.autumn"), "autumn", "\uEA02", Config.DEFAULT_AUTUMN_COLOR,
            Config.getAutumnColor(), Rgb.seasonMap(Config.getAutumnColor())),

    WINTER(3, new TranslatableComponent("desc.seasonhud.season.winter"), "winter", "\uEA03", Config.DEFAULT_WINTER_COLOR,
            Config.getWinterColor(), Rgb.seasonMap(Config.getWinterColor())),

    DRY(4, new TranslatableComponent("desc.seasonhud.season.dry"), "dry", "\uEA04", Config.DEFAULT_DRY_COLOR,
            Config.getDryColor(), Rgb.seasonMap(Config.getDryColor())),

    WET(5, new TranslatableComponent("desc.seasonhud.season.wet"), "wet", "\uEA05", Config.DEFAULT_WET_COLOR,
            Config.getWetColor(), Rgb.seasonMap(Config.getWetColor()));

    public static final EnumSet<Seasons> SEASONS_ENUM_LIST = EnumSet.allOf(Seasons.class);
    private final int id;
    private final Component seasonName;
    private final String seasonFileName;
    private final String seasonIconChar;
    private final int defaultColor;
    private final Map<String, Integer> rgbMap;
    private int seasonColor;

    Seasons(int id, Component seasonName, String fileName, String iconChar, int defaultColor, int seasonColor,
            Map<String, Integer> rgbMap) {
        this.id = id;
        this.seasonName = seasonName;
        this.seasonFileName = fileName;
        this.seasonIconChar = iconChar;
        this.defaultColor = defaultColor;
        this.seasonColor = seasonColor;
        this.rgbMap = rgbMap;
    }

    public int getId() {
        return this.id;
    }

    public Component getSeasonName() {
        return this.seasonName;
    }

    public String getFileName() {
        return this.seasonFileName;
    }

    public String getIconChar() {
        return this.seasonIconChar;
    }

    public int getSeasonColor() {
        return this.seasonColor;
    }

    public void setSeasonColor(int rgbColor) {
        Seasons season = this;
        this.seasonColor = rgbColor;

        switch (season) {
            case SPRING:
                Config.setSpringColor(rgbColor);
                break;
            case SUMMER:
                Config.setSummerColor(rgbColor);
                break;
            case AUTUMN:
                Config.setAutumnColor(rgbColor);
                break;
            case WINTER:
                Config.setWinterColor(rgbColor);
                break;
            case DRY:
                Config.setDryColor(rgbColor);
                break;
            case WET:
                Config.setWetColor(rgbColor);
                break;
        }
    }

    public int getDefaultColor() {
        return this.defaultColor;
    }

    public Map<String, Integer> getRgbMap() {
        return this.rgbMap;
    }
}