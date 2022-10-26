package club.iananderson.seasonhud.client;

import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static xaero.common.core.XaeroMinimapCore.modMain;

public class DebugHUD {

    public static boolean enableDebugHUD() {
        return modMain.getSettings().displayCurrentClaim;
    }

    //Debug
    public static final IGuiOverlay DEBUG_HUD = (ForgeGui, poseStack, partialTick, screenWidth, screenHeight) -> {

        String[] debug = new String[4];
        debug[0] = "MinimapSize: " + SeasonMapSettings.minimapSize + " | " + "size: " + SeasonMapSettings.size + " | " + "minimapScale: " + SeasonMapSettings.minimapScale;
        debug[2] = "screenHeight: " + SeasonMapSettings.y + " | " + "scaledY: " + SeasonMapSettings.scaledY + " | " + "stringY: " + SeasonMapSettings.stringY;
        debug[1] = "screenWidth: " + SeasonMapSettings.x + " | " + "scaledX: " + SeasonMapSettings.scaledX + " | " + "stringX: " + SeasonMapSettings.stringX;
        debug[3] = "minimapScale: " + SeasonMapSettings.minimapScale / SeasonMapSettings.mapScale + " | " + "interfaceSize: " + SeasonMapSettings.interfaceSize + " | " + "mapScale: " + SeasonMapSettings.mapScale;

        int offset = 15;

        if (enableDebugHUD()==true) {
            ForgeGui.getFont().draw(poseStack, debug[0], offset, offset, 0xffffffff);
            ForgeGui.getFont().draw(poseStack, debug[1], offset, (offset * 2), 0xffffffff);
            ForgeGui.getFont().draw(poseStack, debug[2], offset, (offset * 3), 0xffffffff);
            ForgeGui.getFont().draw(poseStack, debug[3], offset, (offset * 4), 0xffffffff);
        }
    };
}


