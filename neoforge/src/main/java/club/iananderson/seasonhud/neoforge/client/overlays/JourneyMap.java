package club.iananderson.seasonhud.neoforge.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import journeymap.client.render.draw.DrawUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import org.jetbrains.annotations.NotNull;

public class JourneyMap implements LayeredDraw.Layer {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker tickCounter) {
    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(Minimaps.JOURNEYMAP)) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      graphics.pose().pushPose();
      graphics.pose().scale(1 / journeyMapCommon.getInfoSlotScale(), 1 / journeyMapCommon.getInfoSlotScale(), 0);
      DrawUtil.sizeDisplay(journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      graphics.pose().popPose();
      journeyMapCommon.drawSeasonLabel(graphics);
    }
  }
}