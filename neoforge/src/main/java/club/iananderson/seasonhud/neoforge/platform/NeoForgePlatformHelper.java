package club.iananderson.seasonhud.neoforge.platform;

import club.iananderson.seasonhud.platform.services.IPlatformHelper;
import java.util.Optional;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {
    return "NeoForge";
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
