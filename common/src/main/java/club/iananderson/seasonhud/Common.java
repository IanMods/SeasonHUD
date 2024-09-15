package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import java.util.List;
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
  public static final ResourceLocation SEASON_ICONS = location("season_icons");
  public static final Style SEASON_ICON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
  public static ResourceLocation slotIcon = new ResourceLocation(MOD_ID, "slot/calendarslot");
  private static boolean curiosLoaded;
  private static boolean accessoriesLoaded;
  private static boolean extrasLoaded;
  private static String platformName;

  private Common() {
  }

  public static void init() {
    platformName = Services.PLATFORM.getPlatformName();
    extrasLoaded =
        Services.PLATFORM.isModLoaded("seasonsextras") || Services.PLATFORM.getPlatformName().equals("Forge");
    curiosLoaded = Services.PLATFORM.isModLoaded("trinkets") || Services.PLATFORM.isModLoaded("curios");
    accessoriesLoaded = Services.PLATFORM.isModLoaded("accessories");
  }

  public static String platformName() {
    return Common.platformName;
  }

  public static boolean extrasLoaded() {
    return Common.extrasLoaded;
  }

  public static boolean curiosLoaded() {
    return Common.curiosLoaded;
  }

  public static boolean accessoriesLoaded() {
    return Common.accessoriesLoaded;
  }

  public static boolean vanillaShouldDrawHud() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
        && !mc.options.renderDebug && !mc.options.hideGui && !mc.player.isScoping();
  }

  public static boolean drawDefaultHud() {
    return (Config.getEnableMod() && (CurrentMinimap.noMinimapLoaded() || !Config.getEnableMinimapIntegration() || (
        CurrentMinimap.allMinimapsHidden() && Config.getShowDefaultWhenMinimapHidden())));
  }

  public static boolean allTrue(List<Boolean> values) {
    for (boolean value : values) {
      if (!value) {
        return false;
      }
    }
    return true;
  }

  public static ResourceLocation location(String path) {
    return new ResourceLocation(MOD_ID, path);
  }
}