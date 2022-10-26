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

import java.util.Objects;

import static club.iananderson.seasonhud.client.SeasonMapSettings.seasonCap;

//Hud w/ Xaero's Minimap installed
public class SeasonMinimap {
    public static Season SeasonVal(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        }



    public static final IGuiOverlay XAERO_SEASON = (ForgeGui, poseStack, partialTick, screenWidth, screenHeight) -> {
        //Icon chooser
        ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
                "textures/season/"+SeasonVal().name().toLowerCase()+".png");

        //Current Season Name
        Season currentSeason = SeasonVal();
        String SEASONNAME = currentSeason.name();
        String seasonLower = SEASONNAME.toLowerCase();
        String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

        //Font
        ForgeGui.getFont().draw(poseStack,seasonCap,(float) SeasonMapSettings.stringX.getValueof(), (float) SeasonMapSettings.stringY,0xffffffff);

        //Icon
        int iconDim = 10;
        int offsetDim = -iconDim;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(poseStack,SeasonMapSettings.stringX+offsetDim, SeasonMapSettings.stringY,0,0,iconDim,iconDim,iconDim,iconDim);
    };
}
