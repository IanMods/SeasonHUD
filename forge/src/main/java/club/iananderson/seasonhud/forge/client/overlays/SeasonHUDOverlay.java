package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlayCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class SeasonHUDOverlay extends Gui {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public SeasonHUDOverlay(Minecraft mc) {
    super(mc);
  }

  public static void init(Minecraft mc) {
    HUD_INSTANCE = new SeasonHUDOverlay(mc);
  }

  public void render(PoseStack graphics, float partialTick) {
    SeasonHUDOverlayCommon.render(graphics);
  }
}
