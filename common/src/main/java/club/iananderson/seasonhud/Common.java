package club.iananderson.seasonhud;

import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;

import club.iananderson.seasonhud.platform.Services;
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

  public static final ResourceLocation SEASON_ICONS = ResourceLocation.fromNamespaceAndPath(MOD_ID, "season_icons");
  public static final Style SEASON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
  private static boolean curiosLoaded;
  private static boolean extrasLoaded;
  private static String platformName;

  public static ResourceLocation location(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }

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
    return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen) && !mc.isPaused()
        && !mc.getDebugOverlay().showDebugScreen();
  }
}