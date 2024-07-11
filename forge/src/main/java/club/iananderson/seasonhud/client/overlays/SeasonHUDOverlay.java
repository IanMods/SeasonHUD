package club.iananderson.seasonhud.client.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class SeasonHUDOverlay implements LayeredDraw.Layer {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
  }

  @Override
  public void render(GuiGraphics graphics, float partialTick) {
    SeasonHUDOverlayCommon.render(graphics);
  }
}