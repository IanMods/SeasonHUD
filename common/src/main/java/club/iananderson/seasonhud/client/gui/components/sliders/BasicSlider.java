package club.iananderson.seasonhud.client.gui.components.sliders;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BasicSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  protected static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");
  protected final boolean drawString;
  protected boolean canChangeValue;
  protected double minValue;
  protected double maxValue;

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial) {
    super(x, y, width, height, Component.empty(), 0.0);
    this.drawString = drawString;
    this.value = snapToNearest(initial);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue) {
    this(x, y, width, height, drawString, initial);
    this.minValue = minValue;
    this.maxValue = maxValue;
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

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  protected double snapToNearest(double value) {
    return (Mth.clamp((float) value, this.minValue, this.maxValue) - this.minValue) / (this.maxValue - this.minValue);
  }

  public double getValue() {
    return this.value * (this.maxValue - this.minValue) + this.minValue;
  }

  protected void setValue(double newValue) {
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

  @Override
  protected void applyValue() {
  }

  @Override
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (CommonInputs.selected(keyCode)) {
      this.canChangeValue = !this.canChangeValue;
      return true;
    } else {
      if (this.canChangeValue) {
        boolean bl = keyCode == 263;
        if (bl || keyCode == 262) {
          float f = bl ? -1.0F : 1.0F;
          this.setValue(this.value + (f / (this.width - 8)));
          return true;
        }
      }

      return false;
    }
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Component.literal(this.getValueString()));
    } else {
      this.setMessage(Component.empty());
    }
  }

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    super.renderWidget(graphics, mouseX, mouseY, partialTick);
  }
}