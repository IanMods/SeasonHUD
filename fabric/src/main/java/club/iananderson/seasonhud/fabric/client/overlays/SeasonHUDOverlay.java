package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlayCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SeasonHUDOverlay implements HudRenderCallback {
    public static SeasonHUDOverlay HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new SeasonHUDOverlay();
        HudRenderCallback.EVENT.register(HUD_INSTANCE);
    }

    @Override
    public void onHudRender(PoseStack graphics, float alpha) {
        SeasonHUDOverlayCommon.render(graphics);
    }
}