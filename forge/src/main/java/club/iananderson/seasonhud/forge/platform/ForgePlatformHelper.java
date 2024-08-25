package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.IPlatformHelper;
import java.util.Optional;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {
    return "Forge";
  }

  @Override
  public boolean isModLoaded(String modId) {
    return ModList.get().isLoaded(modId);
  }

  @Override
  public String getModVersion(String modId) {
    Optional<? extends ModContainer> mod = ModList.get().getModContainerById(modId);

    if (mod.isPresent()) {
      return mod.get().getModInfo().getVersion().toString();
    } else {
      return "Not Loaded";
    }
  }

  @Override
  public boolean isDevelopmentEnvironment() {
    return !FMLLoader.isProduction();
  }
}
