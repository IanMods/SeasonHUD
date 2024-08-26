package club.iananderson.seasonhud.neoforge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlayCommon;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class SeasonHUDOverlay implements LayeredDraw.Layer {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
  }

  public void render(GuiGraphics seasonStack, DeltaTracker deltaTracker) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}