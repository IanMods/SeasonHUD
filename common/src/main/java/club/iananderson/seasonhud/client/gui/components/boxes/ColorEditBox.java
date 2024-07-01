package club.iananderson.seasonhud.client.gui.components.boxes;

import club.iananderson.seasonhud.client.gui.screens.ColorScreen;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.Rgb;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.MutableComponent;
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
    this.setEditable(Config.enableSeasonNameColor.get());
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
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    Minecraft mc = Minecraft.getInstance();
    float textScale = 1;
    int scaledWidth = mc.getWindow().getGuiScaledWidth();
    int widgetTotalSize = ((80 + ColorScreen.WIDGET_PADDING) * seasonListSet().size());
    boolean seasonShort = (scaledWidth < widgetTotalSize);

    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonMenuText(this.boxSeason, seasonShort);

    graphics.pose().pushPose();
    if ((mc.font.width(seasonCombined) > this.getWidth() - PADDING)) {
      textScale = ((float) this.getWidth() - PADDING) / mc.font.width(seasonCombined);
    }
    graphics.pose().scale(textScale, textScale, 1);
    graphics.drawCenteredString(mc.font, seasonCombined, (int) ((getX() + (double) this.getWidth() / 2) / textScale),
                                (int) ((getY() - (mc.font.lineHeight * textScale) - PADDING) / textScale), 0xffffff);
    graphics.pose().popPose();

    super.render(graphics, mouseX, mouseY, partialTicks);
  }
}