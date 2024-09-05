package club.iananderson.seasonhud.client.gui.components.boxes;

import club.iananderson.seasonhud.client.gui.screens.ColorScreen;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class ColorEditBox extends EditBox {

  private static final int PADDING = 4;
  private final Seasons boxSeason;
  private final int seasonColor;
  private int newSeasonColor;

  public ColorEditBox(Font font, int x, int y, int width, int height, Seasons season) {
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
    this.setEditable(Config.getEnableSeasonNameColor());
  }

  private static EnumSet<Seasons> seasonListSet() {
    EnumSet<Seasons> set = Seasons.SEASONS_ENUM_LIST.clone();

    if (!Config.getShowTropicalSeason() || !Services.PLATFORM.getPlatformName().equals("Forge")) {
      set.remove(Seasons.DRY);
      set.remove(Seasons.WET);
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
    } catch (NumberFormatException formatException) {
      return false;
    }
  }

  public void save() {
    Rgb.setRgb(this.boxSeason, this.newSeasonColor);
    this.boxSeason.setSeasonColor(this.newSeasonColor);
  }

  public int getColor() {
    return this.seasonColor;
  }

  public int getNewColor() {
    return this.newSeasonColor;
  }

  public Seasons getSeason() {
    return this.boxSeason;
  }

  @Override
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    Minecraft mc = Minecraft.getInstance();
    float textScale = 1;
    int scaledWidth = mc.getWindow().getGuiScaledWidth();
    int widgetTotalSize = ((80 + ColorScreen.WIDGET_PADDING) * seasonListSet().size());
    boolean seasonShort = (scaledWidth < widgetTotalSize);

    MutableComponent seasonCombined = CurrentSeason.getInstance(mc)
                                                   .getSeasonMenuText(this.boxSeason, this.newSeasonColor, seasonShort);

    graphics.pushPose();
    if ((mc.font.width(seasonCombined) > this.getWidth() - PADDING)) {
      textScale = ((float) this.getWidth() - PADDING) / mc.font.width(seasonCombined);
    }
    graphics.scale(textScale, textScale, 1);
    drawCenteredString(graphics, mc.font, seasonCombined, (int) ((this.x + (double) this.getWidth() / 2) / textScale),
                       (int) ((this.y - (mc.font.lineHeight * textScale) - PADDING) / textScale), 0xffffff);
    graphics.popPose();

    super.render(graphics, mouseX, mouseY, partialTicks);
  }
}