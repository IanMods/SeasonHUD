package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.settings.ModSettings;

import java.util.Objects;

import static xaero.common.settings.ModOptions.WAYPOINTS_NAME_SCALE;
import static xaero.common.settings.ModOptions.modMain;

public class SeasonMinimap {
        public static Season SeasonVal(){
            Minecraft mc = Minecraft.getInstance();
            return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        }
        public static final IGuiOverlay XAERO_SEASON = (ForgeGui, poseStack, partialTick, screenWidth, screenHeight) -> {
            int x = screenWidth;
            int y = screenHeight/2;
            int iconDim = 10;
            int offsetDim = -5;

            ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                    "textures/season/"+ Objects.requireNonNull(SeasonVal()).name().toLowerCase()+".png");

            Minecraft mc = Minecraft.getInstance();

            Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
            String SEASONNAME = currentSeason.name();
            String seasonLower = SEASONNAME.toLowerCase();
            String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

            //Text
            float minimapScale = modMain.getSettings().getMinimapScale();
            float mapScale = (modMain.getSettings().getWaypointsIngameNameScale()/minimapScale);

            int scaledX = (int)((float)x * mapScale);
            int scaledY = (int)((float)y * mapScale);

            int interfaceSize = (int)Minecraft.getInstance().getWindow().getGuiScale();
            int stringWidth = mc.font.width(seasonCap);

            int stringY = scaledY+40;
            int stringX = scaledX + (interfaceSize / 2) - (stringWidth / 2);
            ForgeGui.getFont().draw(poseStack,seasonCap, (float) stringX, (float) stringY,0xffffffff);

            //Icon
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
            RenderSystem.setShaderTexture(0,SEASON);
            GuiComponent.blit(poseStack,stringX+offsetDim, stringY+offsetDim,0,0,iconDim,iconDim,iconDim,iconDim);
        };
}
