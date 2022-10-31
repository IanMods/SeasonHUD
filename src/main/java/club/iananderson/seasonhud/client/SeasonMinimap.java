//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.XaeroMinimapSession;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.settings.ModSettings;

import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public class SeasonMinimap {

    public static final IGuiOverlay XAERO_SEASON = (ForgeGui, seasonStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();

        //Season
        Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        String seasonCap = currentSeason.name();
        String seasonLower = seasonCap.toLowerCase();
        String seasonName = seasonLower.substring(0, 1).toUpperCase() + seasonLower.substring(1);

        //Icon chooser
        ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                "textures/season/" + seasonLower + ".png");

        //Data
        int shape = modMain.getSettings().minimapShape;

        int mapSize = XaeroMinimapCore.currentSession.getMinimapProcessor()
                .getMinimapSize();

        int bufferSize = XaeroMinimapCore.currentSession.getMinimapProcessor()
                .getMinimapBufferSize(mapSize);

        final float scale = 0.5F;


        float sizeFix = (float)bufferSize / 512.0F;
        float minimapScale = modMain.getSettings().getMinimapScale();
        float mapScale = (float)(scale / (double)minimapScale);

        int height = Minecraft.getInstance().getWindow().getHeight();
        int scaledHeight = (int)((float)height * mapScale);
        int width = Minecraft.getInstance().getWindow().getWidth();
        int size = (int)((float)(height <= width ? height : width) / minimapScale);
        int interfaceSize = size;

        int x =modMain.getInterfaces().getMinimapInterface().getX();
        int y =modMain.getInterfaces().getMinimapInterface().getY();

        int scaledX = (int)((float)x * mapScale); //int scaledX = mc.getWindow().getGuiScaledWidth();
        int scaledY = (int)((float)y * mapScale); //int scaledY = mc.getWindow().getGuiScaledHeight();

        double centerX = (double)(2 * scaledX + 18 + mapSize / 2);
        double centerY = (double)(2 * scaledY + 18 + mapSize / 2);


        int i = 0;
        int align = modMain.getSettings().minimapTextAlign;
        int stringWidth = mc.font.width(seasonName);
        boolean under = scaledY + interfaceSize / 2 < scaledHeight / 2;

        int stringX = scaledY + (under ? interfaceSize : -9) + i * 10 * (under ? 1 : -1);
        int stringY = scaledX + (align == 0 ? interfaceSize / 2 - stringWidth / 2 : (align == 1 ? 6 : interfaceSize - 6 - stringWidth));



//        boolean minimapShape = modMain.getSettings().getMinimap(); //Need to factor in?
//        float interfaceSize = (float) mc.getWindow().getGuiScale();
//        float minimapScale = modMain.getSettings().getMinimapScale();
//        float mapScale = interfaceSize / minimapScale;
//
//        int minimapSize = modMain.getSettings().getMinimapSize();
//        int AutoUIScale = modMain.getSettings().getAutoUIScale();
//        //int text size = (height of text)*(how many lines)
//        //Change math by minimap orientation. Look for variable
//
//
//        int x = (int) ((float) scaledX * (minimapScale / mapScale));
//        int y = (int) ((float) scaledY * (minimapScale / mapScale));

        boolean xBiome = modMain.getSettings().showBiome;
        boolean xDim = modMain.getSettings().showDimensionName;
        boolean xCoords = modMain.getSettings().getShowCoords();
        boolean xAngles = modMain.getSettings().showAngles;

        int trueCount = 0;

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


        seasonStack.pushPose();
        seasonStack.scale(scale/mapScale,scale/mapScale,scale);

        //Icon
        int iconDim = 10;
        int offsetDim = 5;

        //Font
        ForgeGui.getFont().draw(seasonStack,seasonName,(float) stringX+offsetDim+iconDim+2, (float) (stringY+offsetDim+(.12*iconDim)),0xffffffff);


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(seasonStack, stringX+offsetDim, stringY+offsetDim,0,0,iconDim,iconDim,iconDim,iconDim);
        seasonStack.popPose();
    };
}
