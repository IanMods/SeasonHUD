package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;

public class MapAtlasesCommon {
  private MapAtlasesCommon() {
  }

  private static void drawSeasonWithLighterShadow(GuiGraphics graphics, Font font, MutableComponent text,
      MutableComponent shadowText) {
    graphics.drawString(font, shadowText, 1, 1, 5855577, false);
    graphics.drawString(font, text, 0, 0, 0xffffff, false);
  }

  private static void drawScaledComponent(GuiGraphics graphics, Font font, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int maxWidth, int targetWidth) {
    PoseStack pose = graphics.pose();
    float textWidth = font.width(text);
    float scale = Math.min(1.0F, maxWidth * textScaling / textWidth);
    scale *= textScaling;
    float centerX = x + targetWidth / 2.0F;
    pose.pushPose();
    pose.translate(centerX, (y + 4), 5.0F);
    pose.scale(scale, scale, 1.0F);
    pose.translate(-textWidth / 2.0F, -4.0F, 0.0F);
    drawSeasonWithLighterShadow(graphics, font, text, shadowText);
    pose.popPose();
  }

  public static void drawMapComponentSeason(GuiGraphics graphics, Font font, int x, int y, int targetWidth,
      float textScaling, float globalScale) {
    MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudText();
    MutableComponent shadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudTextNoFormat();
    drawScaledComponent(graphics, font, x, y, seasonCombined, shadowText, textScaling / globalScale, targetWidth,
                        (int) (targetWidth / globalScale));
  }
}
