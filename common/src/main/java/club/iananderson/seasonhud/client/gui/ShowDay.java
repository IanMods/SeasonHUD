package club.iananderson.seasonhud.client.gui;

import club.iananderson.seasonhud.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;
import java.util.List;

public enum ShowDay {
    NONE(0, "none", "desc.seasonhud.hud.summary"),

    SHOW_DAY(1, "showDay", "desc.seasonhud.hud.detailed"),

    SHOW_WITH_TOTAL_DAYS(2, "totalDays", "desc.seasonhud.hud.detailed.total"),

    SHOW_WITH_MONTH(3, "showMonth", "desc.seasonhud.hud.month");

    private final int idNum;
    private final String currentDayDisplay;
    private final Component dayDisplayName;
    private final String key;

    ShowDay(int id, String dayType, String key) {
        this.idNum = id;
        this.currentDayDisplay = dayType;
        this.dayDisplayName = new TranslatableComponent("showday.seasonhud." + dayType);
        this.key = key;
    }

    public static List<ShowDay> getValues() {
        List<ShowDay> values = Arrays.asList(ShowDay.values());

        if (Services.PLATFORM.getPlatformName().equals("Forge")) {
            values.remove(SHOW_WITH_MONTH.getId());
        }
        return values;
    }

    public int getId() {
        return this.idNum;
    }

    public String getDayDisplay() {
        return this.currentDayDisplay;
    }

    public Component getDayDisplayName() {
        return this.dayDisplayName;
    }

    public String getKey() {
        return this.key;
    }
}