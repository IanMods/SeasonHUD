package club.iananderson.seasonhud.client.gui.components.sliders;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.client.gui.components.ColorEditBox;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.DrawUtil;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RgbSlider extends AbstractSliderButton {

  private static final ResourceLocation SLIDER_LOCATION = new ResourceLocation(
      "textures/gui/slider.png");
  public SeasonList season;
  public ColorEditBox seasonBox;
  public static int SLIDER_PADDING = 2;
  public int minValue;
  public int maxValue;
  public int stepSize;
  public int r;
  public int g;
  public int b;
  public int rgb;
  public double initial;
  public boolean drawString;
  public boolean canChangeValue;

  private RgbSlider(int x, int y, int width, int height, double initial) {
    super(x, y, width, height, CommonComponents.EMPTY, initial);
    this.initial = initial;
    this.drawString = true;
    this.stepSize = 1;
  }

  public RgbSlider(int x, int y, ColorEditBox seasonBox) {
    this(x, y, seasonBox.getWidth() + 2, seasonBox.getHeight() - 6,
        Integer.parseInt(seasonBox.getValue()));
    this.minValue = 0;
    this.maxValue = 16777215;
    this.season = seasonBox.getSeason();
    this.rgb = Integer.parseInt(seasonBox.getValue());
    this.r = Rgb.rgbColor(this.rgb).getRed();
    this.g = Rgb.rgbColor(this.rgb).getGreen();
    this.b = Rgb.rgbColor(this.rgb).getBlue();
    this.value = snapToNearest(this.rgb);
    this.updateMessage();
  }

  public int getTextureY() {
    int i = this.isFocused() && !this.canChangeValue ? 1 : 0;
    return i * 20;
  }

  public int getHandleTextureY() {
    int i = !this.isHovered && !this.canChangeValue ? 2 : 3;
    return i * 20;
  }

  @Override
  protected void updateMessage() {
  }

  public double snapToNearest(double value) {
    return (Mth.clamp((float) value, this.minValue, this.maxValue) - this.minValue) / (
        this.maxValue - this.minValue);
  }

  public void setSliderValue(int newValue) {
    int oldValue = (int) this.value;
    this.value = snapToNearest(newValue);
    if (!Mth.equal(oldValue, this.value)) {
      this.rgb = newValue;
      this.r = Rgb.rgbColor(newValue).getRed();
      this.g = Rgb.rgbColor(newValue).getGreen();
      this.b = Rgb.rgbColor(newValue).getBlue();
    }

    this.updateMessage();
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

  @Override
  protected void applyValue() {
    this.seasonBox.setValue(this.getValueString());
  }

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY,
      float partialTick) {

    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX(), this.getY(), 0,
        this.getTextureY(), this.width, this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION,
        this.getX() + (int) (this.value * (double) (this.width - 8)), this.getY(), 0,
        this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2,
        this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}