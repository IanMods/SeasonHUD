package club.iananderson.seasonhud.forge.impl.minimaps;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import xaero.common.minimap.info.InfoDisplay;
import xaero.common.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.common.minimap.info.widget.InfoDisplayCommonWidgetFactories;

public class XaeroInfoDisplays {
  public static final InfoDisplay<Boolean> SEASON;
  private static final List<InfoDisplay<?>> ALL = new ArrayList<>();

  static {
    Minecraft mc = Minecraft.getInstance();

    SEASON = new InfoDisplay<>("season", new TranslatableComponent("xaerominimap.seasonhud.infodisplay.season"), true,
                               InfoDisplayCommonStateCodecs.BOOLEAN, InfoDisplayCommonWidgetFactories.OFF_ON,
                               (displayInfo, compiler, session, processor, x, y, w, h, scale, size, playerBlockX, playerBlockY, playerBlockZ, playerPos) -> {
                                 if (CurrentSeason.getInstance(mc).getSeasonHudText().toString().isEmpty()) {
                                   return;
                                 }

                                 if (displayInfo.getState() && CurrentMinimap.xaeroLoaded()
                                     && CurrentMinimap.shouldDrawMinimapHud(Minimap.XAERO)) {
                                   compiler.addLine(CurrentSeason.getInstance(mc).getSeasonHudText());
                                 }
                               }, ALL);
  }
}