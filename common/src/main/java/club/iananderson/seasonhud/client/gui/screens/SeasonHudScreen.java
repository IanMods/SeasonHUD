package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class SeasonHudScreen extends Screen {
  public static final int MENU_PADDING = 50;
  public static final int TITLE_PADDING = 10;
  public static final int BUTTON_PADDING = 6;
  public static final int BUTTON_WIDTH = 150;
  public static final int BUTTON_HEIGHT = 20;
  public static MenuButton doneButton;
  public static MenuButton cancelButton;
  public final List<AbstractWidget> widgets = new ArrayList<>();
  public final Screen parentScreen;
  public int leftButtonX;
  public int rightButtonX;
  public int row;
  public int buttonStartY = MENU_PADDING;
  public int yOffset = BUTTON_HEIGHT + BUTTON_PADDING;

  public SeasonHudScreen(Screen parentScreen, Component title) {
    super(title);
    this.parentScreen = parentScreen;
    this.minecraft = Minecraft.getInstance();
    this.width = minecraft.getWindow().getGuiScaledWidth();
    this.height = minecraft.getWindow().getGuiScaledHeight();
  }

  public void open() {
    Minecraft.getInstance().setScreen(this);
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }

  public void onDone() {
    Minecraft.getInstance().setScreen(this.parentScreen);
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().setScreen(this.parentScreen);
  }

  private void clearFocus() {
    ComponentPath componentPath = this.getCurrentFocusPath();
    if (componentPath != null) {
      componentPath.applyFocus(false);
    }

  }

  protected void rebuildWidgets() {
    this.clearWidgets();
    this.clearFocus();
    this.init();
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(graphics);

    graphics.drawCenteredString(font, this.getTitle(), this.width / 2, TITLE_PADDING, 16777215);

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    this.widgets.clear();
    super.init();
    leftButtonX = (this.width / 2) - (BUTTON_WIDTH + BUTTON_PADDING);
    rightButtonX = (this.width / 2) + BUTTON_PADDING;

    doneButton = MenuButton.builder(MenuButtons.DONE, press -> this.onDone())
        .withPos(rightButtonX, (this.height - BUTTON_HEIGHT - BUTTON_PADDING))
        .build();

    cancelButton = MenuButton.builder(MenuButtons.CANCEL, press -> this.onClose())
        .withPos(leftButtonX, (this.height - BUTTON_HEIGHT - BUTTON_PADDING))
        .build();

    this.widgets.addAll(Arrays.asList(doneButton, cancelButton));
  }
}
