package club.iananderson.seasonhud.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;

public enum ShowDay {
  NONE(0, "none", "desc.seasonhud.hud.summary"),

  SHOW_DAY(1, "day", "desc.seasonhud.hud.detailed"),

  SHOW_WITH_TOTAL_DAYS(2, "totalDays", "desc.seasonhud.hud.detailed.total");

  private final int idNum;
  private final String currentDayDisplay;
  private final Component dayDisplayName;
  private final String key;

  ShowDay(int id, String dayType, String key) {
    this.idNum = id;
    this.currentDayDisplay = dayType;
    this.dayDisplayName = Component.translatable("desc.seasonhud.showday." + dayType);
    this.key = key;
  }

  public static List<ShowDay> getValues() {
    return new ArrayList<>(List.of(ShowDay.values()));
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