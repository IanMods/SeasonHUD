package club.iananderson.seasonhud.client.gui.components;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.client.gui.screens.ColorScreen;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public class ColorEditBox extends EditBox {

  public final SeasonList season;
  private final int errorColor = 16733525;
  public static final int PADDING = 4;
  public static final int BOX_WIDTH = 80;
  public static final int BOX_HALF_WIDTH = BOX_WIDTH / 2;
  public static final int BOX_HEIGHT = 20;
  private final int seasonColor;
  private int newSeasonColor;
  private static final Component INVALID_COLOR = Component.translatable(
      "menu.seasonhud.color.editbox.invalidColor");

  public ColorEditBox(Font font, int x, int y, SeasonList season) {
    super(font, x, y, BOX_WIDTH, BOX_HEIGHT, season.getSeasonName());
    this.season = season;
    this.seasonColor = season.getSeasonColor();
    this.newSeasonColor = this.seasonColor;
    this.setMaxLength(8);
    this.setValue(String.valueOf(this.seasonColor));
    this.setResponder(colorString -> {
      if (validate(colorString)) {
        this.setTextColor(0xffffff);
        int colorInt = Integer.parseInt(colorString);

        if (colorInt != newSeasonColor) {
          newSeasonColor = colorInt;
          this.setValue(colorString);
        }

        ColorScreen.doneButton.active = true;
      } else {
        this.setTextColor(errorColor);
        ColorScreen.doneButton.active = false;
      }
    });
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
    Rgb.setRgb(this.season, this.newSeasonColor);
    this.season.setColor(this.newSeasonColor);
  }

  public int getColor() {
    return this.seasonColor;
  }

  public int getNewColor() {
    return this.newSeasonColor;
  }

  @Override
  public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {
    super.render(stack, mouseX, mouseY, partialTicks);
    String seasonIcon = this.season.getIconChar();
    String seasonFileName = this.season.getFileName();
    Style SEASON_FORMAT = Style.EMPTY;

    if (Config.enableSeasonNameColor.get()) {
      SEASON_FORMAT = Style.EMPTY.withColor(this.newSeasonColor);
    }

    Component icon = Component.translatable("desc.seasonhud.icon", seasonIcon)
        .withStyle(SEASON_STYLE);
    Component season = Component.translatable("desc.seasonhud.summary",
        Component.translatable("desc.seasonhud." + seasonFileName)).withStyle(SEASON_FORMAT);
    MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined", icon,
        season);

    stack.drawCenteredString(mc.font, seasonCombined, getX() + BOX_HALF_WIDTH,
        getY() - mc.font.lineHeight - PADDING, 0xffffff);
  }
}
