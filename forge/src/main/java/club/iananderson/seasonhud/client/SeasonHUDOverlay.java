package club.iananderson.seasonhud.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SeasonHUDOverlay implements IGuiOverlay {

  public SeasonHUDOverlay() {
  }

  public void render(ForgeGui gui, GuiGraphics seasonStack, float partialTick, int screenWidth, int screenHeight) {
    SeasonHUDOverlayCommon.render(seasonStack);
  }
}
