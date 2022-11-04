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
import xaero.common.settings.ModOptions;
import xaero.common.settings.ModSettings;

import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public class SeasonMinimap {

    public static final IGuiOverlay XAERO_SEASON = (ForgeGui, seasonStack, partialTick, width, height) -> {
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



        seasonStack.pushPose();
        seasonStack.scale(fontScale,fontScale,fontScale);


        //Font
        mc.font.draw(seasonStack,seasonName,(float) stringX, (float) stringY,0xffffffff);
        mc.font.drawShadow(seasonStack,seasonName, (float) stringX, (float) stringY,0xffffffff);


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(seasonStack, stringX-offsetDim, stringY,0,0,iconDim,iconDim,iconDim,iconDim);
        seasonStack.popPose();
    };
}
