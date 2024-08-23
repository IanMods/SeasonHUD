package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.MapAtlasesCommon;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;

public class MapAtlases implements HudRenderCallback {
  private static final Minecraft mc = Minecraft.getInstance();
  public static MapAtlases HUD_INSTANCE;
  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (mc.player != null && mc.player.level.dimension() == Level.OVERWORLD) {
      Inventory inv = mc.player.inventory;
      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(inv);
      if (MapAtlasesMod.CONFIG != null && !MapAtlasesMod.CONFIG.drawMiniMapHUD) {
        return false;
      } else {
        return !mc.options.renderDebug && atlas.getCount() > 0;
      }
    } else {
      return false;
    }
  }

  @Override
  public void onHudRender(PoseStack graphics, float alpha) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      if (mc.level != null && mc.player != null) {
        int mapBgScaledSize = 64;
        if (MapAtlasesMod.CONFIG != null) {
          mapBgScaledSize = MapAtlasesMod.CONFIG.forceMiniMapScaling;
        }

        int y = 0;
        if (!mc.player.getActiveEffects().isEmpty()) {
          y = 26;
        }
        int x = mc.getWindow().getGuiScaledWidth() - mapBgScaledSize;

        x += mapBgScaledSize / 16 - mapBgScaledSize / 64;
        y += mapBgScaledSize / 16 - mapBgScaledSize / 64;

        if (Config.getEnableMod() && shouldDraw(mc)) {
          float scale = mapBgScaledSize / 142.0F;
          int textHeightOffset = mapBgScaledSize - 2;

          MapAtlasesCommon.drawMapComponentSeasonOld(graphics, x, y, mapBgScaledSize, textHeightOffset, scale);
        }
      }
    }
  }
}