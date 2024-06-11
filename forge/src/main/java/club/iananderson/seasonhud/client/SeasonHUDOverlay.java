package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class SeasonHUDOverlay implements IIngameOverlay {

  public SeasonHUDOverlay() {
  }

  public void render(ForgeIngameGui gui, PoseStack seasonStack, float partialTick, int width, int height) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}
