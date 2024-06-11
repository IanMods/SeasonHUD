package club.iananderson.seasonhud.mixin.ftbchunks;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.config.Config.enableMinimapIntegration;
import static club.iananderson.seasonhud.config.Config.enableMod;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;
import static dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig.MINIMAP;

import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FTBChunksClient.class)
public class FTBChunksClientMixin {

  @Unique
  private static final BooleanValue MINIMAP_SEASON = MINIMAP.getBoolean("season", true)
                                                            .comment(new String[]{"Show season under minimap"});

  @Inject(method = "buildMinimapTextData", at = @At("RETURN"), remap = false, cancellable = true)

  private void buildMinimapTextData(Minecraft mc, double playerX, double playerY, double playerZ, MapDimension dim,
      CallbackInfoReturnable<List<Component>> cir) {
    MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
        getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE), getSeasonHudName().get(1).copy());
    List<Component> res = cir.getReturnValue();

    enableMod.set(MINIMAP_SEASON.get());

    if (enableMod.get() && enableMinimapIntegration.get()) {
      res.add(seasonCombined);
    }

    cir.setReturnValue(res);
  }
}
