package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.SeasonHUD;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Objects;

public class SeasonHUDOverlay {

    public static Season SeasonVal(){
        Minecraft mc = Minecraft.getInstance();
        return SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
    }

    public static final IGuiOverlay HUD_SEASON = (ForgeGui, poseStack, partialTick,screenWidth, screenHeight) -> {
        int x = 0;
        int y = 0;
        int iconDim = 10;
        int offsetDim = 5;

        ResourceLocation SEASON = new ResourceLocation(SeasonHUD.MODID,
            "textures/season/"+ Objects.requireNonNull(SeasonVal()).name().toLowerCase()+".png");

        Minecraft mc = Minecraft.getInstance();

        Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
        String SEASONNAME = currentSeason.name();
        String seasonLower = SEASONNAME.toLowerCase();
        String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

        //String currentSeason = "Summer";

        //Text
        ForgeGui.getFont().draw(poseStack,seasonCap, (float) (x+18), (float) (y+offsetDim+(.12*iconDim)),0xffffffff);

        //Icon
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(poseStack,x+offsetDim, y+offsetDim,0,0,iconDim,iconDim,iconDim,iconDim);
    };
}
