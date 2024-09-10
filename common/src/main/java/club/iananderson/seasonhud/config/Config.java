package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
  public static final ModConfigSpec GENERAL_SPEC;
  public static final int DEFAULT_SPRING_COLOR = 16753595;
  public static final int DEFAULT_SUMMER_COLOR = 16705834;
  public static final int DEFAULT_AUTUMN_COLOR = 12344871;
  public static final int DEFAULT_WINTER_COLOR = 14679292;
  public static final int DEFAULT_DRY_COLOR = 16745216;
  public static final int DEFAULT_WET_COLOR = 2068975;
  public static final int DEFAULT_X_OFFSET = 2;
  public static final int DEFAULT_Y_OFFSET = 2;
  private static ModConfigSpec.BooleanValue enableMod;
  private static ModConfigSpec.ConfigValue<Location> hudLocation;
  private static ModConfigSpec.ConfigValue<Integer> hudX;
  private static ModConfigSpec.ConfigValue<Integer> hudY;
  private static ModConfigSpec.BooleanValue enableSeasonNameColor;
  private static ModConfigSpec.ConfigValue<Integer> springColor;
  private static ModConfigSpec.ConfigValue<Integer> summerColor;
  private static ModConfigSpec.ConfigValue<Integer> autumnColor;
  private static ModConfigSpec.ConfigValue<Integer> winterColor;
  private static ModConfigSpec.ConfigValue<Integer> dryColor;
  private static ModConfigSpec.ConfigValue<Integer> wetColor;
  private static ModConfigSpec.BooleanValue needCalendar;
  private static ModConfigSpec.BooleanValue showTropicalSeason;
  private static ModConfigSpec.BooleanValue showSubSeason;
  private static ModConfigSpec.ConfigValue<ShowDay> showDay;
  private static ModConfigSpec.BooleanValue enableMinimapIntegration;
  private static ModConfigSpec.BooleanValue showDefaultWhenMinimapHidden;

  static {
    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    setupConfig(builder);
    GENERAL_SPEC = builder.build();
  }

  private Config() {
  }

  private static void setupConfig(ModConfigSpec.Builder builder) {
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

    showTropicalSeason = builder.comment("Show the Tropical seasons (Wet/Dry) in Tropical Biomes.\n"
                                             + "Will not change the season behavior in the biomes.\n" + "(true/false)\n"
                                             + "Default is true.").define("enable_show_tropical_season", true);

    showSubSeason = builder.comment(
        "Show sub-season (i.e. Early Winter, Mid Autumn, Late Spring) instead of basic season?\n" + "(true/false)\n"
            + " Default is true.").define("enable_show_sub_season", true);

    showDay = builder.comment("""
                                  Show the current day of the season/sub-season?
                                  NONE, SHOW_DAY,
                                                                                        SHOW_WITH_TOTAL_DAYS
                                  Default is SHOW_DAY.""").defineEnum("enable_show_day", ShowDay.SHOW_DAY);
    builder.pop();

    builder.push("Minimap");
    enableMinimapIntegration = builder.comment(
                                          "Enable integration with minimap mods?\n" + "(true/false)\n" + "Default is true.")
                                      .define("enable_minimap_integration", true);
    showDefaultWhenMinimapHidden = builder.comment(
                                              "Show the default SeasonHUD display when the minimap is hidden?\n" + "(true/false)\n" + "Default is false.")
                                          .define("enable_show_minimap_hidden", false);
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
}