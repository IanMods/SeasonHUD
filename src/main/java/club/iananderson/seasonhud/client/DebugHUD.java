package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.config.SeasonHUDClientConfigs;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.settings.ModOptions;

import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public class DebugHUD {

    public static boolean enableDebugHUD() {
        return SeasonHUDClientConfigs.debugHUD.get();
    }

    //Debug
    public static final IGuiOverlay DEBUG_HUD = (ForgeGui, seasonStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        int offset = 20;

        //Season
        Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        String seasonCap = currentSeason.name();
        String seasonLower = seasonCap.toLowerCase();
        String seasonName = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

        //Data
        int shape = modMain.getSettings().minimapShape;

        int mapSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapSize(); //minimap size

        int bufferSize = XaeroMinimapCore.currentSession.getMinimapProcessor()
                .getMinimapBufferSize(mapSize);

        //float scale = XaeroMinimapCore.currentSession.getModMain().getSettings().getAutoUIScale();
        float scale = modMain.getSettings().getWaypointsIngameNameScale();;

        float sizeFix = (float)bufferSize / 512.0F;
        //float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getAutoUIScale();
        float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getMinimapScale(); //minimap scale
        float mapScale = (float)(scale / (double)minimapScale);

        int height = Minecraft.getInstance().getWindow().getHeight();
        int scaledHeight = (int)((float)height * mapScale);
        int width = Minecraft.getInstance().getWindow().getWidth();
        int size = (int)((float)(height <= width ? height : width) / minimapScale);


        //int interfaceSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapSize() / 2 + 18;



        //int x = screenWidth;
        //int y = screenHeight;
        int x = (int)((Math.sqrt((double)(mapSize*mapSize)/2))); //might need to center on x
        int y = (int)((Math.sqrt((double)(mapSize*mapSize)/2))); //Size looks to be diagonal with x + y being equal.
        //Needs to go down a bit?


        int scaledX = (int)((float)x * mapScale);
        //int scaledX = mc.getWindow().getGuiScaledWidth();
        int scaledY = (int)((float)y * mapScale);
        //int scaledY = mc.getWindow().getGuiScaledHeight();

        int interfaceSize = scaledY + (int)(18*mapScale);

        double centerX = (double)(2 * scaledX + 18 + mapSize / 2);
        double centerY = (double)(2 * scaledY + 18 + mapSize / 2);

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


        int align = XaeroMinimapCore.currentSession.getModMain().getSettings().minimapTextAlign;
        int stringWidth = mc.font.width(seasonName);
        boolean under = scaledY + interfaceSize / 2 < scaledHeight / 2;

        //int stringY = scaledY + (under ? interfaceSize : -9) + i * 10 * (under ? 1 : -1); //doesnt seem right. interface size is 5
        int stringY = (interfaceSize)+(int)(i*10*mapScale);
        int stringX = mc.getWindow().getGuiScaledWidth() - scaledX + (align == 0 ? interfaceSize / 2 - stringWidth / 2 : (align == 1 ? 6 : interfaceSize - 6 - stringWidth));



        String[] debug = new String[5];
        debug[0] = "MinimapSize: " + mapSize + " | " + "Scaled Height: " + scaledHeight + " | " + "minimapScale: " + minimapScale;
        debug[2] = "y: " + y + " | " + "scaledY: " + scaledY + " | " + "stringY: " + stringY;
        debug[1] = "x: " + x + " | " + "scaledX: " + scaledX + " | " + "stringX: " + stringX;
        debug[3] = "scale: " + scale + " | " + "interfaceSize: " + interfaceSize + " | " + "mapScale: " + mapScale;

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


