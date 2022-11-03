//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.opengl.GLX11;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.XaeroMinimapSession;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.minimap.element.render.map.MinimapElementMapRendererHandler;
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

        int mapSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapSize();//Minimap Size

        int bufferSize = XaeroMinimapCore.currentSession.getMinimapProcessor()
                .getMinimapBufferSize(mapSize);

        //float scale = XaeroMinimapCore.currentSession.getModMain().getSettings().getAutoUIScale();
        double scale = mc.getWindow().getGuiScale();

        float sizeFix = (float)bufferSize / 512.0F;
        //float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getAutoUIScale();
        float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getMinimapScale();
        float mapScale = ((float)(scale / (double)minimapScale));

        int height = Minecraft.getInstance().getWindow().getHeight();
        int scaledHeight = (int)(height/minimapScale);
        int width = Minecraft.getInstance().getWindow().getWidth();
        int scaledWidth = (int)(width/minimapScale);
        int size = (int)((float)(height <= width ? height : width) / mapScale);


        //int x = screenWidth;
        //int y = screenHeight;
        int x = (int)((Math.sqrt((double)(mapSize*mapSize)/2))); //might need to center on x
        int y = (int)((Math.sqrt((double)(mapSize*mapSize)/2))); //Size looks to be diagonal with x + y being equal.
        //Needs to go down a bit?

        int scaledX = (int)((float)x / minimapScale);
        //int scaledX = mc.getWindow().getGuiScaledWidth();
        int scaledY = (int)((float)y / minimapScale);
        //int scaledY = mc.getWindow().getGuiScaledHeight();

        int interfaceSize = (int)(18*minimapScale);

        int centerX = (2 * scaledX + 18 + mapSize / 2);
        int centerY = (2 * scaledY + 18 + mapSize / 2);


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
        int iconDim = 10;
        int offsetDim = iconDim+2;


        int align = XaeroMinimapCore.currentSession.getModMain().getSettings().minimapTextAlign;
        int stringWidth = mc.font.width(seasonName);
        boolean under = scaledY + interfaceSize / 2 < scaledHeight / 2;

        //int stringY = scaledY + (under ? interfaceSize : -9) + i * 10 * (under ? 1 : -1); // use this for above map test
        int stringY = 25+scaledY+(interfaceSize)+(int)(i*(ForgeGui.getFont().lineHeight)/mapScale);
        int stringX = scaledWidth - scaledX - iconDim
                - (int)(align == 0 ? interfaceSize - stringWidth/mapScale - offsetDim
                        : (align == 1 ? stringWidth/mapScale + offsetDim : -stringWidth/mapScale - offsetDim));//need to fix for all sizes

        float fontScale = 1/mapScale;

        seasonStack.pushPose();
        seasonStack.scale(fontScale,fontScale,fontScale);


        //Font
        ForgeGui.getFont().draw(seasonStack,seasonName,(float) stringX+iconDim+2, (float) (stringY+(.12*iconDim)),0xffffffff);


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(seasonStack, stringX, stringY,0,0,iconDim,iconDim,iconDim,iconDim);
        seasonStack.popPose();
    };
}
