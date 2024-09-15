package club.iananderson.seasonhud.client.gui;

import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum ShowDay {
  NONE(0, "none", "desc.seasonhud.hud.summary"),

  SHOW_DAY(1, "day", "desc.seasonhud.hud.detailed"),

  SHOW_WITH_TOTAL_DAYS(2, "totalDays", "desc.seasonhud.hud.detailed.total"),

  SHOW_WITH_MONTH(3, "month", "desc.seasonhud.hud.month");

  private final int idNum;
  private final String currentDayDisplay;
  private final Component dayDisplayName;
  private final String key;

  ShowDay(int id, String dayType, String key) {
    this.idNum = id;
    this.currentDayDisplay = dayType;
    this.dayDisplayName = new TranslatableComponent("desc.seasonhud.showday." + dayType);
    this.key = key;
  }

  public static List<ShowDay> getValues() {
    List<ShowDay> values = new ArrayList<>(List.of(ShowDay.values()));

    if (!Services.SEASON.isSeasonTiedWithSystemTime()) {
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