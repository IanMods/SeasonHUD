package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.util.Rgb;
import java.util.EnumSet;
import java.util.HashMap;
import net.minecraft.network.chat.Component;

public enum SeasonList {
  SPRING(0, Component.translatable("desc.seasonhud.spring"), "spring", "\uEA00", Config.defaultSpringColor,
         Config.getSpringColor(), Rgb.seasonMap(Config.getSpringColor())),

  SUMMER(1, Component.translatable("desc.seasonhud.summer"), "summer", "\uEA01", Config.defaultSummerColor,
         Config.getSummerColor(), Rgb.seasonMap(Config.getSummerColor())),

  AUTUMN(2, Component.translatable("desc.seasonhud.autumn"), "autumn", "\uEA02", Config.defaultAutumnColor,
         Config.getAutumnColor(), Rgb.seasonMap(Config.getAutumnColor())),

  WINTER(3, Component.translatable("desc.seasonhud.winter"), "winter", "\uEA03", Config.defaultWinterColor,
         Config.getWinterColor(), Rgb.seasonMap(Config.getWinterColor())),

  DRY(4, Component.translatable("desc.seasonhud.dry"), "dry", "\uEA04", Config.defaultDryColor, Config.getDryColor(),
      Rgb.seasonMap(Config.getDryColor())),

  WET(5, Component.translatable("desc.seasonhud.wet"), "wet", "\uEA05", Config.defaultWetColor, Config.getWetColor(),
      Rgb.seasonMap(Config.getWetColor()));

  public static final EnumSet<SeasonList> seasons = EnumSet.allOf(SeasonList.class);
  private final int id;
  private final Component seasonName;
  private final String seasonFileName;
  private final String seasonIconChar;
  private final int defaultColor;
  private final HashMap<String, Integer> rgbMap;
  private int seasonColor;

  SeasonList(int id, Component seasonName, String fileName, String iconChar, int defaultColor, int seasonColor,
      HashMap<String, Integer> rgbMap) {
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
    SeasonList season = this;
    this.seasonColor = rgbColor;

    switch (season) {
      case SPRING -> Config.setSpringColor(rgbColor);
      case SUMMER -> Config.setSummerColor(rgbColor);
      case AUTUMN -> Config.setAutumnColor(rgbColor);
      case WINTER -> Config.setWinterColor(rgbColor);
      case DRY -> Config.setWetColor(rgbColor);
      case WET -> Config.setDryColor(rgbColor);
    }
  }

  public int getDefaultColor() {
    return this.defaultColor;
  }

  public HashMap<String, Integer> getRgbMap() {
    return this.rgbMap;
  }
}