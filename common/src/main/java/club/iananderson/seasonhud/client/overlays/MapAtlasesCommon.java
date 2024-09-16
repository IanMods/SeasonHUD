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
    MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudComponent();
    MutableComponent shadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudTextNoFormat();
    drawScaledComponent(graphics, font, x, y, seasonCombined, shadowText, textScaling / globalScale, targetWidth,
                        (int) (targetWidth / globalScale));
  }

  public static void drawScaledText(GuiGraphics context, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int originOffsetWidth, int originOffsetHeight) {
    Minecraft mc = Minecraft.getInstance();
    PoseStack poseStack = context.pose();
    float textWidth = (float) mc.font.width(text) * textScaling;
    float textX = (float) ((double) x + (double) originOffsetWidth / 2.0 - (double) textWidth / 2.0);
    float textY = (float) (y + originOffsetHeight);
    if (textX + textWidth >= (float) mc.getWindow().getGuiScaledWidth()) {
      textX = (float) mc.getWindow().getGuiScaledWidth() - textWidth;
    }

    poseStack.pushPose();
    poseStack.translate(textX, textY, 5.0F);
    poseStack.scale(textScaling, textScaling, 1.0F);
    context.drawString(mc.font, shadowText, 1, 1, Integer.parseInt("595959", 16), false);
    context.drawString(mc.font, text, 0, 0, Integer.parseInt("E0E0E0", 16), false);
    poseStack.popPose();
  }

  public static void drawMapComponentSeasonOld(GuiGraphics poseStack, int x, int y, int originOffsetWidth,
      int originOffsetHeight, float textScaling) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudComponent();
      MutableComponent shadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudTextNoFormat();
      drawScaledText(poseStack, x, y, seasonCombined, shadowText, textScaling, originOffsetWidth, originOffsetHeight);
    }
  }
}