package club.iananderson.seasonhud.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;

public class SeasonHUDOverlay implements HudRenderCallback {

  public static SeasonHUDOverlay HUD_INSTANCE;

  public SeasonHUDOverlay() {
  }

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(GuiGraphics seasonStack, float alpha) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}