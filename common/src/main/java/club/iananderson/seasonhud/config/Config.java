package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
  //Config Builder
  public static final ForgeConfigSpec GENERAL_SPEC;
  public static final int defaultSpringColor = 16753595;
  public static final int defaultSummerColor = 16705834;
  public static final int defaultAutumnColor = 12344871;
  public static final int defaultWinterColor = 14679292;
  public static final int defaultDryColor = 16745216;
  public static final int defaultWetColor = 2068975;
  public static final int defaultXOffset = 2;
  public static final int defaultYOffset = 2;
  private static ForgeConfigSpec.BooleanValue enableMod;
  private static ForgeConfigSpec.ConfigValue<Location> hudLocation;
  private static ForgeConfigSpec.ConfigValue<Integer> hudX;
  private static ForgeConfigSpec.ConfigValue<Integer> hudY;
  private static ForgeConfigSpec.BooleanValue enableSeasonNameColor;
  private static ForgeConfigSpec.ConfigValue<Integer> springColor;
  private static ForgeConfigSpec.ConfigValue<Integer> summerColor;
  private static ForgeConfigSpec.ConfigValue<Integer> autumnColor;
  private static ForgeConfigSpec.ConfigValue<Integer> winterColor;
  private static ForgeConfigSpec.ConfigValue<Integer> dryColor;
  private static ForgeConfigSpec.ConfigValue<Integer> wetColor;
  private static ForgeConfigSpec.BooleanValue needCalendar;
  private static ForgeConfigSpec.BooleanValue showTropicalSeason;
  private static ForgeConfigSpec.BooleanValue showSubSeason;
  private static ForgeConfigSpec.ConfigValue<ShowDay> showDay;
  private static ForgeConfigSpec.BooleanValue enableMinimapIntegration;
  private static ForgeConfigSpec.BooleanValue showDefaultWhenMinimapHidden;
  private static ForgeConfigSpec.BooleanValue journeyMapAboveMap;
  private static ForgeConfigSpec.BooleanValue journeyMapMacOS;

  static {
    ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    setupConfig(BUILDER);
    GENERAL_SPEC = BUILDER.build();
  }

  private static void setupConfig(ForgeConfigSpec.Builder BUILDER) {
    BUILDER.push("SeasonHUD");
    enableMod = BUILDER.comment("Enable the mod?\n" + "(true/false)\n" + "Default is true.").define("enable_mod", true);

    BUILDER.push("HUD");
    hudLocation = BUILDER.comment(
                             "Part of the screen to display the HUD when no minimap is installed\n" + "Default is TOP_LEFT.")
                         .defineEnum("hud_location", Location.TOP_LEFT);

    hudX = BUILDER.comment(
        "The horizontal offset of the HUD when no minimap is installed (in pixels)\n" + "Default is " + defaultXOffset
            + ".").define("hud_x_position", defaultXOffset);

    hudY = BUILDER.comment(
        "The vertical offset of the HUD when no minimap is installed (in pixels)\n" + "Default is " + defaultYOffset
            + ".").define("hud_y_position", defaultYOffset);

    BUILDER.push("Colors");
    enableSeasonNameColor = BUILDER.comment("Display the season name in a color?\n" + "(true/false)")
                                   .define("season_name_color", true);

    springColor = BUILDER.comment(
        "The RGB color (decimal) for spring.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + defaultSpringColor + ".").defineInRange("spring_color", defaultSpringColor, 0, 16777215);

    summerColor = BUILDER.comment(
        "The RGB color (decimal) for summer.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + defaultSummerColor + ".").defineInRange("summer_color", defaultSummerColor, 0, 16777215);

    autumnColor = BUILDER.comment(
        "The RGB color (decimal) for autumn.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + defaultAutumnColor + ".").defineInRange("autumn_color", defaultAutumnColor, 0, 16777215);

    winterColor = BUILDER.comment(
        "The RGB color (decimal) for winter.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + defaultWinterColor + ".").defineInRange("winter_color", defaultWinterColor, 0, 16777215);

    dryColor = BUILDER.comment(
        " The RGB color (decimal) for dry tropical season.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n"
            + "Default is " + defaultDryColor + ".").defineInRange("dry_color", defaultDryColor, 0, 16777215);

    wetColor = BUILDER.comment(
        "The RGB color (decimal) for wet tropical season.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n"
            + "Default is " + defaultWetColor + ".").defineInRange("wet_color", defaultWetColor, 0, 16777215);
    BUILDER.pop();
    BUILDER.pop();

    BUILDER.push("Season");
    needCalendar = BUILDER.comment(
        "Require the Calendar item to be in the players inventory to show the HUD?\n" + "(true/false)\n"
            + "Default is false.").define("need_calendar", false);

    showTropicalSeason = BUILDER.comment("Show the Tropical seasons (Wet/Dry) in Tropical Biomes.\n"
                                             + "Will not change the season behavior in the biomes.\n" + "(true/false)\n"
                                             + "Default is true.").define("enable_show_tropical_season", true);

    showSubSeason = BUILDER.comment(
        "Show sub-season (i.e. Early Winter, Mid Autumn, Late Spring) instead of basic season?\n" + "(true/false)\n"
            + " Default is true.").define("enable_show_sub_season", true);

    if (Common.platformName().equals("Forge")) {
      showDay = BUILDER.comment("Show the current day of the season/sub-season?\n" + "Default is SHOW_DAY.")
                       .defineEnum("enable_show_day", ShowDay.SHOW_DAY,
                                   Arrays.asList(ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS));
    }

    if (Common.platformName().equals("Fabric")) {
      showDay = BUILDER.comment("Show the current day of the season/sub-season?\n" + "Default is SHOW_DAY.")
                       .defineEnum("enable_show_day", ShowDay.SHOW_DAY,
                                   Arrays.asList(ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS,
                                                 ShowDay.SHOW_WITH_MONTH));
    }
    BUILDER.pop();

    BUILDER.push("Minimap");
    enableMinimapIntegration = BUILDER.comment(
                                          "Enable integration with minimap mods?\n" + "(true/false)\n" + "Default is true.")
                                      .define("enable_minimap_integration", true);

    showDefaultWhenMinimapHidden = BUILDER.comment(
                                              "Show the default SeasonHUD display when the minimap is hidden?\n" + "(true/false)\n" + "Default is false.")
                                          .define("enable_show_minimap_hidden", false);

    BUILDER.push("Journeymap");
    journeyMapAboveMap = BUILDER.comment(
                                    " Show above the JourneyMap minimap, instead of below.\n" + "(true/false)\n" + "Default is false.")
                                .define("enable_above_map", false);

    journeyMapMacOS = BUILDER.comment("Toggle for macOS retina display scaling when using JourneyMap.\n"
                                          + "Enable if the season line is rendering around the halfway point of the screen.\n"
                                          + "(true/false)\n" + "Default is false.").define("enable_macOS", false);
    BUILDER.pop();
    BUILDER.pop();
    BUILDER.pop();
  }

  //SeasonHUD
  public static boolean getEnableMod() {
    return Config.enableMod.get();
  }

  public static void setEnableMod(boolean enable) {
    Config.enableMod.set(enable);
  }

  //HUD
  public static Location getHudLocation() {
    return Config.hudLocation.get();
  }

  public static void setHudLocation(Location location) {
    Config.hudLocation.set(location);
  }

  public static int getHudX() {
    if (Minecraft.getInstance().player == null) {
      return defaultXOffset;
    } else {
      return hudX.get();
    }
  }

  public static void setHudX(int x) {
    Config.hudX.set(x);
  }

  public static int getHudY() {
    if (Minecraft.getInstance().player == null) {
      return defaultYOffset;
    } else {
      return hudY.get();
    }
  }

  public static void setHudY(int y) {
    Config.hudY.set(y);
  }

  //Colors
  public static boolean getEnableSeasonNameColor() {
    return enableSeasonNameColor.get();
  }

  public static void setEnableSeasonNameColor(boolean enable) {
    Config.enableSeasonNameColor.set(enable);
  }

  public static int getSpringColor() {
    return springColor.get();
  }

  public static void setSpringColor(int rgbColor) {
    Config.springColor.set(rgbColor);
  }

  public static int getSummerColor() {
    return summerColor.get();
  }

  public static void setSummerColor(int rgbColor) {
    Config.summerColor.set(rgbColor);
  }

  public static int getAutumnColor() {
    return autumnColor.get();
  }

  public static void setAutumnColor(int rgbColor) {
    Config.autumnColor.set(rgbColor);
  }

  public static int getWinterColor() {
    return winterColor.get();
  }

  public static void setWinterColor(int rgbColor) {
    Config.winterColor.set(rgbColor);
  }

  public static int getDryColor() {
    return dryColor.get();
  }

  public static void setDryColor(int rgbColor) {
    Config.dryColor.set(rgbColor);
  }

  public static int getWetColor() {
    return wetColor.get();
  }

  public static void setWetColor(int rgbColor) {
    Config.wetColor.set(rgbColor);
  }

  //Season
  public static boolean getNeedCalendar() {
    return needCalendar.get();
  }

  public static void setNeedCalendar(boolean enable) {
    Config.needCalendar.set(enable);
  }

  public static boolean getShowTropicalSeason() {
    return showTropicalSeason.get();
  }

  public static void setShowTropicalSeason(boolean enable) {
    Config.showTropicalSeason.set(enable);
  }

  public static boolean getShowSubSeason() {
    return showSubSeason.get();
  }

  public static void setShowSubSeason(boolean enable) {
    Config.showSubSeason.set(enable);
  }

  public static ShowDay getShowDay() {
    return showDay.get();
  }

  public static void setShowDay(ShowDay showDay) {
    Config.showDay.set(showDay);
  }

  public static boolean getShowDefaultWhenMinimapHidden() {
    return showDefaultWhenMinimapHidden.get();
  }

  public static void setShowDefaultWhenMinimapHidden(boolean enable) {
    Config.showDefaultWhenMinimapHidden.set(enable);
  }

  //Minimap
  public static boolean getEnableMinimapIntegration() {
    return Config.enableMinimapIntegration.get();
  }

  public static void setEnableMinimapIntegration(boolean enable) {
    Config.enableMinimapIntegration.set(enable);
  }

  //Journeymap
  public static boolean getJourneyMapAboveMap() {
    return journeyMapAboveMap.get();
  }

  public static void setJourneyMapAboveMap(boolean enable) {
    Config.journeyMapAboveMap.set(enable);
  }

  public static boolean getJourneyMapMacOS() {
    return journeyMapMacOS.get();
  }

  public static void setJourneyMapMacOS(boolean enable) {
    Config.journeyMapMacOS.set(enable);
  }
}