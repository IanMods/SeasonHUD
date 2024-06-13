package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RgbSlider extends AbstractSliderButton {
  public static int SLIDER_PADDING = 2;
  public final boolean drawString;
  public boolean enableColor = Config.enableSeasonNameColor.get();
  public SeasonList season;
  public ColorEditBox seasonBox;
  public int minValue;
  public int maxValue;
  public int r;
  public int g;
  public int b;
  public int rgb;
  public double initial;
  public boolean drawString;
  public ChatFormatting textColor;
  private boolean canChangeValue;

  private RgbSlider(int x, int y, int width, int height, double initial) {
    super(x, y, width, height, CommonComponents.EMPTY, initial);
    this.initial = initial;
    this.drawString = true;
  }

  public RgbSlider(int x, int y, ColorEditBox seasonBox) {
    this(x, y, seasonBox.getWidth() + 2, seasonBox.getHeight() - 6, Integer.parseInt(seasonBox.getValue()));
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

  public double snapToNearest(double value) {
    return (Mth.clamp((float) value, this.minValue, this.maxValue) - this.minValue) / (this.maxValue - this.minValue);
  }

  public void setSliderValue(int newValue) {
  }

  public double getValue() {
    return this.value * (this.maxValue - this.minValue) + this.minValue;
  }

  public long getValueLong() {
    return Math.round(this.getValue());
  }

  public int getValueInt() {
    return (int) this.getValueLong();
  }

  public String getValueString() {
    return String.valueOf(this.getValueInt());
  }

  protected void applyValue() {
  }

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    super.renderWidget(graphics, mouseX, mouseY, partialTick);
//    DrawUtil.blitWithBorder(graphics, this.getSprite(), this.getX(), this.getY(), 0, this.getTextureY(), this.width,
//                            this.height, 200, 20, 2, 3, 2, 2);
//    DrawUtil.blitWithBorder(graphics, this.getHandleSprite(), this.getX() + (int) (this.value * (double) (this.width - 8)),
//                            this.getY(), 0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
//    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}