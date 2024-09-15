package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import com.mamiyaotaru.voxelmap.MapSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelMap;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import io.github.lucaargolo.seasons.FabricSeasons;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;
import xaero.common.HudMod;

public class FabricMinimapHelper implements IMinimapHelper {

  @Override
  public boolean hideHudInCurrentDimension() {
    return false;
  }

  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null || mc.player.level.dimension() != Level.OVERWORLD) {
        return true;
      }

      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.inventory);

      boolean drawMinimapHud = MapAtlasesMod.CONFIG.drawMiniMapHUD;

      boolean hasAtlas = atlas.getCount() > 0;

      return !drawMinimapHud || !hasAtlas;
    } else {
      return false;
    }
  }

  @Override
  public boolean hiddenMinimap(Minimap minimap) {
    Minecraft mc = Minecraft.getInstance();
    boolean hidden = false;

    if (mc.level == null || mc.player == null) {
      return false;
    }

    switch (minimap) {
      case FTB_CHUNKS:
        hidden = !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.options.renderDebug;
        break;

      case XAERO:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
            || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen);
        break;

      case XAERO_FAIRPLAY:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
            || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen);
        break;

      case MAP_ATLASES:
        hidden = Services.MINIMAP.hideMapAtlases();
        break;

      case VOXELMAP:
        MapSettingsManager voxelOptions = VoxelMap.getInstance().getMapOptions();
        hidden = voxelOptions.hide || (!voxelOptions.showUnderMenus && (mc.screen != null || mc.options.renderDebug))
            || !Calendar.calendarFound();
        break;
    }
    return hidden;
  }
}