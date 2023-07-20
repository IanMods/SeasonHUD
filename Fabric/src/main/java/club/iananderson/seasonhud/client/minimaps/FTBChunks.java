package club.iananderson.seasonhud.client.minimaps;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.MinimapPosition;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftbchunks.data.ClaimedChunk;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManager;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonName;
import static club.iananderson.seasonhud.impl.fabricseasons.CurrentSeason.getSeasonResource;
import static club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.loadedMinimap;


public class FTBChunks implements HudRenderCallback{
    public static FTBChunks HUD_INSTANCE;
    private boolean needDisableBlend = false;

    public static void init()
    {
        HUD_INSTANCE = new FTBChunks();
        HudRenderCallback.EVENT.register(HUD_INSTANCE);
    }

    private void enableAlpha(float alpha)
    {
        needDisableBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    private void disableAlpha(float alpha)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (needDisableBlend)
            RenderSystem.disableBlend();
    }
    @Override
    public void onHudRender(PoseStack seasonStack, float alpha)
    {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);

        if (loadedMinimap("ftbchunks")) {
            MapDimension dim = MapDimension.getCurrent();
            ClaimedChunkManager chunkManager = dev.ftb.mods.ftbchunks.data.FTBChunksAPI.getManager();

            boolean biome = FTBChunksClientConfig.MINIMAP_BIOME.get();
            boolean xyz = FTBChunksClientConfig.MINIMAP_XYZ.get();
            boolean claimed = FTBChunksClientConfig.MINIMAP_ZONE.get();

            ChunkDimPos chunk = new ChunkDimPos(mc.player);
            ClaimedChunk playerChunk = chunkManager.getChunk(chunk);

            int i = 0;

            if(biome){i++;}
            if(xyz){i++;}
            if(claimed && (playerChunk != null)) {i++;}

            //Season
            MINIMAP_TEXT_LIST.add(getSeasonName().get(0));

            if (mc.player != null && mc.level != null && MapManager.inst != null) {
                double guiScale = mc.getWindow().getGuiScale();
                int ww = mc.getWindow().getGuiScaledWidth();
                int wh = mc.getWindow().getGuiScaledHeight();

                if (dim != null) {

                    if (!mc.options.renderDebug && FTBChunksClientConfig.MINIMAP_ENABLED.get() &&FTBChunksClientConfig.MINIMAP_VISIBILITY.get() != 0 && !(Boolean) FTBChunksWorldConfig.FORCE_DISABLE_MINIMAP.get()) {
                        float scale = (float) ( FTBChunksClientConfig.MINIMAP_SCALE.get() * 4.0 / guiScale);
                        int s = (int) (64.0 * (double) scale);
                        double s2d = (double) s / 2.0;
                        MinimapPosition minimapPosition = FTBChunksClientConfig.MINIMAP_POSITION.get();
                        int x = minimapPosition.getX(ww, s);
                        int y = minimapPosition.getY(wh, s);
                        int offsetX = FTBChunksClientConfig.MINIMAP_OFFSET_X.get();
                        int offsetY = FTBChunksClientConfig.MINIMAP_OFFSET_Y.get();

                        MinimapPosition.MinimapOffsetConditional offsetConditional = FTBChunksClientConfig.MINIMAP_POSITION_OFFSET_CONDITION.get();

                        if (offsetConditional.isNone() || offsetConditional.getPosition() == minimapPosition) {
                            x += minimapPosition.posX == 0 ? offsetX : -offsetX;
                            y -= minimapPosition.posY > 1 ? offsetY : -offsetY;
                        }

                        enableAlpha(alpha);
                        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
                        seasonStack.pushPose();

                        seasonStack.translate((double)x + s2d, (double)(y + s) + 3.0, 0.0);
                        seasonStack.scale((float)(0.5 * (double)scale), (float)(0.5 * (double)scale), 1.0F);


                        FormattedCharSequence bs = (MINIMAP_TEXT_LIST.get(0)).getVisualOrderText();
                        int bsw = mc.font.width(bs);
                        int iconDim = mc.font.lineHeight;

                        font.drawShadow(seasonStack, bs, (float)((-bsw) + iconDim/2)/ 2.0F, (float)(i * 11), -1);

                        //Icon
                        ResourceLocation SEASON = getSeasonResource();
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.setShaderTexture(0, SEASON);
                        GuiComponent.blit(seasonStack,(int)((-bsw) / 2.0F)-iconDim, (i * 11), 0, 0, iconDim, iconDim, iconDim, iconDim);
                        seasonStack.popPose();
                        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
                        disableAlpha(alpha);
                    }
                }
            }
        }
    };
}

