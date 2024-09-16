package club.iananderson.seasonhud.neoforge.platform;

import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

public class NeoForgeMinimapHelper implements IMinimapHelper {
  @Override
  public boolean hideHudInCurrentDimension() {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    return !ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}