package club.iananderson.seasonhud.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.overlays.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;
import xaero.common.HudMod;

public class ForgeMinimapHelper implements IMinimapHelper {
  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    return !ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean hiddenMinimap(Minimaps minimap) {
    switch (minimap) {
      case JOURNEYMAP -> {
        MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

        return !properties.enabled.get() || !(properties.isActive());
      }
      case FTB_CHUNKS -> {
        return !FTBChunksClientConfig.MINIMAP_ENABLED.get();
      }
      case XAERO, XAERO_FAIRPLAY -> {
        return !HudMod.INSTANCE.getSettings().getMinimap();
      }
      case MAP_ATLASES -> {
        return !MapAtlases.shouldDraw(Minecraft.getInstance());
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