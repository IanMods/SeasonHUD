package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
  public static final ForgeConfigSpec GENERAL_SPEC;
  public static final int DEFAULT_SPRING_COLOR = 16753595;
  public static final int DEFAULT_SUMMER_COLOR = 16705834;
  public static final int DEFAULT_AUTUMN_COLOR = 12344871;
  public static final int DEFAULT_WINTER_COLOR = 14679292;
  public static final int DEFAULT_DRY_COLOR = 16745216;
  public static final int DEFAULT_WET_COLOR = 2068975;
  public static final int DEFAULT_X_OFFSET = 2;
  public static final int DEFAULT_Y_OFFSET = 2;
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
  private static ForgeConfigSpec.BooleanValue calanderDetailMode;
  private static ForgeConfigSpec.BooleanValue showTropicalSeason;
  private static ForgeConfigSpec.BooleanValue showSubSeason;
  private static ForgeConfigSpec.ConfigValue<ShowDay> showDay;
  private static ForgeConfigSpec.BooleanValue enableMinimapIntegration;
  private static ForgeConfigSpec.BooleanValue showDefaultWhenMinimapHidden;
  private static ForgeConfigSpec.BooleanValue journeyMapAboveMap;
  private static ForgeConfigSpec.BooleanValue journeyMapMacOS;

  static {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    setupConfig(builder);
    GENERAL_SPEC = builder.build();
  }

  private Config() {
  }

  private static void setupConfig(ForgeConfigSpec.Builder builder) {
    builder.push("SeasonHUD");
    enableMod = builder.comment("Enable the mod?\n" + "(true/false)\n" + "Default is true.").define("enable_mod", true);

    builder.push("HUD");
    hudLocation = builder.comment("Where to display the Hud when no minimap is installed.\n" + "Default is TOP_LEFT.")
                         .defineEnum("hud_location", Location.TOP_LEFT);

    hudX = builder.comment(
        "The horizontal offset of the HUD when no minimap is installed (in pixels)\n" + "Default is " + DEFAULT_X_OFFSET
            + ".").define("hud_x_position", DEFAULT_X_OFFSET);

    hudY = builder.comment(
        "The vertical offset of the HUD when no minimap is installed (in pixels)\n" + "Default is " + DEFAULT_Y_OFFSET
            + ".").define("hud_y_position", DEFAULT_Y_OFFSET);

    builder.push("Colors");
    enableSeasonNameColor = builder.comment("Display the season name in a color?\n" + "(true/false)")
                                   .define("season_name_color", true);

    springColor = builder.comment(
        "The RGB color (decimal) for spring.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_SPRING_COLOR + ".").defineInRange("spring_color", DEFAULT_SPRING_COLOR, 0, 16777215);

    summerColor = builder.comment(
        "The RGB color (decimal) for summer.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_SUMMER_COLOR + ".").defineInRange("summer_color", DEFAULT_SUMMER_COLOR, 0, 16777215);

    autumnColor = builder.comment(
        "The RGB color (decimal) for autumn.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_AUTUMN_COLOR + ".").defineInRange("autumn_color", DEFAULT_AUTUMN_COLOR, 0, 16777215);

    winterColor = builder.comment(
        "The RGB color (decimal) for winter.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_WINTER_COLOR + ".").defineInRange("winter_color", DEFAULT_WINTER_COLOR, 0, 16777215);

    dryColor = builder.comment(
        " The RGB color (decimal) for dry tropical season.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n"
            + "Default is " + DEFAULT_DRY_COLOR + ".").defineInRange("dry_color", DEFAULT_DRY_COLOR, 0, 16777215);

    wetColor = builder.comment(
        "The RGB color (decimal) for wet tropical season.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n"
            + "Default is " + DEFAULT_WET_COLOR + ".").defineInRange("wet_color", DEFAULT_WET_COLOR, 0, 16777215);
    builder.pop();
    builder.pop();

    builder.push("Season");
    needCalendar = builder.comment(
        "Require the calendar item to be in the players inventory to show the HUD?\n" + "(true/false)\n"
            + "Default is false.").define("need_calendar", false);

    calanderDetailMode = builder.comment(
                                    "Having the calendar item shows the detailed version of the HUD" + "Default is false.")
                                .define("calendar_detail", false);

    showTropicalSeason = builder.comment("Show the Tropical seasons (Wet/Dry) in Tropical Biomes.\n"
                                             + "Will not change the season behavior in the biomes.\n" + "(true/false)\n"
                                             + "Default is true.").define("enable_show_tropical_season", true);

    showSubSeason = builder.comment(
        "Show sub-season (i.e. Early Winter, Mid Autumn, Late Spring) instead of basic season?\n" + "(true/false)\n"
            + " Default is true.").define("enable_show_sub_season", true);

    if (Common.sereneSeasonsLoaded()) {
      showDay = builder.comment("Show the day of the current Season/Sub-Season?\n" + "Default is SHOW_DAY.")
                       .defineEnum("enable_show_day", ShowDay.SHOW_DAY,
                                   Arrays.asList(ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS));
    }

    if (Common.fabricSeasonsLoaded()) {
      showDay = builder.comment("Show the current day of the season/sub-season?\n" + "Default is SHOW_DAY.")
                       .defineEnum("enable_show_day", ShowDay.SHOW_DAY,
                                   Arrays.asList(ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS,
                                                 ShowDay.SHOW_WITH_MONTH));
    }
    builder.pop();

    builder.push("Minimap");
    enableMinimapIntegration = builder.comment(
                                          "Enable integration with minimap mods?\n" + "(true/false)\n" + "Default is true.")
                                      .define("enable_minimap_integration", true);

    showDefaultWhenMinimapHidden = builder.comment(
                                              "Show the default SeasonHUD display when the minimap is hidden?\n" + "(true/false)\n" + "Default is false.")
                                          .define("enable_show_minimap_hidden", false);

    builder.push("Journeymap");
    journeyMapAboveMap = builder.comment(
        "Display the season above the JourneyMap minimap, instead of below.\n" + "(true" + "/false)\n"
            + "Default is false.").define("enable_above_map", false);

    journeyMapMacOS = builder.comment("Toggle for macOS retina display scaling when using JourneyMap.\n"
                                          + "Enable if the season line is rendering around the halfway point of the screen.\n"
                                          + "(true/false)\n" + "Default is false.").define("enable_macOS", false);
    builder.pop();
    builder.pop();
    builder.pop();
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
      return DEFAULT_X_OFFSET;
    } else {
      return hudX.get();
    }
  }

  public static void setHudX(int x) {
    Config.hudX.set(x);
  }

  public static int getHudY() {
    if (Minecraft.getInstance().player == null) {
      return DEFAULT_Y_OFFSET;
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

  public static boolean getCalanderDetailMode() {
    return calanderDetailMode.get();
  }

  public static void setCalanderDetailMode(boolean enable) {
    Config.calanderDetailMode.set(enable);
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