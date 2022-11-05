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



        seasonStack.pushPose();
        seasonStack.translate((double)(-9), (double)(9), 0.0);
        seasonStack.scale(fontScale,fontScale,1.0F);


        //Font
        mc.font.drawShadow(seasonStack,seasonName, (float) stringX, (float) stringY,0xffffffff);


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(seasonStack, stringX-offsetDim, stringY-offsetDim+iconDim,0,0,iconDim,iconDim,iconDim,iconDim);
        seasonStack.popPose();
    };
}
