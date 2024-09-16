package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.FTBChunksClientAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapContext;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SeasonComponent implements MinimapInfoComponent {
  public static final ResourceLocation ID = FTBChunksAPI.rl("season");

  public SeasonComponent() {
  }

  public static void registerFtbSeason() {
    FTBChunksClientAPI clientApi = FTBChunksAPI.clientApi();
    clientApi.registerMinimapComponent(new SeasonComponent());
  }

  public ResourceLocation id() {
    return ID;
  }

  public void render(MinimapContext context, GuiGraphics graphics, Font font) {
    if (CurrentMinimap.shouldDrawMinimapHud(Minimap.FTB_CHUNKS)) {
      MutableComponent seasonCombined = CurrentSeason.getInstance(context.minecraft()).getSeasonHudText();

      this.drawCenteredText(context.minecraft().font, graphics, seasonCombined, 0);
    }
  }
}
