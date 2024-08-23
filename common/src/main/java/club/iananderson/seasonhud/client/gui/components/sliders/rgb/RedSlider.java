package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;

public class RedSlider extends RgbSlider {
  public RedSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox);
    this.maxValue = 255;
    this.seasonBox = seasonBox;
    this.r = Rgb.rgbColor(Integer.parseInt(seasonBox.getValue())).getRed();
    this.value = snapToNearest(this.r);
    this.textColor = ChatFormatting.RED;
    this.updateMessage();
  }

  public void setSliderValue(int newValue) {
    int oldValue = (int) this.value;
    this.value = snapToNearest(Rgb.rgbColor(newValue).getRed());
    if (!Mth.equal(oldValue, this.value)) {
      this.r = Rgb.rgbColor(newValue).getRed();
    }

    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.g = Rgb.getGreen(season);
    this.b = Rgb.getBlue(season);
    this.rgb = Rgb.rgbInt(getValueInt(), this.g, this.b);

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}