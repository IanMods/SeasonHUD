package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.components.ColorEditBox;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class RedSlider extends RgbSlider {

  private final ColorEditBox seasonBox;

  public RedSlider(int x, int y, ColorEditBox seasonBox) {
    super(x, y, seasonBox);
    this.width = seasonBox.getWidth() + 2;
    this.seasonBox = seasonBox;
    this.maxValue = 255;
    this.initial = Rgb.getRed(this.season);
    this.r = Rgb.rgbColor(Integer.parseInt(seasonBox.getValue())).getRed();
    this.value = snapToNearest(this.r);
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
  protected void updateMessage() {
    Component colorString = Component.literal(this.getValueString());
    if (this.drawString) {
      this.setMessage(colorString.copy().withStyle(ChatFormatting.RED));

      if (!Config.enableSeasonNameColor.get()) {
        this.setMessage(colorString.copy().withStyle(ChatFormatting.GRAY));
      }
    } else {
      this.setMessage(Component.empty());
    }
  }

  @Override
  protected void applyValue() {
    this.g = Rgb.getGreen(this.season);
    this.b = Rgb.getBlue(this.season);
    this.rgb = Rgb.rgbInt(this.getValueInt(), this.g, this.b);

    Rgb.setRgb(this.season, this.rgb);
    seasonBox.setValue(String.valueOf(this.rgb));
  }
}