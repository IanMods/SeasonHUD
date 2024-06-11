package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.ColorEditBox;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class BlueSlider extends RgbSlider {

  private final ColorEditBox seasonBox;

  public BlueSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox);
    this.width = seasonBox.getWidth() + 2;
    this.seasonBox = seasonBox;
    this.maxValue = 255;
    this.initial = Rgb.getBlue(this.season);
    this.b = Rgb.rgbColor(Integer.parseInt(seasonBox.getValue())).getBlue();
    this.value = snapToNearest(this.b);
    this.updateMessage();
  }

  @Override
  protected void updateMessage() {
    Component colorString = Component.literal(this.getValueString());

    if (this.drawString) {
      this.setMessage(colorString.copy().withStyle(ChatFormatting.BLUE));

      if (!Config.enableSeasonNameColor.get()) {
        this.setMessage(colorString.copy().withStyle(ChatFormatting.GRAY));
      }
    } else {
      this.setMessage(Component.empty());
    }
  }

  @Override
  public void setSliderValue(int newValue) {
    int oldValue = (int) this.value;
    this.value = snapToNearest(Rgb.rgbColor(newValue).getBlue());
    if (!Mth.equal(oldValue, this.value)) {
      this.b = Rgb.rgbColor(newValue).getBlue();
    }

    this.updateMessage();
  }

  @Override
  public void applyValue() {
    this.r = Rgb.getRed(this.season);
    this.g = Rgb.getGreen(this.season);
    this.rgb = Rgb.rgbInt(this.r, this.g, this.getValueInt());

    Rgb.setRgb(this.season, this.rgb);
    seasonBox.setValue(String.valueOf(this.rgb));
  }
}