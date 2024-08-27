package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.MapAtlasesCommon;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import pepjebs.dicemc.util.MapAtlasesAccessUtils;

public class MapAtlases extends GuiComponent {
  private static final Minecraft mc = Minecraft.getInstance();
  public static MapAtlases HUD_INSTANCE;
  protected final int BG_SIZE = 64;

  public static void init() {
    HUD_INSTANCE = new MapAtlases();
  }

  public static boolean shouldDraw(Minecraft mc) {
    if (mc.player != null && mc.player.level.dimension() == Level.OVERWORLD) {
      Inventory inv = mc.player.inventory;
      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(inv);
      BooleanValue mapAtlasesConfig = pepjebs.dicemc.config.Config.DRAW_MINIMAP_HUD;

      if (!mapAtlasesConfig.get()) {
        return false;
      } else {
        return !mc.options.renderDebug && atlas.getCount() > 0;
      }
    } else {
      return false;
    }
  }

  public void render(PoseStack graphics) {
    if (CurrentMinimap.mapAtlasesLoaded() && !Services.MINIMAP.hiddenMinimap(Minimaps.MAP_ATLASES)) {
      if (mc.level != null && mc.player != null) {
        int mapBgScaledSize = 64;

        mapBgScaledSize = pepjebs.dicemc.config.Config.FORCE_MINIMAP_SCALING.get();

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