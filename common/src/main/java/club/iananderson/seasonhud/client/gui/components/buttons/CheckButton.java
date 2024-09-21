package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.Common;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CheckButton extends AbstractButton {
  private static final ResourceLocation TEXTURE = Common.location("textures/gui/checkbox.png");
  private static final int TEXT_COLOR = 14737632;
  private final float scale;
  private final OnPress onPress;
  private boolean selected;

  public CheckButton(int x, int y, int width, int height, Component text, float scale, OnPress onPress,
      boolean selected) {
    super(x, y, width, height, text);
    this.scale = scale;
    this.onPress = onPress;
    this.selected = selected;
  }

  public CheckButton(int x, int y, Component component, float scale, OnPress onPress, boolean selected) {
    this(x, y, (int) (20 * scale), (int) (20 * scale), component, scale, onPress, selected);
  }

  public void onPress() {
    this.selected = !this.selected;
    this.onPress.onPress(this);
  }

  public boolean selected() {
    return this.selected;
  }

  public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    if (this.active) {
      if (this.isFocused()) {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Component.translatable("narration.checkbox.usage.focused"));
      } else {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Component.translatable("narration.checkbox.usage.hovered"));
      }
    }
  }

  public void renderWidget(GuiGraphics graphics, int i, int j, float f) {
    Minecraft mc = Minecraft.getInstance();
    Font font = mc.font;
    RenderSystem.enableDepthTest();
    graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    graphics.pose().pushPose();
    graphics.pose().scale(scale, scale, 1);
    graphics.blit(TEXTURE, (int) (this.getX() / scale), (int) (this.getY() / scale), this.isFocused() ? 20.0F : 0.0F,
                  this.selected ? 20.0F : 0.0F, (int) (this.width / scale), (int) (this.height / scale), 64, 64);
    graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

    graphics.pose().translate((this.width / scale) + 4, ((this.height / scale) - 8) / 2, 0);
    graphics.drawString(font, this.getMessage(), (int) (this.getX() / scale), (int) (this.getY() / scale),
                        14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    graphics.pose().popPose();
  }

  public interface OnPress {
    void onPress(CheckButton button);
  }
}