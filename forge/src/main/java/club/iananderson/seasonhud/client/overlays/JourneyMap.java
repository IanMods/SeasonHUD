package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import journeymap.client.render.draw.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class JourneyMap implements LayeredDraw.Layer {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  @Override
  public void render(GuiGraphics graphics, float partialTick) {
    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(Minimaps.JOURNEYMAP)) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      graphics.pose().pushPose();
      graphics.pose().scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(graphics.pose(), journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      graphics.pose().popPose();
      journeyMapCommon.drawSeasonLabel(graphics);
    }
  }
}