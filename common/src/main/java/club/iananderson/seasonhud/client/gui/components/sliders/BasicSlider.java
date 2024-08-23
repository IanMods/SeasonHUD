package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.util.DrawUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BasicSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  protected static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("seasonhud:textures/gui/slider.png");
  protected final boolean drawString;
  protected boolean canChangeValue;
  protected double minValue;
  protected double maxValue;

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial) {
    super(x, y, width, height, new TextComponent(""), 0.0);
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

  protected static void renderScrollingString(PoseStack graphics, Font font, Component component, int i, int j, int k,
      int l, int m, int n) {
    int o = font.width(component);
    int var10000 = k + m;
    Objects.requireNonNull(font);
    int p = (var10000 - 9) / 2 + 1;
    int q = l - j;
    int r;
    if (o > q) {
      r = o - q;
      double d = (double) Util.getMillis() / 1000.0;
      double e = Math.max((double) r * 0.5, 3.0);
      double f = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * d / e)) / 2.0 + 0.5;
      double g = Mth.lerp(f, 0.0, (double) r);
      DrawUtil.enableScissor(j, k, l, m);
      GuiComponent.drawString(graphics, font, component, j - (int) g, p, n);
      DrawUtil.disableScissor();
    } else {
      r = Mth.clamp(i, j + o / 2, l - o / 2);
      GuiComponent.drawCenteredString(graphics, font, component, r, p, n);
    }
  }

  protected static void renderScrollingString(PoseStack graphics, Font font, Component component, int i, int j, int k,
      int l, int m) {
    renderScrollingString(graphics, font, component, (i + k) / 2, i, j, k, l, m);
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

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(new TextComponent(this.getValueString()));
    } else {
      this.setMessage(new TextComponent(""));
    }
  }

  protected void renderScrollingString(PoseStack graphics, Font font, int i, int j) {
    int k = this.x + i;
    int l = this.x + this.getWidth() - i;
    renderScrollingString(graphics, font, this.getMessage(), k, this.y, l, this.y + this.getHeight(), j);
  }

  @Override
  protected void renderBg(@NotNull PoseStack graphics, @NotNull Minecraft mc, int mouseX, int mouseY) {
    DrawUtil.blitWithBorder(graphics, this, SLIDER_LOCATION, this.x, this.y, 0, this.getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, this, SLIDER_LOCATION, this.x + (int) (this.value * (double) (this.width - 8)),
                            this.y, 0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}