package club.iananderson.seasonhud.client.gui.components.sliders;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class HudOffsetSlider extends BasicSlider {
  public double defaultValue;

  public HudOffsetSlider(int x, int y, int width, int height, Component prefix, double minValue, double maxValue,
      double currentValue, double defaultValue, boolean drawString) {
    super(x, y, width, height, prefix, minValue, maxValue, currentValue, drawString);
    this.defaultValue = defaultValue;
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    if (this.active && this.visible) {
      if (mouseButton == 1) {
        boolean rightClicked = this.clicked(mouseX, mouseY);
        if (rightClicked) {
          this.playDownSound(Minecraft.getInstance().getSoundManager());
          this.onRightClick(mouseX, mouseY);
        }

      }
    }
    return super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  public void onRightClick(double mouseX, double mouseY) {
    this.setValue(defaultValue);
  }
}
