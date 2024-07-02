package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.DrawUtil;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RgbSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  private static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");
  public final boolean drawString;
  public final boolean enableColor = Config.getEnableSeasonNameColor();
  public SeasonList season;
  public ColorEditBox seasonBox;
  public int minValue;
  public int maxValue;
  public int r;
  public int g;
  public int b;
  public int rgb;
  public double initial;
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

  public int getTextureY() {
    int i = this.isFocused() && !this.canChangeValue ? 1 : 0;
    return i * 20;
  }

  public int getHandleTextureY() {
    int i = !this.isHovered && !this.canChangeValue ? 2 : 3;
    return i * 20;
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

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    Minecraft mc = Minecraft.getInstance();

    if (!enableColor) {
      this.active = false;
      this.isHovered = false;
    }

    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX(), this.getY(), 0, this.getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX() + (int) (this.value * (double) (this.width - 8)),
                            this.getY(), 0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
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

  protected void applyValue() {
  }
}