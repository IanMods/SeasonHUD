package club.iananderson.seasonhud.client.gui.components.buttons;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MenuButton extends Button {
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 20;

  private MenuButton(int x, int y, Component component, OnPress onPress) {
    super(x, y, MenuButton.BUTTON_WIDTH, MenuButton.BUTTON_HEIGHT, component, onPress, DEFAULT_NARRATION);
  }

  public MenuButton(int x, int y, int width, int height, MenuButtons button, OnPress onPress) {
    super(x, y, width, height, button.getButtonText(), onPress, DEFAULT_NARRATION);
  }

  public MenuButton(int x, int y, MenuButtons button, OnPress onPress) {
    this(x, y, button.getButtonText(), onPress);
  }

  public enum MenuButtons {
    DONE(CommonComponents.GUI_DONE),

    CANCEL(CommonComponents.GUI_CANCEL),

    COLORS(Component.translatable("menu.seasonhud.color.title"));

    private final Component buttonText;

    MenuButtons(Component buttonText) {
      this.buttonText = buttonText;
    }

    public Component getButtonText() {
      return this.buttonText;
    }
  }
}