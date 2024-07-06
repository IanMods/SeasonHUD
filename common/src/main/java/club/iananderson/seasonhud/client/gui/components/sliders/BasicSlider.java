package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BasicSlider extends AbstractSliderButton {
  public static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");
  public static int SLIDER_PADDING = 2;
  public Component prefix;
  public Component suffix;
  public double minValue;
  public double maxValue;
  public boolean drawString;
  public boolean canChangeValue;

  public BasicSlider(int x, int y, int width, int height, double initial, boolean drawString) {
    super(x, y, width, height, Component.empty(), 0.0);
    this.value = snapToNearest(initial);
    this.drawString = drawString;
  }

  public BasicSlider(int x, int y, int width, int height, Component prefix, Component suffix, double initial,
      double minValue, double maxValue, boolean drawString) {
    this(x, y, width, height, initial, drawString);
    this.value = snapToNearest(initial);
    this.prefix = prefix;
    this.suffix = suffix;
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.updateMessage();
  }

  public BasicSlider(int x, int y, int width, int height, Component prefix, double initial, double minValue,
      double maxValue, boolean drawString) {
    this(x, y, width, height, prefix, Component.empty(), initial, minValue, maxValue, drawString);
    this.value = snapToNearest(initial);
  }

  public int getTextureY() {
    int i = this.isFocused() && !this.canChangeValue ? 1 : 0;
    return i * 20;
  }

  public int getHandleTextureY() {
    int i = !this.isHovered && !this.canChangeValue ? 2 : 3;
    return i * 20;
  }

  public double getValue() {
    return this.value * (this.maxValue - this.minValue) + this.minValue;
  }

  public void setValue(double newValue) {
    double oldValue = this.value;
    this.value = Mth.clamp(newValue, 0.0, 1.0);
    if (oldValue != this.value) {
      this.applyValue();
    }

    this.updateMessage();
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

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  public double snapToNearest(double value) {
    return (Mth.clamp((float) value, this.minValue, this.maxValue) - this.minValue) / (this.maxValue - this.minValue);
  }

  @Override
  protected void applyValue() {
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Component.literal("").append(this.prefix).append(this.getValueString()).append(this.suffix));
    } else {
      this.setMessage(Component.empty());
    }
  }

  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    Minecraft mc = Minecraft.getInstance();
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX(), this.getY(), 0, this.getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX() + (int) (this.value * (double) (this.width - 8)),
                            this.getY(), 0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}
