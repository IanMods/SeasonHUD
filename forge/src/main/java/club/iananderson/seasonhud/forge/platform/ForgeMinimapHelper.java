package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.forge.client.overlays.MapAtlases;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import com.mamiyaotaru.voxelmap.MapSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelMap;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lilypuree.mapatlases.MapAtlasesMod;
import lilypuree.mapatlases.util.MapAtlasesAccessUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sereneseasons.config.SeasonsConfig;

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
    boolean hidden = false;

      if (mc.level == null || mc.player == null) {
        return true;
      }

      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.getInventory());

      boolean drawMinimapHud = MapAtlasesMod.CONFIG.drawMiniMapHUD.get();
      ;
      boolean hasAtlas = atlas.getCount() > 0;

      return !drawMinimapHud || !hasAtlas;
    } else {
      return false;
    }
  }
}
