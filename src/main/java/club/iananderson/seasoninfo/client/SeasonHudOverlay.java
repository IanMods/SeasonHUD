package club.iananderson.seasoninfo.client;

import club.iananderson.seasoninfo.Seasoninfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SeasonHudOverlay {

    private static final ResourceLocation SPRING = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/spring.png");
    private static final ResourceLocation SUMMER = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/summer.png");
    private static final ResourceLocation FALL = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/fall.png");
    private static final ResourceLocation WINTER = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/winter.png");

    public static final IGuiOverlay HUD_SEASON = ((gui, poseStack, partialTick,screenWidth, screenHeight) -> {
        int x = 0;
        int y = 0;
        int iconDim = 12;
        int offsetDim = 5;

        String currentSeason = "Summer";
        gui.getFont().draw(poseStack,currentSeason, (float) (x+20), (float) (y+offsetDim+(.25*iconDim)),0xffffffff);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,SUMMER);
        GuiComponent.blit(poseStack,x+offsetDim, y+offsetDim,0,0,iconDim,iconDim,iconDim,iconDim);

    });
}
