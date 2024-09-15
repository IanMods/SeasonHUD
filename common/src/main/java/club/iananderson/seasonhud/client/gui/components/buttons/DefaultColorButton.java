package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class DefaultColorButton extends Button {
  private static final Component DEFAULT = new TranslatableComponent("menu.seasonhud.color.default.button");
  private final ColorEditBox colorEditBox;
  private final int defaultColor;

  private DefaultColorButton(int x, int y, ColorEditBox colorEditBox, OnPress onPress) {
    super(x, y, colorEditBox.getWidth() + 2, colorEditBox.getHeight() - 2, DefaultColorButton.DEFAULT, onPress);
    this.colorEditBox = colorEditBox;
    this.defaultColor = colorEditBox.getSeason().getDefaultColor();
  }

  public static Builder builder(ColorEditBox colorEditBox, OnPress onPress) {
    return new Builder(colorEditBox, onPress);
  }

  private boolean inBounds(int color) {
    int minColor = 0;
    int maxColor = 16777215;

    return color >= minColor && color <= maxColor;
  }

  public boolean validate(String colorString) {
    try {
      int colorInt = Integer.parseInt(colorString);
      return this.inBounds(colorInt);
    } catch (NumberFormatException var) {
      return false;
    }
  }

  public boolean isHoveredOrFocused() {
    return this.isHovered() || this.isFocused();
  }

  public int getTextureY() {
    int k = 1;
    if (!this.active) {
      k = 0;
    } else if (this.isHoveredOrFocused()) {
      k = 2;
    }

    return 46 + k * 20;
  }

  @Override
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    refresh();
    String boxValue = this.colorEditBox.getValue();

    if (validate(boxValue) && Integer.parseInt(boxValue) == defaultColor) {
      this.active = false;
    }

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  private void refresh() {
    Map<String, Integer> defaultColors = Rgb.defaultSeasonMap(this.colorEditBox.getSeason());
    Map<String, Integer> currentColors = this.colorEditBox.getSeason().getRgbMap();

    this.active = defaultColors != currentColors;
  }

  public static class Builder {
    protected final OnPress onPress;
    protected final ColorEditBox colorEditBox;
    protected int x;
    protected int y;

    public Builder(ColorEditBox colorEditBox, OnPress onPress) {
      this.colorEditBox = colorEditBox;
      this.onPress = onPress;
    }

    /**
     * Uses default width = 150 and height = 20
     *
     * @param x The horizontal position of the button
     * @param y The vertical position of the button
     */
    public Builder withPos(int x, int y) {
      this.x = x;
      this.y = y;
      return this;
    }

    public DefaultColorButton build() {
      DefaultColorButton button = new DefaultColorButton(this.x, this.y, this.colorEditBox, this.onPress);
      return button;
    }
  }
}