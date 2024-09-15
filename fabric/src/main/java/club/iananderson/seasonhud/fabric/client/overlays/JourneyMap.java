package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import com.mojang.blaze3d.vertex.PoseStack;
import journeymap.client.render.draw.DrawUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class JourneyMap implements HudRenderCallback {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(PoseStack graphics, float alpha) {
    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(Minimap.JOURNEYMAP)) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      graphics.pushPose();
      graphics.scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(graphics, journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      graphics.popPose();
      journeyMapCommon.drawSeasonLabel(graphics);
    }
  }
}
