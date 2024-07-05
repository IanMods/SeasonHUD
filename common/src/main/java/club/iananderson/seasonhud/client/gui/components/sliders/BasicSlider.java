package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.util.DrawUtil;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BasicSlider extends AbstractSliderButton {
  public static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");
  public static int SLIDER_PADDING = 2;
  public DecimalFormat format;
  public Component prefix;
  public Component suffix;
  public double minValue;
  public double maxValue;
  public double stepSize;
  public boolean drawString;
  public boolean canChangeValue;

  public BasicSlider(int x, int y, int width, int height, double initial) {
    super(x, y, width, height, Component.empty(), initial);
  }

  public BasicSlider(int x, int y, int width, int height, double initial, Component prefix, Component suffix,
      double minValue, double maxValue, double currentValue, double stepSize, int precision, boolean drawString) {
    super(x, y, width, height, Component.empty(), initial);
    this.prefix = prefix;
    this.suffix = suffix;
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.stepSize = Math.abs(stepSize);
    this.value = this.snapToNearest((currentValue - minValue) / (maxValue - minValue));
    this.drawString = drawString;
    if (stepSize == 0.0) {
      precision = Math.min(precision, 4);
      StringBuilder builder = new StringBuilder("0");
      if (precision > 0) {
        builder.append('.');
      }

      while (precision-- > 0) {
        builder.append('0');
      }

      this.format = new DecimalFormat(builder.toString());
    } else if (Mth.equal(this.stepSize, Math.floor(this.stepSize))) {
      this.format = new DecimalFormat("0");
    } else {
      this.format = new DecimalFormat(Double.toString(this.stepSize).replaceAll("\\d", "0"));
    }

    this.updateMessage();
  }

  public BasicSlider(int x, int y, int width, int height, Component prefix, double minValue, double maxValue,
      double currentValue, boolean drawString) {
    this(x, y, width, height, 0.0, prefix, Component.empty(), minValue, maxValue, currentValue, 1.0, 0, drawString);
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

  public void setValue(double value) {
    this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
    this.updateMessage();
  }

  public long getValueLong() {
    return Math.round(this.getValue());
  }

  public int getValueInt() {
    return (int) this.getValueLong();
  }

  public String getValueString() {
    return this.format.format(this.getValue());
  }

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  public void setValueFromMouse(double mouseX) {
    this.setSliderValue((mouseX - (double) (this.getX() + 4)) / (double) (this.width - 8));
  }

  public void onClick(double mouseX, double mouseY) {
    this.setValueFromMouse(mouseX);
  }

  protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
    super.onDrag(mouseX, mouseY, dragX, dragY);
    this.setValueFromMouse(mouseX);
  }

  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    boolean flag = keyCode == 263;
    if (flag || keyCode == 262) {
      if (this.minValue > this.maxValue) {
        flag = !flag;
      }

      float f = flag ? -1.0F : 1.0F;
      if (this.stepSize <= 0.0) {
        this.setSliderValue(this.value + (double) (f / (float) (this.width - 8)));
      } else {
        this.setValue(this.getValue() + (double) f * this.stepSize);
      }
    }

    return false;
  }

  public double snapToNearest(double value) {
    if (this.stepSize <= 0.0) {
      return Mth.clamp(value, 0.0, 1.0);
    } else {
      value = Mth.lerp(Mth.clamp(value, 0.0, 1.0), this.minValue, this.maxValue);
      value = this.stepSize * (double) Math.round(value / this.stepSize);
      if (this.minValue > this.maxValue) {
        value = Mth.clamp(value, this.maxValue, this.minValue);
      } else {
        value = Mth.clamp(value, this.minValue, this.maxValue);
      }

      return Mth.map(value, this.minValue, this.maxValue, 0.0, 1.0);
    }
  }

  public void setSliderValue(double value) {
    double oldValue = this.value;
    this.value = this.snapToNearest(value);
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

    this.updateMessage();
  }

  @Override
  protected void applyValue() {
  }

  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Component.literal("").append(this.prefix).append(this.getValueString()).append(this.suffix));
    } else {
      this.setMessage(Component.empty());
    }
  }

  public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    Minecraft mc = Minecraft.getInstance();
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX(), this.getY(), 0, this.getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX() + (int) (this.value * (double) (this.width - 8)),
                            this.getY(), 0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}
