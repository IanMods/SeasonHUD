package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class MapAtlases implements IIngameOverlay {
  public static MapAtlases HUD_INSTANCE;
  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
  }

  public static boolean shouldDraw(Minecraft mc) {
    return false;
  }

  @Override
  public void render(ForgeIngameGui gui, PoseStack graphics, float partialTick, int screenWidth, int screenHeight) {
    Minecraft mc = Minecraft.getInstance();

    if (CurrentMinimap.mapAtlasesLoaded() && shouldDraw(mc)) {
    }
  }
}