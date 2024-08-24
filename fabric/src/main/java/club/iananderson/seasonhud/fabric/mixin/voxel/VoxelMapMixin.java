package club.iananderson.seasonhud.fabric.mixin.voxel;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mamiyaotaru.voxelmap.Map;
import com.mamiyaotaru.voxelmap.MapSettingsManager;
import com.mamiyaotaru.voxelmap.util.GLShim;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Map.class)
public class VoxelMapMixin {
  @Shadow
  private int mapX;
  @Shadow
  private int mapY;
  @Shadow
  private int scHeight;
  @Shadow
  private MapSettingsManager options;
  @Shadow
  private String error;
  @Shadow
  private boolean fullscreenMap;
  @Shadow
  private Minecraft game;
  @Shadow
  private Font fontRenderer;

  @Shadow
  private int chkLen(Component text) {
    return this.fontRenderer.width(text);
  }

  @Shadow
  private void write(PoseStack matrixStack, Component text, float x, float y, int color) {
    this.fontRenderer.drawShadow(matrixStack, text, x, y, color);
  }

  @Unique
  private void seasonhud$showSeason(PoseStack graphics, int x, int y) {
    int textStart;

    if (y > this.scHeight - 37 - 32 - 4 - 15) {
      textStart = y - 32 - 4 - 9;
      if (this.options.coords) {
        textStart -= 5;
      }
    } else {
      textStart = y + 32 + 4;
      if (this.options.coords) {
        textStart += 10;
      }
      if (!this.error.isEmpty()) {
        textStart += 5;
      }
    }

    if (!this.options.hide && !this.fullscreenMap) {
      boolean unicode = this.game.options.forceUnicodeFont;
      float scale = unicode ? 1.0F : 0.5F;
      GLShim.glPushMatrix();
      GLShim.glScalef(scale, scale, 1.0F);
      MutableComponent seasonCombined = CurrentSeason.getInstance(game).getSeasonHudText();
      int m = this.chkLen(seasonCombined) / 2;
      this.write(graphics, seasonCombined, (float) x / scale - (float) m, (float) textStart / scale, 0xffffff);
      GLShim.glPopMatrix();
    }
  }

  @Inject(method = "drawMinimap", at = @At(value = "INVOKE", target = "Lcom/mamiyaotaru/voxelmap/Map;showCoords"
      + "(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", shift = At.Shift.BY, by = 2))
  private void drawMinimap(PoseStack matrixStack, Minecraft mc, CallbackInfo ci) {
    if (Config.getEnableMod() && Config.getEnableMinimapIntegration()) {
      this.seasonhud$showSeason(matrixStack, this.mapX, this.mapY);
    }
  }
}