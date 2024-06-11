package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SeasonHUDOverlay implements HudRenderCallback {

  public static SeasonHUDOverlay HUD_INSTANCE;

  public SeasonHUDOverlay() {
  }

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(PoseStack seasonStack, float alpha) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}