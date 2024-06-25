package club.iananderson.seasonhud;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import journeymap.client.JourneymapClient;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.dialog.OptionsManager;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common {

  public static final String MOD_ID = "seasonhud";
  public static final String MOD_NAME = "SeasonHUD";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

  public static final ResourceLocation SEASON_ICONS = new ResourceLocation(MOD_ID, "season_icons");
  public static final ResourceLocation SEASON_ICONS_OUTLINE = new ResourceLocation(MOD_ID, "season_icons_outline");
  public static final Style SEASON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
  public static final Style SEASON_OUTLINE_STYLE = Style.EMPTY.withFont(SEASON_ICONS_OUTLINE);

  private static boolean curiosLoaded;
  private static boolean extrasLoaded;

  private static String platformName;

  public static void init() {
    curiosLoaded = Services.PLATFORM.isModLoaded("trinkets") || Services.PLATFORM.isModLoaded("curios");
    extrasLoaded = Services.PLATFORM.isModLoaded("seasonsextras");
    platformName = Services.PLATFORM.getPlatformName();
  }

  public static boolean extrasLoaded() {
    return Common.extrasLoaded;
  }

  public static boolean curiosLoaded() {
    return Common.curiosLoaded;
  }

  public static String platformName() {
    return Common.platformName;
  }

  public static boolean vanillaShouldDrawHud() {
    if (CurrentMinimap.minimapLoaded("journeymap")) {
      MiniMapProperties currentMinimap = JourneymapClient.getInstance().getActiveMiniMapProperties();
      return currentMinimap.isActive() && (!mc.isPaused());
    } else
      return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
          && !mc.isPaused()
        && !mc.options.renderDebug;
  }
}