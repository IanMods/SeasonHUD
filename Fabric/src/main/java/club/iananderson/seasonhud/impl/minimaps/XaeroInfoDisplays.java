package club.iananderson.seasonhud.impl.minimaps;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import xaero.common.minimap.info.InfoDisplay;
import xaero.common.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.common.minimap.info.widget.InfoDisplayCommonWidgetFactories;

import java.util.ArrayList;
import java.util.List;

import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.hideMinimap;


public class XaeroInfoDisplays {
    private static List<InfoDisplay<?>> ALL = new ArrayList<>();
    public static final InfoDisplay<Boolean> SEASON;

    static{
        SEASON = new InfoDisplay("season", Component.translatable("menu.seasonhud.infodisplay.season"), true, InfoDisplayCommonStateCodecs.BOOLEAN, InfoDisplayCommonWidgetFactories.OFF_ON, (displayInfo, compiler, session, processor, x, y, w, h, scale, size, playerBlockX, playerBlockY, playerBlockZ, playerPos) -> {
            if ((Boolean)displayInfo.getState() && !hideMinimap()) {
                ArrayList<Component> seasonName = getSeasonName();

                for (Component s : seasonName) {
                    compiler.addLine(s);}
            }
        },ALL);
    }
}