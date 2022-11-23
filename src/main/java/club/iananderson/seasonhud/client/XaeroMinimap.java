//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import club.iananderson.seasonhud.config.SeasonHUDClientConfigs;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.ftb.mods.ftblibrary.snbt.config.StringValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.gui.IScreenBase;
import xaero.common.interfaces.Interface;
import xaero.common.interfaces.InterfaceManager;

import java.util.ArrayList;

import static club.iananderson.seasonhud.CurrentSeason.*;
import static xaero.common.settings.ModOptions.modMain;

/* Todo
    * Need to switch names to translatable ones
    * Clean up code and improve the accuracy of the formulas
 */

public class XaeroMinimap {
    public static boolean minimapLoaded(){
        return ModList.get().isLoaded("xaerominimap");
    }

    //LanguageProvider
    public static final IGuiOverlay XAERO_SEASON = (ForgeGui, seasonStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        int hudPosition = SeasonHUDClientConfigs.hudPosition.get();

        ArrayList<Component> underText = new ArrayList<>();
        underText.add(Component.translatable(getSeasonName()));


        if (minimapLoaded()) {
            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                    "textures/season/" + getSeasonLower() + ".png");


            //Data
            int mapSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapSize();//Minimap Size

            double scale = mc.getWindow().getGuiScale();

            float minimapScale = XaeroMinimapCore.currentSession.getModMain().getSettings().getMinimapScale();
            float mapScale = ((float) (scale / (double) minimapScale));
            float fontScale = 1 / mapScale;

            int bufferSize = XaeroMinimapCore.currentSession.getMinimapProcessor().getMinimapBufferSize(mapSize);
            float sizeFix = (float) bufferSize / 512.0F;

            int minimapFrameSize = (int) ((float) (mapSize / 2) / sizeFix);
            int halfFrame = (int) ((float) mapSize * minimapScale / 2.0F / 2.0F);


            int bottomCornerStartY = (Math.min(minimapFrameSize, halfFrame)) + (int) (3.0F * minimapScale);
            int padding = bottomCornerStartY - Math.min(minimapFrameSize, halfFrame);


            float scaledMapSize = mapSize/mapScale;


            float x = XaeroMinimapCore.currentSession.getModMain().getInterfaces().getMinimapInterface().getX();
            float y = XaeroMinimapCore.currentSession.getModMain().getInterfaces().getMinimapInterface().getY();

            float scaledX = (x * mapScale);
            float scaledY = (y * mapScale);


            boolean xBiome = modMain.getSettings().showBiome;
            boolean xDim = modMain.getSettings().showDimensionName;
            boolean xCoords = modMain.getSettings().getShowCoords();
            boolean xAngles = modMain.getSettings().showAngles;
            int xLight = modMain.getSettings().showLightLevel;
            int xTime = modMain.getSettings().showTime;

            int trueCount = 0;

            if (xBiome) {trueCount++;}
            if (xDim) {trueCount++;}
            if (xCoords) {trueCount++;}
            if (xAngles) {trueCount++;}
            if (xLight > 0) {trueCount++;}
            if (xTime > 0) {trueCount++;}


            //Icon
            int align = XaeroMinimapCore.currentSession.getModMain().getSettings().minimapTextAlign;
            int stringWidth = Math.round(mc.font.width(String.valueOf(getSeasonName()))* fontScale);
            int stringHeight = (int) Math.round((mc.font.lineHeight)+1);
            int scaledHeight = (int) ((fontScale) + (stringHeight));


            int iconDim = stringHeight;
            int offsetDim = (int)1;//maybe change to 2

            int totalWidth = stringWidth + iconDim + offsetDim;

            int center = (int)(halfFrame/2) + totalWidth/2;

            //int stringY = bottomCornerStartY + (trueCount * scaledHeight);
           // int stringX = scaledX - (align == 0 ? center : (align == 1 ? Math.min(minimapFrameSize, halfFrame) - iconDim - offsetDim : stringWidth + iconDim/2-offsetDim));

            float stringX = scaledX+(align == 0 ? center : (align == 1 ? Math.min(minimapFrameSize, halfFrame) - iconDim - offsetDim : stringWidth + iconDim/2-offsetDim));
            float stringY = scaledY+halfFrame+(trueCount * scaledHeight);

            if ((!modMain.getSettings().hideMinimapUnderScreen || mc.screen == null || mc.screen instanceof IScreenBase || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
            && (!modMain.getSettings().hideMinimapUnderF3 || !mc.options.renderDebug)) {
                seasonStack.pushPose();

                seasonStack.scale(fontScale, fontScale, 1.0F);


                //Font
                for (Component s : underText) {
                    mc.font.drawShadow (seasonStack,s, stringX, stringY, -1);
                }

                underText.clear();

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, SEASON);
                GuiComponent.blit(seasonStack, (int)stringX-iconDim-offsetDim, (int)stringY-offsetDim, 0, 0, iconDim, iconDim, iconDim, iconDim);
                seasonStack.popPose();
            }
        }

    };
}


