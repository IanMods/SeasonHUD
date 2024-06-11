package club.iananderson.seasonhud.impl.minimaps;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xaero.common.minimap.info.InfoDisplay;
import xaero.common.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.common.minimap.info.widget.InfoDisplayCommonWidgetFactories;

public class XaeroInfoDisplays {
  public static final InfoDisplay<Boolean> SEASON;
  private static final List<InfoDisplay<?>> ALL = new ArrayList<>();

  static {
    SEASON = new InfoDisplay<>("season", Component.translatable("menu.seasonhud.infodisplay.season"), true,
        InfoDisplayCommonStateCodecs.BOOLEAN, InfoDisplayCommonWidgetFactories.OFF_ON,
        (displayInfo, compiler, session, processor, x, y, w, h, scale, size, playerBlockX, playerBlockY, playerBlockZ, playerPos) -> {
          if (getSeasonHudName().isEmpty()) {
            return;
          }

          MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
              getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE), getSeasonHudName().get(1).copy());

          if ((Boolean) displayInfo.getState() && CurrentMinimap.shouldDrawMinimapHud()) {
            compiler.addLine(seasonCombined);
          }
        }, ALL);
  }
}