package club.iananderson.seasonhud.client.gui.components.buttons;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class MenuButton extends Button {
  public static final int SMALL_WIDTH = 120;
  public static final int DEFAULT_WIDTH = 150;
  public static final int DEFAULT_HEIGHT = 20;

  protected MenuButton(int x, int y, int width, int height, MenuButtons buttonType, OnPress onPress) {
    super(x, y, width, height, buttonType.getButtonText(), onPress);
  }

  public static Builder builder(MenuButtons button, OnPress onPress) {
    return new Builder(button, onPress);
  }

  public enum MenuButtons {
    DONE(CommonComponents.GUI_DONE),

    CANCEL(CommonComponents.GUI_CANCEL),

    COLORS(new TranslatableComponent("menu.seasonhud.main.color.button").append("...")),

    SEASON(new TranslatableComponent("menu.seasonhud.main.season.button").append("..."));

    private final Component buttonText;

    MenuButtons(Component buttonText) {
      this.buttonText = buttonText;
    }

    public Component getButtonText() {
      return this.buttonText;
    }
  }

  public static class Builder {
    protected final MenuButtons buttonType;
    protected final OnPress onPress;
    protected int x;
    protected int y;
    protected int width = 150;
    protected int height = 20;
    protected Component tooltip;

    public Builder(MenuButtons buttonType, OnPress onPress) {
      this.buttonType = buttonType;
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

    /**
     * Uses default height = 20
     *
     * @param width The width of the button
     */
    public Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public Builder withBounds(int x, int y, int width, int height) {
      this.withPos(x, y);
      this.width = width;
      this.height = height;
      return this;
    }

    public Builder withTooltip(Component tooltip) {
      this.tooltip = tooltip;
      return this;
    }

    public MenuButton build() {
      MenuButton button = new MenuButton(this.x, this.y, this.width, this.height, this.buttonType, this.onPress);
      return button;
    }
  }
}