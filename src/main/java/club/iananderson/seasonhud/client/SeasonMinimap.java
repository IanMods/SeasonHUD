package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.settings.ModOptions;
import xaero.common.settings.ModSettings;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.openjdk.nashorn.internal.objects.Global.println;
import static xaero.common.settings.ModOptions.WAYPOINTS_NAME_SCALE;
import static xaero.common.settings.ModOptions.modMain;


public class SeasonMinimap {
        public static Season SeasonVal(){
            Minecraft mc = Minecraft.getInstance();
            return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        }

        public static final IGuiOverlay XAERO_SEASON = (ForgeGui, poseStack, partialTick, screenWidth, screenHeight) -> {
            Minecraft mc = Minecraft.getInstance();

            //Current Season Name
            Season currentSeason = SeasonVal();
            String SEASONNAME = currentSeason.name();
            String seasonLower = SEASONNAME.toLowerCase();
            String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

            //Icon chooser
            ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                    "textures/season/"+ Objects.requireNonNull(SeasonVal()).name().toLowerCase()+".png");


            //Text
            int interfaceSize = (int)Minecraft.getInstance().getWindow().getGuiScale();
            int scaledX = screenWidth;
            int scaledY = screenHeight;

            float minimapScale = modMain.getSettings().getMinimapScale();
            float mapScale = interfaceSize/minimapScale;
            int minimapSize = modMain.getSettings().getMinimapSize();
            //int text size = (height of text)*(how many lines)
            //Change math by minimap orientation. Look for variable

            int x = (int)((float)scaledX*(minimapScale/mapScale));
            int y = (int)((float)scaledY*(minimapScale/mapScale));


            int stringWidth = mc.font.width(seasonCap);
            int size = (int)((float)(Math.min(y, x)) / minimapScale);

            int stringX = (int)(scaledX - stringWidth - (Math.sqrt((double)(minimapSize*minimapSize)/2)/mapScale)); //might need to center on x
            int stringY = (int)((Math.sqrt((double)(minimapSize*minimapSize)/2))/mapScale); //Size looks to be diagonal with x + y being equal.
            //Needs to go down a bit


            //Debug
            String[] debug = new String[4];
            debug[0] = "MinimapSize: "+ minimapSize +" | "+"size: "+ size +" | "+ "minimapScale: "+minimapScale;
            debug[2] = "screenHeight: "+y +" | "+ "scaledY: "+scaledY + " | " + "stringY: "+stringY;
            debug[1] = "screenWidth: "+x +" | "+ "scaledX: "+scaledX + " | " + "stringX: "+stringX;
            debug[3] = "minimapScale: "+minimapScale/mapScale +" | "+ "interfaceSize: "+interfaceSize + " | " + "mapScale: "+mapScale;


            ForgeGui.getFont().draw(poseStack,debug[0],(float) stringX, (float) stringY,0xffffffff);
            ForgeGui.getFont().draw(poseStack,debug[1],(float) 15, (float) stringY+15,0xffffffff);
            ForgeGui.getFont().draw(poseStack,debug[2],(float) 15, (float) stringY+15*2,0xffffffff);
            ForgeGui.getFont().draw(poseStack,debug[3],(float) 15, (float) stringY+15*3,0xffffffff);

            //Font
            //ForgeGui.getFont().draw(poseStack,seasonCap,(float) stringX, (float) stringY,0xffffffff);

            //Icon
            int iconDim = 10;
            int offsetDim = -iconDim;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
            RenderSystem.setShaderTexture(0,SEASON);
            GuiComponent.blit(poseStack,stringX+offsetDim, stringY,0,0,iconDim,iconDim,iconDim,iconDim);
        };
}
