package club.iananderson.seasonhud.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import xaero.common.IXaeroMinimap;
import xaero.common.XaeroMinimapSession;
import xaero.common.graphics.CustomVertexConsumers;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.MinimapInterface;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.render.MinimapRenderer;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.minimap.waypoints.render.CompassRenderer;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.misc.Misc;
import xaero.common.settings.ModSettings;

import java.util.ArrayList;
import java.util.Objects;

import static xaero.common.settings.ModOptions.modMain;

public abstract class MinimapSeasons extends MinimapRenderer {
    protected IXaeroMinimap modMain;

    protected Minecraft mc;
    protected MinimapInterface minimapInterface;
    protected MinimapRendererHelper helper;
    protected WaypointsGuiRenderer waypointsGuiRenderer;
    private int lastMinimapSize;
    private final ArrayList<Component> underText;
    protected double zoom = 1.0;
    private BlockPos.MutableBlockPos mutableBlockPos;


    public MinimapSeasons(IXaeroMinimap modMain, Minecraft mc, WaypointsGuiRenderer waypointsGuiRenderer, MinimapInterface minimapInterface, CompassRenderer compassRenderer) {
        super(modMain, mc, waypointsGuiRenderer, minimapInterface, compassRenderer);
        this.modMain = modMain;
        this.mc = mc;
        this.waypointsGuiRenderer = waypointsGuiRenderer;
        this.minimapInterface = minimapInterface;
        this.underText = new ArrayList();
        this.helper = new MinimapRendererHelper();
        this.mutableBlockPos = new BlockPos.MutableBlockPos();
    }

    protected void renderChunks(XaeroMinimapSession minimapSession, PoseStack matrixStack, MinimapProcessor minimap, int mapSize, int bufferSize, float sizeFix, float partial, int lightLevel, boolean useWorldMap, boolean lockedNorth, int shape, double ps, double pc, boolean cave, boolean circle, ModSettings settings, CustomVertexConsumers cvc) {
    }

    public void renderMinimapSeasons(XaeroMinimapSession minimapSession, PoseStack matrixStack, MinimapProcessor minimap, int x, int y, int width, int height, double scale, int size, float partial, CustomVertexConsumers cvc) {
        ModSettings settings = this.modMain.getSettings();

        int mapSize = minimapSession.getMinimapProcessor().getMinimapSize();
        int bufferSize = minimapSession.getMinimapProcessor().getMinimapBufferSize(mapSize);
        int lightLevel = (int)((1.0F - Math.min(1.0F, this.getSunBrightness())) * (float)(minimap.getMinimapWriter().getLoadedLevels() - 1));

        int shape = modMain.getSettings().minimapShape;
        float sizeFix = (float)bufferSize / 512.0F;
        float minimapScale = settings.getMinimapScale();
        float mapScale = (float)(scale / (double)minimapScale);

        boolean useWorldMap = this.modMain.getSupportMods().shouldUseWorldMapChunks() && minimap.getMinimapWriter().getLoadedCaving() == Integer.MAX_VALUE && minimap.getMinimapWriter().loadedLightOverlayType <= 0;
        boolean cave = !useWorldMap && minimap.getMinimapWriter().getLoadedCaving() != Integer.MAX_VALUE;
        boolean lockedNorth = settings.getLockNorth(mapSize / 2, shape);
        boolean circleShape = shape == 1;

        double angle = Math.toRadians(this.getRenderAngle(lockedNorth));
        double ps = Math.sin(Math.PI - angle);
        double pc = Math.cos(Math.PI - angle);

        ArrayList<Component> underText = this.underText;
        matrixStack.pushPose();
        matrixStack.scale(1.0F / mapScale, 1.0F / mapScale, 1.0F);


        this.renderChunks(minimapSession, matrixStack, minimap, mapSize, bufferSize, sizeFix, partial, lightLevel, useWorldMap, lockedNorth, shape, ps, pc, cave, circleShape, settings, cvc);

        int scaledX = (int)((float)x * mapScale);
        int scaledY = (int)((float)y * mapScale);


        RenderSystem.setShaderTexture(0, InterfaceRenderer.guiTextures);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pushPose();
        matrixStack.translate((double)(scaledX + 9), (double)(scaledY + 9), 0.0);
        matrixStack.scale(1.0F / minimapScale, 1.0F / minimapScale, 1.0F);

        if (settings.displayCurrentClaim){
            Season currentSeason = SeasonHelper.getSeasonState(Objects.requireNonNull(mc.level)).getSeason();
            String seasonCap = currentSeason.name();
            String seasonLower = seasonCap.toLowerCase();
            String seasonName = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);
            this.addLineWordsUnderMinimap(underText, size, seasonName);
        }

//        this.drawTextUnderMinimap(matrixStack, underText, scaledX, scaledY, height, size, mapScale, settings, cvc.getBetterPVPRenderTypeBuffers());
//        matrixStack.popPose();
//        Lighting.setupFor3DItems();

    }

    private void addLineWordsUnderMinimap(ArrayList<Component> underText, int size, String line) {
        if (this.mc.font.width(line) <= size) {
            underText.add(Component.literal(line));
        } else {
            String[] words = line.split(" ");
            StringBuilder lineBuilder = new StringBuilder();

            for(int i = 0; i < words.length; ++i) {
                int wordStart = lineBuilder.length();
                if (i > 0) {
                    lineBuilder.append(' ');
                }

                lineBuilder.append(words[i]);
                if (i != 0) {
                    int lineWidth = this.mc.font.width(lineBuilder.toString());
                    if (lineWidth > size) {
                        lineBuilder.delete(wordStart, lineBuilder.length());
                        underText.add(Component.literal(lineBuilder.toString()));
                        lineBuilder.delete(0, lineBuilder.length());
                        lineBuilder.append(words[i]);
                    }
                }
            }

            underText.add(Component.literal(lineBuilder.toString()));
        }

    }

    public void drawTextUnderMinimap(PoseStack matrixStack, ArrayList<Component> underText, int scaledX, int scaledY, int height, int size, float mapScale, ModSettings settings, MultiBufferSource.BufferSource textRenderTypeBuffer){
        int interfaceSize = size;
        int scaledHeight = (int)((float)height * mapScale);
        int align = settings.minimapTextAlign;

        for(int i = 0; i < underText.size(); ++i) {
            Component s = underText.get(i);
            int stringWidth = this.mc.font.width(s);
            boolean under = scaledY + interfaceSize / 2 < scaledHeight / 2;
            int stringY = scaledY + (under ? interfaceSize : -9) + i * 10 * (under ? 1 : -1);
            int stringX = scaledX + (align == 0 ? interfaceSize / 2 - stringWidth / 2 : (align == 1 ? 6 : interfaceSize - 6 - stringWidth));
            Misc.drawNormalText(matrixStack, s, (float)stringX, (float)stringY, -1, true, textRenderTypeBuffer);
        }

        textRenderTypeBuffer.endBatch();
        underText.clear();
    }
    private float getSunBrightness() {
        return Mth.clamp((this.mc.level.getSkyDarken(1.0F) - 0.2F) / 0.8F, 0.0F, 1.0F);
    }



}
