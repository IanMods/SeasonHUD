package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

public class RedSlider extends RgbSlider {

  public RedSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox);
    this.width = seasonBox.getWidth() + 2;
    this.seasonBox = seasonBox;
    this.maxValue = 255;
    this.initial = Rgb.getRed(this.season);
    this.r = Rgb.rgbColor(Integer.parseInt(seasonBox.getValue())).getRed();
    this.value = snapToNearest(this.r);
    this.textColor = ChatFormatting.RED;
    this.updateMessage();
  }

  @Override
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
    this.g = Rgb.getGreen(this.season);
    this.b = Rgb.getBlue(this.season);
    this.rgb = Rgb.rgbInt(this.getValueInt(), this.g, this.b);

    Rgb.setRgb(this.season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}