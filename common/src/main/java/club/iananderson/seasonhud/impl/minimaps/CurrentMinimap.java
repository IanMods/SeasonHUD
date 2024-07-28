package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.List;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.fullscreen.Fullscreen;
import journeymap.client.ui.minimap.MiniMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

public class CurrentMinimap {
  private static boolean minimapLoaded(Minimaps minimap) {
    String modID = minimap.getModID();
    return Services.PLATFORM.isModLoaded(modID);
  }

  public static List<Minimaps> getLoadedMinimaps() {
    List<Minimaps> values = new ArrayList<>(List.of(Minimaps.values()));
    List<Minimaps> loaded = new ArrayList<>();

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

  public static boolean xaeroLoaded() {
    return getLoadedMinimaps().contains(Minimaps.XAERO) || getLoadedMinimaps().contains(Minimaps.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return getLoadedMinimaps().contains(Minimaps.JOURNEYMAP);
  }

  public static boolean ftbChunksLoaded() {
    return getLoadedMinimaps().contains(Minimaps.FTB_CHUNKS);
  }

  public static boolean mapAtlasesLoaded() {
    return getLoadedMinimaps().contains(Minimaps.MAP_ATLASES);
  }

  /**
   * Checks if the minimap version of the HUD should be drawn instead of the default.
   *
   * @return True if the minimap version of the HUD should be drawn
   */
  public static boolean shouldDrawMinimapHud(Minimaps minimap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    return Config.getEnableMod() && Config.getEnableMinimapIntegration() && Calendar.calendarFound() && validScreen(
        mc.screen) && !mc.options.hideGui && !Services.MINIMAP.hideHudInCurrentDimension()
        && !Services.MINIMAP.hiddenMinimap(minimap);
  }

  public static boolean validScreen(Screen screen) {
    boolean valid = (screen == null || screen instanceof ChatScreen || screen instanceof DeathScreen);

    if (journeyMapLoaded()) {
      return MiniMap.uiState().active;
    } else {
      return valid;
    }
  }

  public enum Minimaps {
    XAERO("xaerominimap"),

    XAERO_FAIRPLAY("xaerominimapfair"),

    JOURNEYMAP("journeymap"),

    FTB_CHUNKS("ftbchunks"),

    MAP_ATLASES("map_atlases");

    private final String modID;

    Minimaps(String modID) {
      this.modID = modID;
    }

    public String getModID() {
      return this.modID;
    }
  }
}
