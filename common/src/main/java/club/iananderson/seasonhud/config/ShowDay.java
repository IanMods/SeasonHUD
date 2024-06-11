package club.iananderson.seasonhud.config;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum ShowDay {
  NONE(0, "none"),

  SHOW_DAY(1, "showDay"),

  SHOW_WITH_TOTAL_DAYS(2, "totalDays"),

  SHOW_WITH_MONTH(3, "showMonth");

  private final int idNum;
  private final String currentDayDisplay;
  private final Component dayDisplayName;

  ShowDay(int id, String dayType) {
    this.idNum = id;
    this.currentDayDisplay = dayType;
    this.dayDisplayName = new TranslatableComponent("showday.seasonhud." + dayType);
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
}