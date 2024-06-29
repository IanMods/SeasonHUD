package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;

public enum ShowDay {
  NONE(0, "none", "desc.seasonhud.summary"),

  SHOW_DAY(1, "showDay", "desc.seasonhud.detailed"),

  SHOW_WITH_TOTAL_DAYS(2, "totalDays", "desc.seasonhud.detailed.total"),

  SHOW_WITH_MONTH(3, "showMonth", "desc.seasonhud.month");

  private final int idNum;
  private final String currentDayDisplay;
  private final Component dayDisplayName;
  private final String key;
  private ShowDay[] showDayValues;

  ShowDay(int id, String dayType, String key) {
    this.idNum = id;
    this.currentDayDisplay = dayType;
    this.dayDisplayName = Component.translatable("showday.seasonhud." + dayType);
    this.key = key;
  }

  public static ArrayList<ShowDay> getValues() {
    ArrayList<ShowDay> values = new ArrayList<>(List.of(ShowDay.values()));

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