package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.fullscreen.Fullscreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;
import xaero.common.HudMod;

public class FabricMinimapHelper implements IMinimapHelper {
  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    return !ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  /* Todo:
   * Change this to be part of common code
   * Double check all logic
   * Add option to display current loaded integration
   * Add a dropdown to override this if more than one are loaded
   */
  @Override
  public boolean hiddenMinimap(Minimaps minimap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    switch (minimap) {
      case JOURNEYMAP -> {
        MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

        return (!properties.enabled.get() && !(Fullscreen.uiState().active && UIManager.INSTANCE.getMiniMap()
                                                                                                .isDrawingInPreviewMode()));
      }
      case FTB_CHUNKS -> {
        return !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.getDebugOverlay().showDebugScreen();
      }
      case XAERO, XAERO_FAIRPLAY -> {
        return !HudMod.INSTANCE.getSettings().getMinimap() || mc.getDebugOverlay().showDebugScreen();
      }
      case MAP_ATLASES -> {
        return !Config.getEnableMinimapIntegration() || !Config.getEnableMod() || !Calendar.calendarFound()
            || Services.MINIMAP.hideHudInCurrentDimension();
      }
      default -> {
        return false;
      }
    }
  }

  @Override
  public boolean allMinimapsHidden() {
    List<Minimaps> loadedMinimaps = CurrentMinimap.getLoadedMinimaps();
    List<Boolean> hiddenMinimaps = new ArrayList<>();

    loadedMinimaps.forEach(minimap -> hiddenMinimaps.add(Services.MINIMAP.hiddenMinimap(minimap)));

    return Common.allTrue(hiddenMinimaps);
  }
}