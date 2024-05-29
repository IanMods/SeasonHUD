package club.iananderson.seasonhud.client.gui.components;

import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.Rgb;
import java.util.HashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ColorButton extends Button {

  public static final int BUTTON_WIDTH = ColorEditBox.BOX_WIDTH + 2;
  public static final int BUTTON_HEIGHT = ColorEditBox.BOX_HEIGHT - 2;
  private static final Component DEFAULT = Component.translatable(
      "menu.seasonhud.color.button.default");
  private ColorEditBox colorEditBox;
  private int defaultColor;

  private ColorButton(int x, int y, int width, int height, Component component, OnPress onPress) {
    super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
  }

  public ColorButton(int x, int y, SeasonList season, ColorEditBox colorEditBox, OnPress onPress) {
    this(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, DEFAULT, onPress);
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
  public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {
    refresh();
    String boxValue = this.colorEditBox.getValue();

    if (validate(boxValue)) {
      if (Integer.parseInt(boxValue) == defaultColor) {
        this.active = false;
      }
    }

    super.render(stack, mouseX, mouseY, partialTicks);
  }

  private void refresh() {
    HashMap<String, Integer> defaultColors = Rgb.defaultSeasonMap(this.colorEditBox.season);
    HashMap<String, Integer> currentColors = this.colorEditBox.season.getRgbMap();

    this.active = defaultColors != currentColors;
  }
}