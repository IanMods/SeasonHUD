package club.iananderson.seasonhud.platform;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import journeymap.client.ui.UIManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.config.ServerConfig;
import xaero.common.HudMod;

import java.util.Objects;

import static club.iananderson.seasonhud.Common.mc;

public class ForgeMinimapHelper implements IMinimapHelper {
	@Override
	public boolean hideHudInCurrentDimension() {
		ResourceKey<Level> currentDim = Objects.requireNonNull(mc.level).dimension();

		return !ServerConfig.isDimensionWhitelisted(currentDim);
	}

	@Override
	public boolean currentMinimapHidden() {
		if (CurrentMinimap.minimapLoaded("journeymap")) {
			return !UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties().enabled.get();
		}
		if (CurrentMinimap.minimapLoaded("ftbchunks")
				&& !CurrentMinimap.minimapLoaded("journeymap")
				&& !CurrentMinimap.minimapLoaded("xaerominimap")
				&& !CurrentMinimap.minimapLoaded("xaerominimapfair")
				&& !CurrentMinimap.minimapLoaded("map_atlases")) {
			return !FTBChunksClientConfig.MINIMAP_ENABLED.get();
		}
		if (CurrentMinimap.minimapLoaded("xaerominimap") || CurrentMinimap.minimapLoaded("xaerominimapfair")) {
			return !HudMod.INSTANCE.getSettings().getMinimap();
		}
//		if (CurrentMinimap.minimapLoaded("map_atlases")) {
//			return !MapAtlases.shouldDraw(Minecraft.getInstance());
//		}
		else return false;
	}
}
