package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.forge.client.overlays.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import com.mamiyaotaru.voxelmap.MapSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelMap;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.config.SeasonsConfig;
import xaero.common.HudMod;

public class ForgeMinimapHelper implements IMinimapHelper {

  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    return !SeasonsConfig.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean hiddenMinimap(Minimaps minimap) {
    Minecraft mc = Minecraft.getInstance();
    boolean hidden = false;

    if (mc.level == null || mc.player == null) {
      return false;
    }

    switch (minimap) {
      case JOURNEYMAP:
        MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();
        return !properties.enabled.get() || (!properties.isActive() && mc.isPaused());

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
        hidden = !MapAtlases.shouldDraw(mc) || !Calendar.calendarFound();
        break;

      case VOXELMAP:
        MapSettingsManager voxelOptions = VoxelMap.getInstance().getMapOptions();
        hidden = voxelOptions.hide || (!voxelOptions.showUnderMenus && (mc.screen != null || mc.options.renderDebug))
            || !Calendar.calendarFound();
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