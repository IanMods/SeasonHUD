package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;

public class MapAtlasesCommon {
  private static void drawSeasonWithLighterShadow(GuiGraphics context, Font font, MutableComponent text,
      MutableComponent shadowText, int x, int y) {
    context.drawString(font, shadowText, x + 1, y + 1, 5855577, false);
    context.drawString(font, text, x, y, 0xffffff, false);
  }

  private static void drawScaledComponent(GuiGraphics context, Font font, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int maxWidth, int targetWidth) {
    PoseStack pose = context.pose();
    float textWidth = (float) font.width(text);
    float scale = Math.min(1.0F, (float) maxWidth * textScaling / textWidth);
    scale *= textScaling;
    float centerX = (float) x + (float) targetWidth / 2.0F;
    pose.pushPose();
    pose.translate(centerX, (float) (y + 4), 5.0F);
    pose.scale(scale, scale, 1.0F);
    pose.translate(-textWidth / 2.0F, -4.0F, 0.0F);
    drawSeasonWithLighterShadow(context, font, text, shadowText, 0, 0);
    pose.popPose();
  }

  public static void drawMapComponentSeason(GuiGraphics poseStack, Font font, int x, int y, int targetWidth,
      float textScaling, float globalScale) {
    if (CurrentMinimap.minimapLoaded(Minimaps.MAP_ATLASES)) {
      MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudText();
      MutableComponent shadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudTextNoFormat();
      drawScaledComponent(poseStack, font, x, y, seasonCombined, shadowText, textScaling / globalScale, targetWidth,
                          (int) (targetWidth / globalScale));
    }
  }
}
