package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.util.DrawUtil;
import club.iananderson.seasonhud.util.Rgb;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.jetbrains.annotations.NotNull;

public class DefaultColorButton extends Button {
  private static final Component DEFAULT = Component.translatable("menu.seasonhud.button.color.default");
  private ColorEditBox colorEditBox;
  private int defaultColor;

  private DefaultColorButton(int x, int y, int width, int height, OnPress onPress) {
    super(x, y, width, height, DefaultColorButton.DEFAULT, onPress, DEFAULT_NARRATION);
  }

  public DefaultColorButton(int x, int y, Seasons season, ColorEditBox colorEditBox, OnPress onPress) {
    this(x, y, colorEditBox.getWidth() + 2, colorEditBox.getHeight() - 2, onPress);
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

  public int getTextureY() {
    int k = 1;
    if (!this.active) {
      k = 0;
    } else if (this.isHoveredOrFocused()) {
      k = 2;
    }

    return 46 + k * 20;
  }

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  public void renderWidget(@NotNull GuiGraphics graphics, int i, int j, float f) {
    Minecraft mc = Minecraft.getInstance();
    DrawUtil.blitWithBorder(graphics, WIDGETS_LOCATION, this.getX(), this.getY(), 0, getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    FormattedText buttonText = this.getMessage();
    graphics.drawCenteredString(mc.font, Language.getInstance().getVisualOrder(buttonText),
                                this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, getFGColor());
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
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