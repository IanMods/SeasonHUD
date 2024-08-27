package club.iananderson.seasonhud.fabric.mixin.ftbchunks;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FTBChunksClient.class)
public class FTBChunksClientMixin {
  @Unique
  private static final BooleanValue MINIMAP_SEASON = FTBChunksClientConfig.MINIMAP.getBoolean("season", true)
      .comment(new String[]{"Show season under minimap"});
  @Final
  @Shadow
  private static List<Component> MINIMAP_TEXT_LIST;

  @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.AFTER), remap = false)

  private void renderHud(PoseStack matrixStack, float tickDelta, CallbackInfo ci) {
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();

    Config.setEnableMod(MINIMAP_SEASON.get());

    if (Config.getEnableMod() && Config.getEnableMinimapIntegration()) {
      MINIMAP_TEXT_LIST.add(seasonCombined);
    }
  }
}