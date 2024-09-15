package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class HudOffsetSlider extends BasicSlider {
  protected final Component prefix;
  private final double defaultValue;
  private final Supplier<BasicSlider> tooltipSupplier;

  protected HudOffsetSlider(int x, int y, int width, int height, Component prefix, double initial, double minValue,
      double maxValue, double defaultValue, Supplier<BasicSlider> tooltipSupplier) {
    super(x, y, width, height, true, initial, minValue, maxValue);
    this.prefix = prefix;
    this.defaultValue = snapToNearest(defaultValue);
    this.value = snapToNearest(initial);
    this.tooltipSupplier = tooltipSupplier;
    this.updateMessage();
  }

  public static Builder builder(Component prefix) {
    return new Builder(prefix);
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    if (this.active && this.visible && mouseButton == 1) {
      boolean rightClicked = this.clicked(mouseX, mouseY);
      if (rightClicked) {
        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.onRightClick();
      }
    }

    return super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  public void onRightClick() {
    this.setValue(defaultValue);
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(new TextComponent("").append(this.prefix).append(this.getValueString()));
    } else {
      this.setMessage(new TextComponent(""));
    }
  }

  @Override
  public void renderBg(@NotNull PoseStack graphics, @NotNull Minecraft mc, int mouseX, int mouseY) {
    if (Config.getHudLocation() != Location.TOP_LEFT) {
      this.active = false;
    }
    super.renderBg(graphics, mc, mouseX, mouseY);
  }

  public static class Builder {
    protected final Component prefix;
    protected int x;
    protected int y;
    protected int width = 180;
    protected int height = 20;
    protected double minValue;
    protected double maxValue;
    protected double initial;
    protected double defaultValue;
    protected Supplier<BasicSlider> tooltipSupplier;

    public Builder(Component prefix) {
      this.prefix = prefix;
    }

    /**
     * Uses default width = 180 and height = 20
     *
     * @param x The horizontal position of the slider
     * @param y The vertical position of the slider
     */
    public Builder withPos(int x, int y) {
      this.x = x;
      this.y = y;
      return this;
    }

    /**
     * Uses default height = 20
     *
     * @param width The width of the slider
     */
    public Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public Builder withBounds(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    public Builder withValueRange(double minValue, double maxValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      return this;
    }

    public Builder withInitialValue(double initial) {
      this.initial = initial;
      return this;
    }

    /**
     * @param defaultValue The value that the slider will return to if right-clicked.
     */
    public Builder withDefaultValue(int defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public Builder withTooltip(Supplier<BasicSlider> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public HudOffsetSlider build() {
      HudOffsetSlider slider = new HudOffsetSlider(this.x, this.y, this.width, this.height, this.prefix, this.initial,
                                                   this.minValue, this.maxValue, this.defaultValue,
                                                   this.tooltipSupplier);
      return slider;
    }
  }
}

