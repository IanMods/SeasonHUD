package club.iananderson.seasonhud.client;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class SeasonHUDOverlay implements IGuiOverlay{
    public SeasonHUDOverlay(){
    }

    @Override
    public void render(ExtendedGui gui, GuiGraphics seasonStack, float partialTick, int screenWidth, int screenHeight) {
        SeasonHUDOverlayCommon.render(seasonStack);
    }
}