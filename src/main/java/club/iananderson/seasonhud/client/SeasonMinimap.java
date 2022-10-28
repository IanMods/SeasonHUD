//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.minimap.render.MinimapRenderer;

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
        int scaledX = mc.getWindow().getGuiScaledWidth();
        int scaledY = mc.getWindow().getGuiScaledHeight();

        boolean minimapShape = modMain.getSettings().getMinimap(); //Need to factor in?
        float interfaceSize = (float) mc.getWindow().getGuiScale();
        float minimapScale = modMain.getSettings().getMinimapScale();
        float mapScale = interfaceSize / minimapScale;

        int minimapSize = modMain.getSettings().getMinimapSize();
        int AutoUIScale = modMain.getSettings().getAutoUIScale();
        //int text size = (height of text)*(how many lines)
        //Change math by minimap orientation. Look for variable


        int x = (int) ((float) scaledX * (minimapScale / mapScale));
        int y = (int) ((float) scaledY * (minimapScale / mapScale));

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

        int stringWidth = mc.font.width(seasonName);
        int size = (int) ((float) (Math.min(y, x)) / minimapScale);

        int stringX = (int) ((scaledX - stringWidth - (Math.sqrt((double)(minimapSize * minimapSize)/2)))/(minimapScale/mapScale)); //might need to center on x

        int stringY = (int) (((Math.sqrt((double)(minimapSize * minimapSize)/2))+(trueCount *15))/(minimapScale/mapScale)); //Size looks to be diagonal with x + y being equal.
        //Needs to go down a bit

        final float scale = 1.0F;//(minimapScale/mapScale);

        seasonStack.pushPose();
        seasonStack.scale(scale,scale,scale);

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
