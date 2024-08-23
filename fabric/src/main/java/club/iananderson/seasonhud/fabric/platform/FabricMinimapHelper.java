package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.fabric.client.overlays.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import com.mamiyaotaru.voxelmap.MapSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelMap;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import xaero.common.HudMod;

public class FabricMinimapHelper implements IMinimapHelper {

  @Override
  public boolean hideHudInCurrentDimension() {
    //ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    //return !FabricSeasons.CONFIG.isValidInDimension(currentDim);
    return false;
  }

  @Override
  public boolean hiddenMinimap(Minimaps minimap) {
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
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug;
        break;

      case XAERO_FAIRPLAY:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug;
        break;

      case MAP_ATLASES:
        hidden = !MapAtlases.shouldDraw(mc);
        break;

      case VOXELMAP:
        MapSettingsManager voxelOptions = VoxelMap.getInstance().getMapOptions();
        hidden = voxelOptions.hide || (!voxelOptions.showUnderMenus && (mc.screen != null || mc.options.renderDebug));
        break;
    }
    return hidden;
  }

  @Override
  public boolean allMinimapsHidden() {
    List<Minimaps> loadedMinimaps = CurrentMinimap.getLoadedMinimaps();
    List<Boolean> hiddenMinimaps = new ArrayList<>();

    loadedMinimaps.forEach(minimap -> hiddenMinimaps.add(Services.MINIMAP.hiddenMinimap(minimap)));

    return Common.allTrue(hiddenMinimaps);
  }
}
