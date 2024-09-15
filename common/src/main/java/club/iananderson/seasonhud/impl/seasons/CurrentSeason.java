package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import java.time.LocalDateTime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class CurrentSeason {
  private final String currentSeason;
  private final String currentSubSeason;
  private final String seasonFileName;
  private final int seasonDate;
  private final int seasonDuration;
  private final Player player;
  private Style seasonFormat;

  public CurrentSeason(Minecraft mc) {
    this.player = mc.player;
    this.seasonFormat = Style.EMPTY;
    this.currentSeason = Services.SEASON.getCurrentSeason(player);
    this.currentSubSeason = Services.SEASON.getCurrentSubSeason(player);
    this.seasonFileName = Services.SEASON.getSeasonFileName(player);
    this.seasonDate = Services.SEASON.getDate(player);
    this.seasonDuration = Services.SEASON.seasonDuration(player);
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  //Convert Season to lower case (for localized names)
  public String getSeasonStateLower() {
    if ((Config.getShowSubSeason() || Calendar.calendarFoundDetailed()) && currentSubSeason.contains("_")) {
      String lowerSubSeason = currentSubSeason.toLowerCase();
      return currentSeason.toLowerCase() + "." + lowerSubSeason.substring(0, lowerSubSeason.indexOf("_"));
    } else {
      return currentSeason.toLowerCase();
    }
  }

  //Get the current season and match it to the icon for the font
  public String getSeasonIcon() {
    for (Seasons season : Seasons.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getIconChar();
      }
    }
    return "Icon Error";
  }

  //Localized name for the hud with icon
  public Component getText() {
    Component season = new TranslatableComponent("desc.seasonhud.season." + getSeasonStateLower());
    Component text = new TextComponent("");

    switch (Config.getShowDay()) {
      case NONE:
        text = new TranslatableComponent(ShowDay.NONE.getKey(), season);

        if (Calendar.calendarFoundDetailed()) {
          text = new TranslatableComponent(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), season, seasonDate, seasonDuration);
        }
        break;

      case SHOW_DAY:
        text = new TranslatableComponent(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
        break;

      case SHOW_WITH_TOTAL_DAYS:
        text = new TranslatableComponent(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), season, seasonDate, seasonDuration);
        break;

      case SHOW_WITH_MONTH:
        if (Services.SEASON.isSeasonTiedWithSystemTime()) {
          int systemMonth = LocalDateTime.now().getMonth().getValue();
          String systemMonthString = String.valueOf(systemMonth);

          if (systemMonth < 10) {
            systemMonthString = "0" + systemMonthString;
          }

          Component currentMonth = new TranslatableComponent("desc.seasonhud.month." + systemMonthString);

          text = new TranslatableComponent(ShowDay.SHOW_WITH_MONTH.getKey(), season, currentMonth, seasonDate);
        } else {
          text = new TranslatableComponent(ShowDay.SHOW_DAY.getKey(), season, seasonDate);
        }
        break;
    }

    return text;
  }

  //Get the current season and match it to the icon for the font
  public TextColor getTextColor() {
    for (Seasons season : Seasons.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return TextColor.fromRgb(season.getSeasonColor());
      }
    }
    return TextColor.fromRgb(16777215);
  }

  public MutableComponent getSeasonHudTextNoFormat() {
    Component seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", getSeasonIcon()).withStyle(
        Common.SEASON_ICON_STYLE);
    MutableComponent seasonText = getText().copy();

    return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon, seasonText);
  }

  public MutableComponent getSeasonHudText() {
    MutableComponent seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", getSeasonIcon());
    MutableComponent seasonText = getText().copy();

    if (Config.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(getTextColor());
    }
//
//    if (Services.PLATFORM.isModLoaded("modernui")) {
//      return seasonText.withStyle(seasonFormat);
//    } else {
    return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                     seasonText.withStyle(seasonFormat));
//    }
  }

  public MutableComponent getSeasonMenuText(Seasons season, TextColor newRgb, boolean seasonShort) {
    MutableComponent seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", season.getIconChar());
    MutableComponent seasonText = new TranslatableComponent(ShowDay.NONE.getKey(), season.getSeasonName());

    if (Config.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(newRgb);
    }

    if (season == Seasons.DRY && seasonShort) {
      seasonText = new TranslatableComponent("menu.seasonhud.color.season.dry.editbox");
    }

    if (season == Seasons.WET && seasonShort) {
      seasonText = new TranslatableComponent("menu.seasonhud.color.season.wet.editbox");
    }

    return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                     seasonText.withStyle(seasonFormat));
  }
//  public ResourceLocation getIconLocation() {
//    return Common.location("textures/season/" + Services.SEASON.getSeasonFileName(player) + ".png");
//  }
//
//  public void drawIcon(Minecraft mc, PoseStack graphics, float scale, int x, int y) {
//    int iconWidth = 9;
//    int iconSpace = 2;
//    int iconX = x - iconWidth - iconSpace;
//    int iconY = y - 1;
//
//    graphics.pushPose();
//    graphics.scale(scale, scale, 1.0F);
//    RenderSystem.enableBlend();
//    RenderSystem.defaultBlendFunc();
//    mc.getTextureManager().bind(getIconLocation());
//    RenderSystem.color4f(0.25F, 0.25F, 0.25F, 0.75F);
//    GuiComponent.blit(graphics, (int) ((iconX + 1) / scale), (int) ((iconY + 1) / scale), 0, 0, iconWidth, iconWidth,
//                      iconWidth, iconWidth);
//    RenderSystem.color4f(1F, 1F, 1F, 1F);
//    GuiComponent.blit(graphics, (int) (iconX / scale), (int) (iconY / scale), 0, 0, iconWidth, iconWidth, iconWidth,
//                      iconWidth);
//    mc.getTextureManager().bind(GuiComponent.GUI_ICONS_LOCATION);
//    RenderSystem.disableBlend();
//    graphics.popPose();
//  }
}