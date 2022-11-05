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
        float mapScale = ((float)(scale / (double)minimapScale));
        float fontScale = 1/mapScale;

        int bufferSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapBufferSize(mapSize);
        float sizeFix = (float)bufferSize / 512.0F;

        int minimapFrameSize  = (int)((float)(mapSize / 2) / sizeFix);
        int halfFrame = (int)((float)mapSize * minimapScale / 2.0F / 2.0F);


        int bottomCornerStartY =(Math.min(minimapFrameSize, halfFrame)) + (int)(3.0F * minimapScale);
        int padding = bottomCornerStartY-(Math.min(minimapFrameSize, halfFrame));


        int size = (int)((float)(Math.min(height, width)) / mapScale);


        int x = width;//-(frameSize+6+(mapSize/2));
        int y = height;//(2*frameSize)+18+mapSize;

        int scaledX = (int)(x*mapScale);
        int scaledY = (int)(y*mapScale);



        boolean xBiome = modMain.getSettings().showBiome;
        boolean xDim = modMain.getSettings().showDimensionName;
        boolean xCoords = modMain.getSettings().getShowCoords();
        boolean xAngles = modMain.getSettings().showAngles;
        int xLight = modMain.getSettings().showLightLevel;
        int xTime = modMain.getSettings().showTime;

        int trueCount = 0;

        if (xBiome) {
            trueCount++;
        }
        if (xDim) {
            trueCount++;
        }
        if (xCoords) {
            trueCount++;
        }
        if (xAngles) {
            trueCount++;
        }
        if (xLight > 0) {
            trueCount++;
        }
        if (xTime > 0) {
            trueCount++;
        }

        int i = trueCount;


        //Icon
        int align = XaeroMinimapCore.currentSession.getModMain().getSettings().minimapTextAlign;
        int stringWidth = Math.round(mc.font.width(seasonName)*fontScale);
        int stringHeight = Math.round((mc.font.lineHeight+1));

        int iconDim = stringHeight+1;
        int offsetDim = iconDim+1;//maybe change to 2

        int stringY = bottomCornerStartY+(i*stringHeight);
        int stringX = scaledX + (align == 0 ? -Math.min(minimapFrameSize, halfFrame)/2 - stringWidth/2 +offsetDim/2
                : (align == 1 ? -Math.min(minimapFrameSize, halfFrame)+offsetDim: -stringWidth-(padding/2)));




        String[] debug = new String[5];
        debug[0] = "stringHeight: " + stringHeight + " | " + "align: " + align + " | " + "minimapFrameSize: " + minimapFrameSize + " | " + "halfFrame: " + halfFrame + " | " + "bottomCornerStartY: " + bottomCornerStartY;;
        debug[2] = "y: " + y + " | " + "scaledY: " + scaledY + " | " + "stringY: " + stringY;
        debug[1] = "x: " + x + " | " + "scaledX: " + scaledX + " | " + "stringX: " + stringX;
        debug[3] = "scale: " + scale + " | " + "minimapScale: " + minimapScale + " | " + "mapScale: " + mapScale
                + " | " + "fontScale: " + fontScale;
        debug[4] =  "bufferSize: " + bufferSize + " | " + "sizeFix: " + sizeFix + " | " + "MinimapSize: " + mapSize;

        if (enableDebugHUD()) {
            seasonStack.pushPose();
            seasonStack.scale(fontScale,fontScale,fontScale);
            ForgeGui.getFont().drawShadow(seasonStack, debug[0], offset, offset, 0xffffffff);
            ForgeGui.getFont().drawShadow(seasonStack, debug[1], offset, (offset * 2), 0xffffffff);
            ForgeGui.getFont().drawShadow(seasonStack, debug[2], offset, (offset * 3), 0xffffffff);
            ForgeGui.getFont().drawShadow(seasonStack, debug[3], offset, (offset * 4), 0xffffffff);
            ForgeGui.getFont().drawShadow(seasonStack, debug[4], offset, (offset * 5), 0xffffffff);

            seasonStack.popPose();
        }
    };
}


