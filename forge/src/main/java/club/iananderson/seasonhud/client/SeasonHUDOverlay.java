package club.iananderson.seasonhud.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class SeasonHUDOverlay implements LayeredDraw.Layer {

  public SeasonHUDOverlay() {
  }

  @Override
  public void render(GuiGraphics seasonStack, DeltaTracker deltaTracker) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}
