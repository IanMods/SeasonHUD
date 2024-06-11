package club.iananderson.seasonhud.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
  //Config Builder
  public static final ModConfigSpec GENERAL_SPEC;
  public static ModConfigSpec.BooleanValue enableMod;
  public static ModConfigSpec.ConfigValue<Location> hudLocation;
  public static ModConfigSpec.ConfigValue<Integer> hudX;
  public static ModConfigSpec.ConfigValue<Integer> hudY;
  public static ModConfigSpec.BooleanValue enableSeasonNameColor;
  public static ModConfigSpec.ConfigValue<Integer> springColor;
  public static ModConfigSpec.ConfigValue<Integer> summerColor;
  public static ModConfigSpec.ConfigValue<Integer> autumnColor;
  public static ModConfigSpec.ConfigValue<Integer> winterColor;
  public static ModConfigSpec.ConfigValue<Integer> dryColor;
  public static ModConfigSpec.ConfigValue<Integer> wetColor;
  public static ModConfigSpec.BooleanValue needCalendar;
  public static ModConfigSpec.BooleanValue showTropicalSeason;
  public static ModConfigSpec.BooleanValue showSubSeason;
  public static ModConfigSpec.ConfigValue<ShowDay> showDay;
  public static ModConfigSpec.BooleanValue enableMinimapIntegration;
  public static ModConfigSpec.BooleanValue showDefaultWhenMinimapHidden;
  public static ModConfigSpec.BooleanValue journeyMapAboveMap;
  public static ModConfigSpec.BooleanValue journeyMapMacOS;

  static {
    ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    setupConfig(BUILDER);
    GENERAL_SPEC = BUILDER.build();
  }

  private static void setupConfig(ModConfigSpec.Builder BUILDER) {
    BUILDER.push("SeasonHUD");
    enableMod = BUILDER.comment("""
        Enable the mod?
        (true/false)
        Default is true.""").define("enable_mod", true);

    BUILDER.push("HUD");
    hudLocation = BUILDER.comment("""
        Part of the screen to display the HUD when no minimap is installed
        Default is "TOP_LEFT".""").defineEnum("hud_location", Location.TOP_LEFT);

    hudX = BUILDER.comment("""
        The horizontal offset of the HUD when no minimap is installed (in pixels)
        Default is 0.""").define("hud_x_position", 0);

    hudY = BUILDER.comment("""
        The vertical offset of the HUD when no minimap is installed (in pixels)
        Default is 0.""").define("hud_y_position", 0);

    BUILDER.push("Colors");
    enableSeasonNameColor = BUILDER.comment("""
        Display the season name in a color?
        (true/false)""").define("season_name_color", true);

    springColor = BUILDER.comment("""
        The RGB color (decimal) for spring.
        (256 * 256 * r) + (256 * g) + (b) is the formula
        Default is 16753595.""").defineInRange("spring_color", 16753595, 0, 16777215);

    summerColor = BUILDER.comment("""
        The RGB color (decimal) for summer.
        (256 * 256 * r) + (256 * g) + (b) is the formula
        Default is 16705834.""").defineInRange("summer_color", 16705834, 0, 16777215);

    autumnColor = BUILDER.comment("""
        The RGB color (decimal) for autumn.
        (256 * 256 * r) + (256 * g) + (b) is the formula
        Default is 12344871.""").defineInRange("autumn_color", 12344871, 0, 16777215);

    winterColor = BUILDER.comment("""
        The RGB color (decimal) for winter.
        (256 * 256 * r) + (256 * g) + (b) is the formula
        Default is 14679292.""").defineInRange("winter_color", 14679292, 0, 16777215);

    dryColor = BUILDER.comment("""
        The RGB color (decimal) for dry tropical season.
        (256 * 256 * r) + (256 * g) + (b) is the formula
        Default is 16745216.""").defineInRange("dry_color", 16745216, 0, 16777215);

    wetColor = BUILDER.comment("""
        The RGB color (decimal) for wet tropical season.
        (256 * 256 * r) + (256 * g) + (b) is the formula
        Default is 2068975.""").defineInRange("wet_color", 2068975, 0, 16777215);
    BUILDER.pop();
    BUILDER.pop();

    BUILDER.push("Season");
    needCalendar = BUILDER.comment("""
        Require the Calendar item to be in the players inventory to show the HUD?
        (true/false)
        Default is false.""").define("need_calendar", false);

    showTropicalSeason = BUILDER.comment("""
        Show the Tropical seasons (Wet/Dry) in Tropical Biomes.
        Will not change the season behavior in the biomes.
        (true/false)
        Default is true.""").define("enable_show_tropical_season", true);

    showSubSeason = BUILDER.comment("""
        Show sub-season (i.e. Early Winter, Mid Autumn, Late Spring) instead of basic season?
        (true/false)
        Default is true.""").define("enable_show_sub_season", true);

    showDay = BUILDER.comment("""
        Show the current day of the season/sub-season?
        NONE, SHOW_DAY,
                                                              SHOW_WITH_TOTAL_DAYS
        Default is SHOW_DAY.""").defineEnum("enable_show_day", ShowDay.SHOW_DAY);
    BUILDER.pop();

    BUILDER.push("Minimap");
    enableMinimapIntegration = BUILDER.comment("""
        Enable integration with minimap mods.
        (true/false)
        Default is true.""").define("enable_minimap_integration", true);
    showDefaultWhenMinimapHidden = BUILDER.comment("""
        Show the default SeasonHUD display when the minimap is hidden.
        (true/false)
        Default is false.""").define("enable_show_minimap_hidden", false);

    BUILDER.push("Journeymap");
    journeyMapAboveMap = BUILDER.comment("""
        Show above the JourneyMap minimap, instead of below.
        (true/false)
        Default is false.""").define("enable_above_map", false);
    journeyMapMacOS = BUILDER.comment("""
        Toggle for macOS retina display scaling when using JourneyMap.
        Enable if the season line is rendering around the halfway point of the screen.
        (true/false)
        Default is false.""").define("enable_macOS", false);
    BUILDER.pop();
    BUILDER.pop();
    BUILDER.pop();
  }

  public static boolean getEnableMod() {
    return Config.enableMod.get();
  }

  //SeasonHUD
  public static void setEnableMod(boolean enable) {
    Config.enableMod.set(enable);
  }

  //HUD
  public static void setHudLocation(Location location) {
    Config.hudLocation.set(location);
  }

  public static void setHudX(int x) {
    Config.hudX.set(x);
  }

  public static void setHudY(int y) {
    Config.hudY.set(y);
  }

  //Colors
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
  public static void setNeedCalendar(boolean enable) {
    Config.needCalendar.set(enable);
  }

  public static void setShowTropicalSeason(boolean enable) {
    Config.showTropicalSeason.set(enable);
  }

  public static void setShowSubSeason(boolean enable) {
    Config.showSubSeason.set(enable);
  }

  public static void setShowDay(ShowDay showDay) {
    Config.showDay.set(showDay);
  }

  //Minimap
  public static void setEnableMinimapIntegration(boolean enable) {
    Config.enableMinimapIntegration.set(enable);
  }

  public static void setShowDefaultWhenMinimapHidden(boolean enable) {
    Config.showDefaultWhenMinimapHidden.set(enable);
  }

  //Journeymap
  public static void setJourneyMapAboveMap(boolean enable) {
    Config.journeyMapAboveMap.set(enable);
  }

  public static void setJourneyMapMacOS(boolean enable) {
    Config.journeyMapMacOS.set(enable);
  }
}