package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

public class BlueSlider extends RgbSlider {

  public BlueSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox);
    this.width = seasonBox.getWidth() + 2;
    this.seasonBox = seasonBox;
    this.maxValue = 255;
    this.initial = Rgb.getBlue(this.season);
    this.b = Rgb.rgbColor(Integer.parseInt(seasonBox.getValue())).getBlue();
    this.value = snapToNearest(this.b);
    this.textColor = ChatFormatting.BLUE;
    this.updateMessage();
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
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}