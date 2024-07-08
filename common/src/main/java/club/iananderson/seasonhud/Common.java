package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import java.util.List;
import journeymap.client.JourneymapClient;
import net.minecraft.client.Minecraft;
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
  public static final Style SEASON_ICON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
  private static boolean curiosLoaded;
  private static String platformName;

  private Common() {
  }

  public static ResourceLocation location(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }

  public static void init() {
    curiosLoaded = Services.PLATFORM.isModLoaded("trinkets") || Services.PLATFORM.isModLoaded("curios");
    platformName = Services.PLATFORM.getPlatformName();
  }

  public static boolean curiosLoaded() {
    return Common.curiosLoaded;
  }

  public static String platformName() {
    return Common.platformName;
  }

  public static boolean vanillaShouldDrawHud() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    if (CurrentMinimap.journeyMapLoaded()) {
      return JourneymapClient.getInstance().getActiveMiniMapProperties().isActive();
    } else {
      return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
          && !mc.isPaused() && !mc.getDebugOverlay().showDebugScreen() && !mc.options.hideGui && !mc.player.isScoping();
    }
  }

  public static boolean drawDefaultHud() {
    return (Config.getEnableMod() && (CurrentMinimap.noMinimapLoaded() || !Config.getEnableMinimapIntegration() || (
        Services.MINIMAP.allMinimapsHidden() && Config.getShowDefaultWhenMinimapHidden())));
  }

  public static boolean allTrue(List<Boolean> values) {
    for (boolean value : values) {
      if (!value) {
        return false;
      }
    }
    return true;
  }
}