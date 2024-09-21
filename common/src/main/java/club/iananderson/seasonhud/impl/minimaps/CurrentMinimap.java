package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public class CurrentMinimap {
  private static boolean minimapLoaded(Minimap minimap) {
    String modID = minimap.getModID();
    return Services.PLATFORM.isModLoaded(modID);
  }

  public static boolean xaeroLoaded() {
    return minimapLoaded(Minimap.XAERO) || minimapLoaded(Minimap.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return minimapLoaded(Minimap.JOURNEYMAP);
  }

  public static boolean ftbChunksLoaded() {
    return minimapLoaded(Minimap.FTB_CHUNKS);
  }

  public static boolean mapAtlasesLoaded() {
    return minimapLoaded(Minimap.MAP_ATLASES);
  }

  private static List<Minimap> getLoadedMinimaps() {
    List<Minimap> values = Arrays.asList(Minimap.values());
    List<Minimap> loaded = new ArrayList<>();

    values.forEach(minimaps -> {
      if (minimapLoaded(minimaps)) {
        loaded.add(minimaps);
      }
    });
    return loaded;
  }

  public static boolean noMinimapLoaded() {
    return getLoadedMinimaps().isEmpty();
  }

  /* Todo:
   ** Double check all logic
   ** Add option to display current loaded integration
   ** Add a dropdown to override this if more than one are loaded
   */

  /**
   * Used incase FtbChunks or Journeymap are loaded, but not used for the minimap.
   *
   * @return True if all the loaded minimaps are hidden.
   */
  public static boolean allMinimapsHidden() {
    List<Minimap> loadedMinimaps = CurrentMinimap.getLoadedMinimaps();
    List<Boolean> hiddenMinimaps = new ArrayList<>();

    loadedMinimaps.forEach(minimap -> hiddenMinimaps.add(Services.MINIMAP.hiddenMinimap(minimap)));

    return Common.allTrue(hiddenMinimaps);
  }

  /**
   * @param minimap Current loaded minimap mod.
   * @return True if the minimap version of the HUD should be drawn instead of the default.
   */
  public static boolean shouldDrawMinimapHud(Minimap minimap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    boolean enabled = Config.getEnableMod() && Config.getEnableMinimapIntegration();
    boolean hiddenMinimap = Services.MINIMAP.hideHudInCurrentDimension() || Services.MINIMAP.hiddenMinimap(minimap);

    return enabled && Calendar.validNeedCalendar() && !mc.options.hideGui && !hiddenMinimap;
  }

  public enum Minimap {
    XAERO("xaerominimap"),

    XAERO_FAIRPLAY("xaerominimapfair"),

    JOURNEYMAP("journeymap"),

    FTB_CHUNKS("ftbchunks"),

    MAP_ATLASES("map_atlases"),

    MAP_ATLASES_FORGE("mapatlases"),

    VOXELMAP("voxelmap");

    private final String modID;

    Minimap(String modID) {
      this.modID = modID;
    }

    public String getModID() {
      return this.modID;
    }
  }
}