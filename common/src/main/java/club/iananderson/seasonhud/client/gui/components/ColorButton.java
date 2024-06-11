package club.iananderson.seasonhud.client.gui.components;

import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.Rgb;
import java.util.HashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ColorButton extends Button {

  private static final Component DEFAULT = Component.translatable("menu.seasonhud.color.button.default");
  private ColorEditBox colorEditBox;
  private int defaultColor;

  private ColorButton(int x, int y, int width, int height, Component component, OnPress onPress) {
    super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
  }

  public ColorButton(int x, int y, SeasonList season, ColorEditBox colorEditBox, OnPress onPress) {
    this(x, y, colorEditBox.getWidth() + 2, colorEditBox.getHeight() - 2, DEFAULT, onPress);
    this.colorEditBox = colorEditBox;
    this.defaultColor = season.getDefaultColor();
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

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    refresh();
    String boxValue = this.colorEditBox.getValue();

    if (validate(boxValue)) {
      if (Integer.parseInt(boxValue) == defaultColor) {
        this.active = false;
      }
    }

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  private void refresh() {
    HashMap<String, Integer> defaultColors = Rgb.defaultSeasonMap(this.colorEditBox.getSeason());
    HashMap<String, Integer> currentColors = this.colorEditBox.getSeason().getRgbMap();

    this.active = defaultColors != currentColors;
  }
}