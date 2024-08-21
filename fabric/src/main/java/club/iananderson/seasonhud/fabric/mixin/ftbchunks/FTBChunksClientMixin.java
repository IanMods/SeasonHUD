package club.iananderson.seasonhud.fabric.mixin.ftbchunks;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FTBChunksClient.class)
public class FTBChunksClientMixin {

    @Unique
    private static final BooleanValue MINIMAP_SEASON = FTBChunksClientConfig.MINIMAP.getBoolean("season", true)
            .comment(new String[]{"Show season under minimap"});

    @Inject(method = "buildMinimapTextData", at = @At("RETURN"), remap = false, cancellable = true)

    private void buildMinimapTextData(Minecraft mc, double playerX, double playerY, double playerZ, MapDimension dim,
                                      CallbackInfoReturnable<List<Component>> cir) {
        MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
        List<Component> res = cir.getReturnValue();

        Config.setEnableMod(MINIMAP_SEASON.get());

        if (Config.getEnableMod() && Config.getEnableMinimapIntegration()) {
            res.add(seasonCombined);
        }

        cir.setReturnValue(res);
    }
}
