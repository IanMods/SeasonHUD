package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlayCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;

public class SeasonHUDOverlay extends GuiComponent {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public SeasonHUDOverlay() {
  }

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
  }

  public void render(PoseStack graphics) {
    SeasonHUDOverlayCommon.render(graphics);
  }
}
