package club.iananderson.seasonhud.config;

import java.util.Arrays;
import java.util.List;

import club.iananderson.seasonhud.client.DebugHUD;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collection;
import java.util.function.Supplier;

public class SeasonHUDClientConfigs {

    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }
    public static ForgeConfigSpec.IntValue seasonHUDLocation;
    public static ForgeConfigSpec.BooleanValue debugHUD;

    private static void setupConfig(ForgeConfigSpec.Builder builder){
        builder.push("Configs for SeasonHUD");
        builder.push("HUD");

        seasonHUDLocation = builder
                .comment("The location of the Season HUD: \n 0 -> Off \n 1 -> Top Left Corner \n 2 -> Under Minimap")
                .defineInRange("season_hud_location", 1, 0, 2);

        debugHUD = builder
                .comment("Enable the Debug hud? \n (true/false)")
                .define("enable_debug_hud",false);

        builder.pop();
        builder.pop();
    }

}
