package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.forge.client.overlays.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
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
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pepjebs.dicemc.util.MapAtlasesAccessUtils;
import sereneseasons.config.SeasonsConfig;
import xaero.common.HudMod;

public class ForgeMinimapHelper implements IMinimapHelper {

  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    return !SeasonsConfig.isDimensionWhitelisted(currentDim);
  }

  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null) {
        return true;
      }

      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.inventory);

      boolean drawMinimapHud = pepjebs.dicemc.config.Config.DRAW_MINIMAP_HUD.get();

      boolean hasAtlas = atlas.getCount() > 0;

      return !drawMinimapHud || !hasAtlas;
    } else {
      return false;
    }
  }

  @Override
  public boolean hiddenMinimap(Minimap minimap) {
    Minecraft mc = Minecraft.getInstance();
    boolean hidden = false;

    if (mc.level == null || mc.player == null) {
      return false;
    }

    switch (minimap) {
      case JOURNEYMAP:
        MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

        hidden = !properties.enabled.get() || (!properties.isActive() && mc.isPaused()) || !(mc.screen == null
            || mc.screen instanceof ChatScreen);
        break;

      case FTB_CHUNKS:
        hidden = !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.options.renderDebug;
        break;

      case XAERO:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
            || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen);
        break;

      case XAERO_FAIRPLAY:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
            || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen);
        break;

      case MAP_ATLASES:
        hidden = Services.MINIMAP.hideMapAtlases();
        break;

      case VOXELMAP:
        MapSettingsManager voxelOptions = VoxelMap.getInstance().getMapOptions();
        hidden = voxelOptions.hide || (!voxelOptions.showUnderMenus && (mc.screen != null || mc.options.renderDebug));
        break;
    }
    return hidden;
  }
}
