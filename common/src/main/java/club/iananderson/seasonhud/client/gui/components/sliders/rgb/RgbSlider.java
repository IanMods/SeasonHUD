package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class RgbSlider extends BasicSlider {
    public static final int SLIDER_PADDING = 2;
    protected final Seasons season;
    private final boolean enableColor = Config.getEnableSeasonNameColor();
    protected ColorEditBox seasonBox;
    protected int r;
    protected int g;
    protected int b;
    protected int rgb;
    protected ChatFormatting textColor;

    public RgbSlider(int x, int y, ColorEditBox seasonBox) {
        super(x, y, seasonBox.getWidth() + 2, seasonBox.getHeight() - 6, true, Integer.parseInt(seasonBox.getValue()));
        this.minValue = 0;
        this.maxValue = 16777215;
        this.seasonBox = seasonBox;
        this.season = seasonBox.getSeason();
        this.rgb = Integer.parseInt(seasonBox.getValue());
        this.r = Rgb.rgbColor(rgb).getRed();
        this.g = Rgb.rgbColor(rgb).getGreen();
        this.b = Rgb.rgbColor(rgb).getBlue();
        this.value = snapToNearest(rgb);
        this.textColor = ChatFormatting.WHITE;
        this.updateMessage();
    }

    @Override
    public void onClick(double x, double y) {
        if (enableColor) {
            super.onClick(x, y);
        }
    }

    @Override
    protected void onDrag(double d, double e, double f, double g) {
        if (enableColor) {
            super.onDrag(d, e, f, g);
        }
    }

    @Override
    public void renderBg(@NotNull PoseStack graphics, Minecraft mc, int mouseX, int mouseY) {
        if (!enableColor) {
            this.active = false;
            this.isHovered = false;
        }

        super.renderBg(graphics, mc, mouseX, mouseY);
    }

    @Override
    protected void updateMessage() {
        Component colorString = new TextComponent(this.getValueString());

        if (this.drawString) {
            this.setMessage(colorString.copy().withStyle(textColor));

            if (!enableColor) {
                this.setMessage(colorString.copy().withStyle(ChatFormatting.GRAY));
            }
        } else {
            this.setMessage(new TextComponent(""));
        }
    }
}