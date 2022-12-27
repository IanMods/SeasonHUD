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
    public static ForgeConfigSpec.BooleanValue showSubSeason;
    public static ForgeConfigSpec.BooleanValue showDay;


    private static void setupConfig(ForgeConfigSpec.Builder BUILDER){
        BUILDER.push("Configs for SeasonHUD");
        BUILDER.push("HUD:");

        showSubSeason = BUILDER
                .comment("Show sub-season (i.e. Early Winter, Late Autumn) instead of basic season? \n (true/false)")
                .define("enable_show_sub_season",true);

        showDay = BUILDER
                .comment("Show the current day of the season/sub-season? \n (true/false)")
                .define("enable_show_day",true);

        BUILDER.pop();
        BUILDER.pop();
    }

    public static void setShowSubSeason(boolean showSubSeason) {
        Config.showSubSeason.set(showSubSeason);
    }
    public static void setShowDay(boolean showDay) {
        Config.showDay.set(showDay);
    }

}
