package club.iananderson.seasonhud.client.gui.components.boxes;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.client.gui.screens.ColorScreen;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.EnumSet;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public class ColorEditBox extends EditBox {

  private static final int PADDING = 4;
  private final SeasonList boxSeason;
  private final int seasonColor;
  private int newSeasonColor;

  public ColorEditBox(Font font, int x, int y, int width, int height, SeasonList season) {
    super(font, x, y, width, height, season.getSeasonName());
    this.boxSeason = season;
    this.seasonColor = season.getSeasonColor();
    this.newSeasonColor = seasonColor;
    this.setMaxLength(8);
    this.setValue(String.valueOf(seasonColor));
    this.setResponder(colorString -> {
      if (validate(colorString)) {
        this.setTextColor(0xffffff);
        int colorInt = Integer.parseInt(colorString);

        if (colorInt != this.newSeasonColor) {
          this.newSeasonColor = colorInt;
          this.setValue(colorString);
        }

        ColorScreen.doneButton.active = true;
      } else {
        this.setTextColor(16733525);
        ColorScreen.doneButton.active = false;
      }
    });
  }

  private static EnumSet<SeasonList> seasonListSet() {
    EnumSet<SeasonList> set = SeasonList.seasons.clone();

    if (!Config.showTropicalSeason.get() || !Services.PLATFORM.getPlatformName().equals("Forge")) {
      set.remove(SeasonList.DRY);
      set.remove(SeasonList.WET);
    }

    return set;
  }

  private boolean inBounds(int color) {
    int minColor = 0;
    int maxColor = 16777215;

    return color >= minColor && color <= maxColor;
  }

  public boolean validate(String colorString) {
    try {
      int colorInt = Integer.parseInt(colorString);
      return this.inBounds(colorInt);
    } catch (NumberFormatException var) {
      return false;
    }
  }

  public void save() {
    Rgb.setRgb(this.boxSeason, this.newSeasonColor);
    this.boxSeason.setColor(this.newSeasonColor);
  }

  public int getColor() {
    return this.seasonColor;
  }

  public int getNewColor() {
    return this.newSeasonColor;
  }

  public SeasonList getSeason() {
    return this.boxSeason;
  }

  @Override
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    String seasonIcon = this.boxSeason.getIconChar();
    String seasonFileName = this.boxSeason.getFileName();
    boolean enableSeasonNameColor = Config.enableSeasonNameColor.get();

    this.setEditable(enableSeasonNameColor);

    Style SEASON_FORMAT = Style.EMPTY;

    if (enableSeasonNameColor) {
      SEASON_FORMAT = Style.EMPTY.withColor(this.newSeasonColor);
    }

    Component icon = Component.translatable("desc.seasonhud.icon", seasonIcon).withStyle(SEASON_STYLE);
    Component season = Component.translatable("desc.seasonhud.summary",
        Component.translatable("desc.seasonhud." + seasonFileName)).withStyle(SEASON_FORMAT);

    int widgetTotalSize = ((80 + ColorScreen.WIDGET_PADDING) * seasonListSet().size());
    int scaledWidth = mc.getWindow().getGuiScaledWidth();

    if (this.boxSeason == SeasonList.DRY && (scaledWidth < widgetTotalSize)) {
      season = Component.translatable("menu.seasonhud.color.editbox.dryColor").withStyle(SEASON_FORMAT);
    }

    if (this.boxSeason == SeasonList.WET && (scaledWidth < widgetTotalSize)) {
      season = Component.translatable("menu.seasonhud.color.editbox.wetColor").withStyle(SEASON_FORMAT);
    }

    MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined", icon, season);

    graphics.pushPose();
    float scale = 1;
    if ((mc.font.width(seasonCombined) > this.getWidth() - PADDING)) {
      scale = ((float) this.getWidth() - PADDING) / mc.font.width(seasonCombined);
    }
    graphics.scale(scale, scale, 1);
    drawCenteredString(graphics, mc.font, seasonCombined, (int) ((this.x + (double) this.getWidth() / 2) / scale),
        (int) ((this.y - (mc.font.lineHeight * scale) - PADDING) / scale), 0xffffff);
    graphics.popPose();

    super.render(graphics, mouseX, mouseY, partialTicks);
  }
}