package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.impl.seasons.SeasonList;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class DefaultColorButton extends Button {

  private static final Component DEFAULT = new TranslatableComponent("menu.seasonhud.color.button.default");
  private ColorEditBox colorEditBox;
  private int defaultColor;

  private DefaultColorButton(int x, int y, int width, int height, Component component, OnPress onPress) {
    super(x, y, width, height, component, onPress);
  }

  public DefaultColorButton(int x, int y, SeasonList season, ColorEditBox colorEditBox, OnPress onPress) {
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
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    refresh();
    String boxValue = this.colorEditBox.getValue();

    if (validate(boxValue) && Integer.parseInt(boxValue) == defaultColor) {
      this.active = false;
    }

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  private void refresh() {
    HashMap<String, Integer> defaultColors = Rgb.defaultSeasonMap(this.colorEditBox.getSeason());
    HashMap<String, Integer> currentColors = this.colorEditBox.getSeason().getRgbMap();

    this.active = defaultColors != currentColors;
  }
}