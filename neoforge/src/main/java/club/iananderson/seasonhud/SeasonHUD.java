package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.Config;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Common.MOD_ID)
public class SeasonHUD {

  private static final Logger LOGGER = LogUtils.getLogger();

  public SeasonHUD(IEventBus modEventBus, ModContainer modContainer) {
    modEventBus.addListener(this::commonSetup);

    Common.init();

    modContainer.registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC, "SeasonHUD-client.toml");
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
//        CuriosApi.registerCurio(Calendar.calendar,new CuriosCalendar());
  }
}