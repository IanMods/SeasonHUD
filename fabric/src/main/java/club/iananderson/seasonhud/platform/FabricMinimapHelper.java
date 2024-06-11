package club.iananderson.seasonhud.platform;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.Objects;
import journeymap.client.ui.UIManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;
import xaero.common.HudMod;

public class FabricMinimapHelper implements IMinimapHelper {

  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(mc.level).dimension();

    return !ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean currentMinimapHidden() {
    if (CurrentMinimap.minimapLoaded("journeymap")) {
      return !UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties().enabled.get();
    }
    if (CurrentMinimap.minimapLoaded("ftbchunks") && !CurrentMinimap.minimapLoaded("journeymap")
        && !CurrentMinimap.minimapLoaded("xaerominimap") && !CurrentMinimap.minimapLoaded("xaerominimapfair")
        && !CurrentMinimap.minimapLoaded("map_atlases")) {
      return !FTBChunksClientConfig.MINIMAP_ENABLED.get();
    }
    if (CurrentMinimap.minimapLoaded("xaerominimap") || CurrentMinimap.minimapLoaded("xaerominimapfair")) {
      return !HudMod.INSTANCE.getSettings().getMinimap();
    }
//		if (CurrentMinimap.minimapLoaded("map_atlases")) {
//			return !MapAtlases.shouldDraw(Minecraft.getInstance());
//		}
    else {
      return false;
    }
  }
}