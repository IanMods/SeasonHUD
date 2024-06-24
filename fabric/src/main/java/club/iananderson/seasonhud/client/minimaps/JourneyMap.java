package club.iananderson.seasonhud.client.minimaps;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.JourneyMapCommon;
import journeymap.client.render.draw.DrawUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class JourneyMap implements HudRenderCallback {

  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(GuiGraphics guiGraphics, float alpha) {
    if (CurrentMinimap.minimapLoaded("journeymap") && CurrentMinimap.shouldDrawMinimapHud()) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      guiGraphics.pose().pushPose();
      guiGraphics.pose().scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(guiGraphics.pose(), journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      guiGraphics.pose().popPose();
      journeyMapCommon.drawSeasonLabel(guiGraphics);
    }
  }
}
