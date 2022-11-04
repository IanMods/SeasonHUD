package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.config.SeasonHUDClientConfigs;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.XaeroMinimapSession;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.settings.ModOptions;

import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public class DebugHUD {

    public static boolean enableDebugHUD() {
        return SeasonHUDClientConfigs.debugHUD.get();
    }

    //Debug
    public static final IGuiOverlay DEBUG_HUD = (ForgeGui, seasonStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        int offset = 20;

        //Season
        Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        String seasonCap = currentSeason.name();
        String seasonLower = seasonCap.toLowerCase();
        String seasonName = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

        //Data
        int mapSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapSize();//Minimap Size

        double scale = mc.getWindow().getGuiScale();

        float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getMinimapScale();
        float mapScale = ((float)(scale / minimapScale));
        float fontScale = 1/mapScale;

        int scaledHeight = (int)(height*mapScale);

        int scaledWidth = (int)((width)*mapScale);

        //int bufferSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapBufferSize(mapSize);
        ///float sizeFix = (float)bufferSize / 512.0F;
        int frameSize = 8;

        int size = (int)((float)(Math.min(height, width)) / mapScale);


        int x = -(frameSize+6+(mapSize/2));
        int y = (2*frameSize)+18+mapSize;

        int scaledX = (int)(x*fontScale);
        int scaledY = (int)(y*fontScale);



        boolean xBiome = modMain.getSettings().showBiome;
        boolean xDim = modMain.getSettings().showDimensionName;
        boolean xCoords = modMain.getSettings().getShowCoords();
        boolean xAngles = modMain.getSettings().showAngles;

        int trueCount=0;

        if (xBiome == true) {
            trueCount++;
        }
        if (xDim == true) {
            trueCount++;
        }
        if (xCoords == true) {
            trueCount++;
        }
        if (xAngles == true) {
            trueCount++;
        }

        int i = trueCount;


        //Icon
        int iconDim = Math.round(12*fontScale);
        int offsetDim = Math.round(iconDim+(3*fontScale));//maybe change to 2

        int align = XaeroMinimapCore.currentSession.getModMain().getSettings().minimapTextAlign;
        int stringWidth = Math.round(mc.font.width(seasonName)*fontScale);
        int stringHeight = Math.round(mc.font.lineHeight*fontScale);

        int stringY = scaledY+(i*stringHeight);
        int stringX = scaledWidth + scaledX - (int)(align == 0 ? - stringWidth/2 : (align == 1 ? stringWidth/2 + offsetDim : -stringWidth/2 - offsetDim));//need to fix for all sizes





        String[] debug = new String[5];
        debug[0] = "MinimapSize: " + mapSize + " | " + "stringHeight: " + stringHeight + " | " + "align: " + align + " | " + "frameSize: " + frameSize + " | " + "i: " + i;
        debug[2] = "y: " + y + " | " + "scaledY: " + scaledY + " | " + "stringY: " + stringY + " | " + "Scaled Height: " + scaledHeight;
        debug[1] = "x: " + x + " | " + "scaledX: " + scaledX + " | " + "stringX: " + stringX + " | " + "Scaled Width: " + scaledWidth;
        debug[3] = "scale: " + scale + " | " + "minimapScale: " + minimapScale + " | " + "mapScale: " + mapScale
                + " | " + "fontScale: " + fontScale;

        if (enableDebugHUD()) {
            seasonStack.pushPose();
            ForgeGui.getFont().draw(seasonStack, debug[0], offset, offset, 0xffffffff);
            ForgeGui.getFont().draw(seasonStack, debug[1], offset, (offset * 2), 0xffffffff);
            ForgeGui.getFont().draw(seasonStack, debug[2], offset, (offset * 3), 0xffffffff);
            ForgeGui.getFont().draw(seasonStack, debug[3], offset, (offset * 4), 0xffffffff);
            seasonStack.popPose();
        }
    };
}


