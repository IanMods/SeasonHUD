package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;

public class BlueSlider extends RgbSlider {
  public BlueSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox);
    this.maxValue = 255;
    this.b = Rgb.rgbColor(Integer.parseInt(seasonBox.getValue())).getBlue();
    this.value = snapToNearest(this.b);
    this.textColor = ChatFormatting.BLUE;
    this.updateMessage();
  }

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
    this.r = Rgb.getRed(season);
    this.g = Rgb.getGreen(season);
    this.rgb = Rgb.rgbInt(this.r, this.g, getValueInt());

    Rgb.setRgb(season, this.rgb);
    seasonBox.setValue(String.valueOf(this.rgb));
  }
}