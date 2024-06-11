package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SeasonHUDOverlay implements IGuiOverlay {

  public SeasonHUDOverlay() {
  }

  public void render(ForgeGui gui, PoseStack seasonStack, float partialTick, int screenWidth, int screenHeight) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}
