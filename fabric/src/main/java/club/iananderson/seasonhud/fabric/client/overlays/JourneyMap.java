package club.iananderson.seasonhud.fabric.client.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class JourneyMap implements HudRenderCallback {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(PoseStack graphics, float alpha) {
//    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(Minimaps.JOURNEYMAP)) {
//      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());
//
//      graphics.pushPose();
//      graphics.scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
//      DrawUtil.sizeDisplay(graphics, journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
//      graphics.popPose();
//      journeyMapCommon.drawSeasonLabel(graphics);
//    }
  }
}
