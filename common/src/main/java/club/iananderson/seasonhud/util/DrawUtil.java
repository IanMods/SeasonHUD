package club.iananderson.seasonhud.util;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public class DrawUtil {
  private DrawUtil() {
  }

  public static void blitWithBorder(PoseStack graphics, GuiComponent guiComponent, ResourceLocation texture, int x,
      int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder,
      int leftBorder, int rightBorder) {
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    int fillerWidth = textureWidth - leftBorder - rightBorder;
    int fillerHeight = textureHeight - topBorder - bottomBorder;
    int canvasWidth = width - leftBorder - rightBorder;
    int canvasHeight = height - topBorder - bottomBorder;
    int xPasses = canvasWidth / fillerWidth;
    int remainderWidth = canvasWidth % fillerWidth;
    int yPasses = canvasHeight / fillerHeight;
    int remainderHeight = canvasHeight % fillerHeight;
    Minecraft.getInstance().getTextureManager().bind(texture);
    guiComponent.blit(graphics, x, y, u, v, leftBorder, topBorder);
    guiComponent.blit(graphics, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder,
                      topBorder);
    guiComponent.blit(graphics, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder,
                      bottomBorder);
    guiComponent.blit(graphics, x + leftBorder + canvasWidth, y + topBorder + canvasHeight,
                      u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder);

    int i;
    for (i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); ++i) {
      guiComponent.blit(graphics, x + leftBorder + i * fillerWidth, y, u + leftBorder, v,
                        i == xPasses ? remainderWidth : fillerWidth, topBorder);
      guiComponent.blit(graphics, x + leftBorder + i * fillerWidth, y + topBorder + canvasHeight, u + leftBorder,
                        v + topBorder + fillerHeight, i == xPasses ? remainderWidth : fillerWidth, bottomBorder);

      for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j) {
        guiComponent.blit(graphics, x + leftBorder + i * fillerWidth, y + topBorder + j * fillerHeight, u + leftBorder,
                          v + topBorder, i == xPasses ? remainderWidth : fillerWidth,
                          j == yPasses ? remainderHeight : fillerHeight);
      }
    }

    for (i = 0; i < yPasses + (remainderHeight > 0 ? 1 : 0); ++i) {
      guiComponent.blit(graphics, x, y + topBorder + i * fillerHeight, u, v + topBorder, leftBorder,
                        i == yPasses ? remainderHeight : fillerHeight);
      guiComponent.blit(graphics, x + leftBorder + canvasWidth, y + topBorder + i * fillerHeight,
                        u + leftBorder + fillerWidth, v + topBorder, rightBorder,
                        i == yPasses ? remainderHeight : fillerHeight);
    }

  }

  public static void blitWithBorder(PoseStack graphics, GuiComponent guiComponent, ResourceLocation texture, int x,
      int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int borderSize) {
    blitWithBorder(graphics, guiComponent, texture, x, y, u, v, width, height, textureWidth, textureHeight, borderSize,
                   borderSize, borderSize, borderSize);
  }

  //1.18
  public static void enableScissor(int i, int j, int k, int l) {
    Window window = Minecraft.getInstance().getWindow();
    int height = window.getHeight();
    double guiScale = window.getGuiScale();
    double e = i * guiScale;
    double f = height - l * guiScale;
    double g = (k - i) * guiScale;
    double h = (l - j) * guiScale;
    RenderSystem.enableScissor((int) e, (int) f, Math.max(0, (int) g), Math.max(0, (int) h));
  }

  //1.18
  public static void disableScissor() {
    RenderSystem.disableScissor();
  }
}
