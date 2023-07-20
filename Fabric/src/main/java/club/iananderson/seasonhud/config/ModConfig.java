package club.iananderson.seasonhud.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import static club.iananderson.seasonhud.SeasonHUD.MODID;
import static club.iananderson.seasonhud.config.Location.TOP_LEFT;

@Config(name = MODID)
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static ModConfig INSTANCE;

    public static void init()
    {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    @Comment("Enable the mod? \n (true/false)")
    public boolean enableMod = true;

    @Comment("Require the Calender item to be in the players inventory to show the HUD? \\n (true/false)")
    public boolean needCalendar = false;

    @Comment("The horizontal offset of the HUD when no minimap is installed (in pixels)\n Default is 0")
    public int hudX = 0;

    @Comment("The vertical offset of the HUD when no minimap is installed (in pixels)\\n Default is 0")
    public int hudY = 0;

    @Comment("Part of the screen to display the HUD when no minimap is installed\n Default is TOP_LEFT")
    public Location hudLocation = TOP_LEFT;

    @Comment("Show the current day of the season/sub-season? \\n (true/false)")
    public boolean showDay = true;
}
