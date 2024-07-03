package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class RgbSlider extends BasicSlider {
  public static int SLIDER_PADDING = 2;
  public final boolean enableColor = Config.getEnableSeasonNameColor();
  public SeasonList season;
  public ColorEditBox seasonBox;
  public int r;
  public int g;
  public int b;
  public int rgb;
  public double initial;
  public ChatFormatting textColor;

  public RgbSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox.getWidth() + 2, seasonBox.getHeight() - 6, Integer.parseInt(seasonBox.getValue()));
    this.minValue = 0;
    this.maxValue = 16777215;
    this.season = seasonBox.getSeason();
    this.rgb = Integer.parseInt(seasonBox.getValue());
    this.r = Rgb.rgbColor(this.rgb).getRed();
    this.g = Rgb.rgbColor(this.rgb).getGreen();
    this.b = Rgb.rgbColor(this.rgb).getBlue();
    this.value = snapToNearest(this.rgb);
    this.textColor = ChatFormatting.WHITE;
    this.updateMessage();
  }

  @Override
  public void onClick(double x, double y) {
    if (enableColor) {
      super.onClick(x, y);
    }
  }

  @Override
  protected void onDrag(double d, double e, double f, double g) {
    if (enableColor) {
      super.onDrag(d, e, f, g);
    }
  }

  public void setSliderValue(int newValue) {
  }

  @Override
  public String getValueString() {
    return String.valueOf(this.getValueInt());
  }

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    if (!enableColor) {
      this.active = false;
      this.isHovered = false;
    }

    super.renderWidget(graphics, mouseX, mouseY, partialTick);
  }

  @Override
  protected void updateMessage() {
    Component colorString = Component.literal(this.getValueString());

    if (this.drawString) {
      this.setMessage(colorString.copy().withStyle(textColor));

      if (!enableColor) {
        this.setMessage(colorString.copy().withStyle(ChatFormatting.GRAY));
      }
    } else {
      this.setMessage(Component.empty());
    }
  }
}