package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.impl.minimaps.JourneyMapCommon;
import journeymap.client.render.draw.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class JourneyMap implements IGuiOverlay {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  @Override
  public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int scaledWidth, int scaledHeight) {
    if (CurrentMinimap.minimapLoaded(Minimaps.JOURNEYMAP) && CurrentMinimap.shouldDrawMinimapHud()) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      guiGraphics.pose().pushPose();
      guiGraphics.pose().scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(guiGraphics.pose(), journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      guiGraphics.pose().popPose();
      journeyMapCommon.drawSeasonLabel(guiGraphics);
    }
  }
}