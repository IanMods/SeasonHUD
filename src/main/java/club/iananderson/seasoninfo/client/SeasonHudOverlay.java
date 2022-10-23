package club.iananderson.seasoninfo.client;

import club.iananderson.seasoninfo.Seasoninfo;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import javax.annotation.Nullable;

public class SeasonHudOverlay {
    @Nullable
    public static Season SeasonVal(){
        ClientLevel MCLEVEL = Minecraft.getInstance().level;
        Season seasonValue = SeasonHelper.getSeasonState(MCLEVEL).getSeason();
        return seasonValue;
    }

    public static final IGuiOverlay HUD_SEASON = (gui, poseStack, partialTick,screenWidth, screenHeight) -> {
        int x = 0;
        int y = 0;
        int iconDim = 16;
        int offsetDim = 5;

        ResourceLocation SEASON = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/"+SeasonVal().name().toLowerCase()+".png");

        ClientLevel MCLEVEL = Minecraft.getInstance().level;

        Season currentSeason = SeasonHelper.getSeasonState(MCLEVEL).getSeason();
        String SEASONNAME = currentSeason.name();
        String seasonLower = SEASONNAME.toLowerCase();
        String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

        //String currentSeason = "Summer";
        gui.getFont().draw(poseStack,seasonCap, (float) (x+25), (float) (y+offsetDim+(.25*iconDim)),0xffffffff);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SEASON);
        GuiComponent.blit(poseStack,x+offsetDim, y+offsetDim,0,0,iconDim,iconDim,iconDim,iconDim);
    };
}
