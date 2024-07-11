package club.iananderson.seasonhud.client.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class SeasonHUDOverlay implements IIngameOverlay {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
  }

  public void render(ForgeIngameGui gui, PoseStack graphics, float partialTick, int width, int height) {
    SeasonHUDOverlayCommon.render(graphics);
  }
}
