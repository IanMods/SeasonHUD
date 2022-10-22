package club.iananderson.seasoninfo.client;

import club.iananderson.seasoninfo.Seasoninfo;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class SeasonHudOverlay {

//    public Season currentSeason = SeasonHelper.getSeasonState(Minecraft.getInstance().level).getSeason();
//    public class SeasonName{
//        private String SEASONNAMEOLD;
//        public String getName(){
//            return SEASONNAMEOLD;
//        }
//        public void setName(String SEASONNAME) {
//            this.SEASONNAMEOLD = SEASONNAME;
//       }
//    }


    private static final ResourceLocation SPRING = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/spring.png");
    private static final ResourceLocation SUMMER = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/summer.png");
    private static final ResourceLocation FALL = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/fall.png");
    private static final ResourceLocation WINTER = new ResourceLocation(Seasoninfo.MODID,
            "textures/season/winter.png");
    //private static final ResourceLocation SEASONNAME = new ResourceLocation(Seasoninfo.MODID,
    //        "textures/season/"+ SEASON + ".png");

    public static final IGuiOverlay HUD_SEASON = (gui, poseStack, partialTick,screenWidth, screenHeight) -> {
        int x = 0;
        int y = 0;
        int iconDim = 12;
        int offsetDim = 5;

        Season currentSeason = SeasonHelper.getSeasonState(Minecraft.getInstance().level).getSeason();
        String SEASONNAME = currentSeason.name();
        String seasonLower = SEASONNAME.toLowerCase();
        String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);

        //String currentSeason = "Summer";
        gui.getFont().draw(poseStack,seasonCap, (float) (x+20), (float) (y+offsetDim+(.25*iconDim)),0xffffffff);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,WINTER);
        GuiComponent.blit(poseStack,x+offsetDim, y+offsetDim,0,0,iconDim,iconDim,iconDim,iconDim);

    };
}
