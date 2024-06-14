package club.iananderson.seasonhud.client.gui.components.buttons;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class MenuButton extends Button {
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 20;

  private MenuButton(int x, int y, int width, int height, Component component, OnPress onPress) {
    super(x, y, width, height, component, onPress);
  }

  public MenuButton(int x, int y, int width, int height, MenuButtons button, OnPress onPress) {
    super(x, y, width, height, button.getButtonText(), onPress);
  }

  public MenuButton(int x, int y, MenuButtons button, OnPress onPress) {
    this(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, button.getButtonText(), onPress);
  }

  public enum MenuButtons {
    DONE(CommonComponents.GUI_DONE),

    CANCEL(CommonComponents.GUI_CANCEL),

    COLORS(new TranslatableComponent("menu.seasonhud.color.title"));

    private final Component buttonText;

    MenuButtons(Component buttonText) {
      this.buttonText = buttonText;
    }

    public Component getButtonText() {
      return this.buttonText;
    }
  }
}
