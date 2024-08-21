package club.iananderson.seasonhud.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.forge.client.overlays.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.config.SeasonsConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ForgeMinimapHelper implements IMinimapHelper {

    @Override
    public boolean hideHudInCurrentDimension() {
        ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

        return !SeasonsConfig.isDimensionWhitelisted(currentDim);
    }

    @Override
    public boolean hiddenMinimap(Minimaps minimap) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null || mc.player == null) {
            return false;
        }

        switch (minimap) {
            case JOURNEYMAP:
                MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();
                return !properties.enabled.get() || (!properties.isActive() && mc.isPaused());

            case FTB_CHUNKS:
                return !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.options.renderDebug;

            case XAERO:
                return !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug;

            case XAERO_FAIRPLAY:
                return !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug;

            case MAP_ATLASES:
                return !MapAtlases.shouldDraw(mc);

            default:
                return false;
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