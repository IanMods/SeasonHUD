package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import com.mojang.blaze3d.vertex.PoseStack;
import journeymap.client.render.draw.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class JourneyMap implements IIngameOverlay {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  @Override
  public void render(ForgeIngameGui gui, PoseStack graphics, float partialTick, int scaledWidth, int scaledHeight) {
    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(Minimap.JOURNEYMAP)) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      graphics.pushPose();
      graphics.scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(graphics, journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      graphics.popPose();
      journeyMapCommon.drawSeasonLabel(graphics);
    }
  }
}