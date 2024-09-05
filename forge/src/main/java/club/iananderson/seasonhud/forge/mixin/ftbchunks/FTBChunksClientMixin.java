package club.iananderson.seasonhud.forge.mixin.ftbchunks;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.FTBChunksCommon;
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
public class FTBChunksClientMixin extends FTBChunksCommon {

  @Unique
  private static final BooleanValue MINIMAP_SEASON = FTBChunksClientConfig.MINIMAP.getBoolean("season", true)
                                                                                  .comment(new String[]{
                                                                                      "Show season under minimap"});
  @Final
  @Shadow
  private static List<Component> MINIMAP_TEXT_LIST;

  @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.AFTER), remap = false)

  private void renderHud(PoseStack matrixStack, float tickDelta, CallbackInfo ci) {
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
//    float scale = (float) ((Double) FTBChunksClientConfig.MINIMAP_SCALE.get() * 4.0 / mc.getWindow().getGuiScale());
//    int s = (int) (64.0 * (double) scale);
//    int x = (FTBChunksClientConfig.MINIMAP_POSITION.get()).getX(mc.getWindow().getGuiScaledWidth(), s);
//    int y = (FTBChunksClientConfig.MINIMAP_POSITION.get()).getY(mc.getWindow().getGuiScaledHeight(), s);
//    float iconScale = scale / 2;
//    double s2d = (double) s / 2;
//    int iconX = (int) (x + s2d - (double) mc.font.width(seasonCombined) / 2);
//    int iconY = (y + s) + 3;

    Config.setEnableMod(MINIMAP_SEASON.get());

    if (Config.getEnableMod() && Config.getEnableMinimapIntegration()) {
      MINIMAP_TEXT_LIST.add(seasonCombined);
//      int listPosition = MINIMAP_TEXT_LIST.indexOf(seasonCombined);
//
//      CurrentSeason.getInstance(mc).drawIcon(mc, matrixStack, iconScale, iconX, (iconY + (listPosition * 11)));
    }
  }
}
