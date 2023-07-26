package club.iananderson.seasonhud.impl.minimaps;

import net.minecraft.network.chat.Component;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.minimap.info.BuiltInInfoDisplays;
import xaero.common.minimap.info.InfoDisplay;
import xaero.common.minimap.info.InfoDisplayManager;
import xaero.common.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.common.minimap.info.widget.InfoDisplayCommonWidgetFactories;
import xaero.minimap.XaeroMinimap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonName;


public class XaeroInfoDisplays extends BuiltInInfoDisplays {
    public static final InfoDisplay<Boolean> SEASON;

    public static InfoDisplayManager manager = XaeroMinimapCore.modMain.getInterfaces().getMinimapInterface().getInfoDisplayManager();

    private static List<InfoDisplay<?>> ALL = XaeroMinimapCore.modMain.getInterfaces().getMinimapInterface().getInfoDisplayManager().get()

    static{
        SEASON = new InfoDisplay("season", Component.translatable("menu.seasonhud.infodisplay.season"), false, InfoDisplayCommonStateCodecs.BOOLEAN, InfoDisplayCommonWidgetFactories.OFF_ON, (displayInfo, compiler, session, processor, x, y, w, h, scale, size, playerBlockX, playerBlockY, playerBlockZ, playerPos) -> {
            if ((Boolean)displayInfo.getState()) {
                String seasonName = String.valueOf(getSeasonName());
                compiler.addLine(seasonName);
            }
        },ALL);
    }
}