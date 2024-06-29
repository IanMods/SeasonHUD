package club.iananderson.seasonhud.platform;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.client.minimaps.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.Objects;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.config.ServerConfig;
import xaero.common.HudMod;

public class ForgeMinimapHelper implements IMinimapHelper {

  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(mc.level).dimension();

    return !ServerConfig.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean currentMinimapHidden() {
    if (CurrentMinimap.minimapLoaded(Minimaps.JOURNEYMAP)) {
      return !UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties().enabled.get();
    }
    if (CurrentMinimap.minimapLoaded(Minimaps.FTB_CHUNKS) && !CurrentMinimap.minimapLoaded(Minimaps.JOURNEYMAP)
        && !CurrentMinimap.minimapLoaded(Minimaps.XAERO) && !CurrentMinimap.minimapLoaded(Minimaps.XAERO_FAIRPLAY)
        && !CurrentMinimap.minimapLoaded(Minimaps.MAP_ATLASES)) {
      return !FTBChunksClientConfig.MINIMAP_ENABLED.get();
    }
    if (CurrentMinimap.minimapLoaded(Minimaps.XAERO) || CurrentMinimap.minimapLoaded(Minimaps.XAERO_FAIRPLAY)) {
      return !HudMod.INSTANCE.getSettings().getMinimap();
    }
    if (CurrentMinimap.minimapLoaded(Minimaps.MAP_ATLASES)) {
      return !MapAtlases.shouldDraw(Minecraft.getInstance());
    } else {
      return false;
    }
  }
}
