package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.MapAtlasesCommon;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import lilypuree.mapatlases.MapAtlasesMod;
import lilypuree.mapatlases.util.MapAtlasesAccessUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class MapAtlases implements IIngameOverlay {
  private static final Minecraft mc = Minecraft.getInstance();
  public static MapAtlases HUD_INSTANCE;
  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (mc.player != null && mc.player.level.dimension() == Level.OVERWORLD) {
      Inventory inv = mc.player.getInventory();
      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(inv);
      boolean mapAtlasesConfig = MapAtlasesMod.CONFIG.drawMiniMapHUD.get();

      if (!mapAtlasesConfig) {
        return false;
      } else {
        return !mc.options.renderDebug && atlas.getCount() > 0;
      }
    } else {
      return false;
    }
  }

  @Override
  public void render(ForgeIngameGui gui, PoseStack graphics, float partialTick, int screenWidth, int screenHeight) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      if (mc.level != null && mc.player != null) {
        int mapBgScaledSize = 64;

        mapBgScaledSize = MapAtlasesMod.CONFIG.forceMiniMapScaling.get();

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

          MapAtlasesCommon.drawMapComponentSeasonOld(graphics, x - 3, y, mapBgScaledSize, textHeightOffset, scale);
        }
      }
    }
  }
}