package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import net.minecraft.client.gui.components.Button;

public class DropdownButton extends Button {

  protected DropdownButton(int x, int y, int width, int height, MenuButtons buttonType, OnPress onPress) {
    super(x, y, width, height, buttonType.getButtonText(), onPress);
  }

}
