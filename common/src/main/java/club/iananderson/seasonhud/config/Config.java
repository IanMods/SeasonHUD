package club.iananderson.seasonhud.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {


    //Config Builder
    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(BUILDER);
        GENERAL_SPEC = BUILDER.build();
    }

    public static ForgeConfigSpec.BooleanValue enableMod;
    public static ForgeConfigSpec.BooleanValue needCalendar;
    public static ForgeConfigSpec.ConfigValue<Integer> hudX;
    public static ForgeConfigSpec.ConfigValue<Integer> hudY;
    public static ForgeConfigSpec.ConfigValue<Location> hudLocation;
    public static ForgeConfigSpec.BooleanValue showSubSeason;
    public static ForgeConfigSpec.BooleanValue showDay;
    public static ForgeConfigSpec.BooleanValue showTropicalSeason;
    public static ForgeConfigSpec.BooleanValue showMinimapHidden;
    public static ForgeConfigSpec.BooleanValue journeyMapAboveMap;
    public static ForgeConfigSpec.BooleanValue journeyMapMacOS;




    private static void setupConfig(ForgeConfigSpec.Builder BUILDER){
        BUILDER.push("Configs for SeasonHUD");
        BUILDER.push("HUD:");

        enableMod = BUILDER
                .comment("Enable the mod? \n (true/false)")
                .define("enable_mod",true);

        needCalendar = BUILDER
                .comment("Require the Calender item to be in the players inventory to show the HUD? \n (true/false)")
                .define("need_calendar",false);

        hudX = BUILDER
                .comment("The horizontal offset of the HUD when no minimap is installed (in pixels)\n Default is 0")
                .define("hud_x_position",0);

        hudY = BUILDER
                .comment("The vertical offset of the HUD when no minimap is installed (in pixels)\n Default is 0")
                .define("hud_y_position",0);

        hudLocation = BUILDER
                .comment("Part of the screen to display the HUD when no minimap is installed\n Default is TOP_LEFT")
                .defineEnum("hud_location",Location.TOP_LEFT);

        showSubSeason = BUILDER
                .comment("Show sub-season (i.e. Early Winter, Late Autumn) instead of basic season? \n (true/false)")
                .define("enable_show_sub_season",true);

        showDay = BUILDER
                .comment("Show the current day of the season/sub-season? \n (true/false)")
                .define("enable_show_day",true);

        showTropicalSeason = BUILDER
                .comment("Show the Tropical seasons (Wet/Dry) in Tropical Biomes" +
                        "\n Will not change the season behavior"+
                        "\n (true/false)")
                .define("enable_show_tropical_season",true);

        showMinimapHidden = BUILDER
                .comment("Show the default SeasonHUD when the Minimap is hidden \n (true/false)")
                .define("enable_show_minimap_hidden",false);

        journeyMapAboveMap = BUILDER
                .comment("Show above the Journeymap map, instead of below. \n (true/false)")
                .define("enable_above_map",false);

        journeyMapMacOS = BUILDER
                .comment("Toggle for MacOS retina display scaling. \n (true/false)")
                .define("enable_MacOS",false);

        BUILDER.pop();
        BUILDER.pop();
    }

    public static void setShowSubSeason(boolean showSubSeason) {
        Config.showSubSeason.set(showSubSeason);
    }
    public static void setShowDay(boolean showDay) {
        Config.showDay.set(showDay);
    }
    public static void setShowTropicalSeason(boolean showTropicalSeason) {
        Config.showTropicalSeason.set(showTropicalSeason);
    }

    public static void setHudX(int hudX) {
        Config.hudX.set(hudX);
    }
    public static void setHudY(int hudY) {
        Config.hudY.set(hudY);
    }
    public static void setHudLocation(Location location) {
        Config.hudLocation.set(location);
    }
    public static void setEnableMod(boolean enableMod) {
        Config.enableMod.set(enableMod);
    }
    public static void setNeedCalendar(boolean needCalendar) {
        Config.needCalendar.set(needCalendar);
    }
    public static void setShowMinimapHidden(boolean showMinimapHidden) {
        Config.showMinimapHidden.set(showMinimapHidden);
    }
    public static void setJourneyMapAboveMap(boolean journeyMapAboveMap) {
        Config.journeyMapAboveMap.set(journeyMapAboveMap);
    }
    public static void setJourneyMapMacOS(boolean journeyMapMacOS) {
        Config.journeyMapMacOS.set(journeyMapMacOS);
    }

}
