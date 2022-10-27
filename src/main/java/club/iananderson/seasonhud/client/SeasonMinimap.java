//Hud w/ Xaero's Minimap installed
package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import club.iananderson.seasonhud.client.CurrentSeason;

import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public class SeasonMinimap {
    public static final IGuiOverlay XAERO_SEASON = (ForgeGui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();

        //Icon chooser
        ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                "textures/season/"+ Objects.requireNonNull(CurrentSeason.getCurrentSeason().name().toLowerCase())+".png");

        //Data
        int interfaceSize = (int) mc.getWindow().getGuiScale();
        int scaledX = mc.getWindow().getGuiScaledWidth();
        int scaledY = mc.getWindow().getGuiScaledHeight();

        boolean minimapShape = modMain.getSettings().getMinimap(); //Need to factor in?
        float minimapScale = modMain.getSettings().getMinimapScale();
        float mapScale = interfaceSize/minimapScale;

        int minimapSize = modMain.getSettings().getMinimapSize();
        int AutoUIScale = modMain.getSettings().getAutoUIScale();
        //int text size = (height of text)*(how many lines)
        //Change math by minimap orientation. Look for variable

        boolean xBiome = modMain.getSettings().showBiome; // Use for line count x 4
        boolean xDimensionName = modMain.getSettings().showDimensionName;
        boolean xCoords = modMain.getSettings().getShowCoords();
        boolean xAngles = modMain.getSettings().showAngles;

        int x = (int)((float)scaledX*(minimapScale/mapScale));
        int y = (int)((float)scaledY*(minimapScale/mapScale));


        int stringWidth = mc.font.width(CurrentSeason.getSeasonFinalName());
        int size = (int)((float)(Math.min(y, x)) / minimapScale);

        int stringX = (int)(scaledX - stringWidth - (Math.sqrt((double)(minimapSize*minimapSize)/2)/mapScale)); //might need to center on x

        int stringY = (int) ((Math.sqrt((double) (minimapSize * minimapSize) / 3)) / mapScale); //Size looks to be diagonal with x + y being equal.
                //Needs to go down a bit


        //Font
        ForgeGui.getFont().draw(poseStack,CurrentSeason.getSeasonFinalName(),(float) stringX, (float) stringY,0xffffffff);

        //Icon
        int iconDim = 10;
        int offsetDim = -iconDim;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(poseStack, stringX+offsetDim, stringY,0,0,iconDim,iconDim,iconDim,iconDim);
    };
}
