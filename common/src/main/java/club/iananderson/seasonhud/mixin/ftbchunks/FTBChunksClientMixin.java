package club.iananderson.seasonhud.mixin.ftbchunks;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FTBChunksClient.class)
public class FTBChunksClientMixin {

  @Shadow
  private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);


  @Unique
  private static final BooleanValue MINIMAP_SEASON = FTBChunksClientConfig.MINIMAP.getBoolean("season", true)
                                                                                  .comment(new String[]{
                                                                                      "Show season under minimap"});

  @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"), remap = false)
  private void renderHud(PoseStack matrixStack, float tickDelta, CallbackInfo ci) {
    MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getSeasonHudText();

    Config.setEnableMod(MINIMAP_SEASON.get());

    if (CurrentMinimap.shouldDrawMinimapHud(Minimap.FTB_CHUNKS)) {
      MINIMAP_TEXT_LIST.add(seasonCombined);
    }
  }
}