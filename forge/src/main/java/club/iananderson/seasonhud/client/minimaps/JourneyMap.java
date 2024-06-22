package club.iananderson.seasonhud.client.minimaps;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import journeymap.client.JourneymapClient;
import journeymap.client.io.ThemeLoader;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class JourneyMap implements IGuiOverlay {

  @Override
  public void render(ForgeGui gui, GuiGraphics seasonStack, float partialTick, int scaledWidth, int scaledHeight) {
    if (CurrentMinimap.minimapLoaded("journeymap")) {
      Minecraft mc = Minecraft.getInstance();
      MutableComponent seasonCombined = new CurrentSeason(mc).getSeasonHudText();
      DisplayVars vars = UIManager.INSTANCE.getMiniMap().getDisplayVars();
      JourneymapClient jm = JourneymapClient.getInstance();
      Font fontRenderer = mc.font;
      float fontScale = jm.getActiveMiniMapProperties().fontScale.get();
      double screenWidth = mc.getWindow().getWidth();
      double screenHeight = mc.getWindow().getHeight();
      double textureX = vars.textureX;
      double textureY = vars.textureY;
      int minimapHeight = vars.minimapHeight;
      int minimapWidth = vars.minimapWidth;

      int halfWidth = minimapWidth / 2;

      Theme.LabelSpec currentTheme = ThemeLoader.getCurrentTheme().minimap.square.labelBottom;
      int labelColor = currentTheme.background.getColor();
      int textColor = currentTheme.foreground.getColor();
      float labelAlpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
      float textAlpha = currentTheme.foreground.alpha;
      boolean fontShadow = currentTheme.shadow;
      int labelHeight = (int) ((DrawUtil.getLabelHeight(fontRenderer, fontShadow) + currentTheme.margin) * fontScale);

      int topLabelHeight = vars.getInfoLabelAreaHeight(fontRenderer, currentTheme,
          ThemeLabelSource.values.get(jm.getActiveMiniMapProperties().info1Label.get()),
          ThemeLabelSource.values.get(jm.getActiveMiniMapProperties().info2Label.get()));
      int bottomLabelHeight = vars.getInfoLabelAreaHeight(fontRenderer, currentTheme,
          ThemeLabelSource.values.get(jm.getActiveMiniMapProperties().info3Label.get()),
          ThemeLabelSource.values.get(jm.getActiveMiniMapProperties().info4Label.get()));

      int margin = ThemeLoader.getCurrentTheme().minimap.square.margin;

      int startX = (int) (textureX + halfWidth);
      int startY = (int) (textureY + (Config.journeyMapAboveMap.get() ? -margin - labelHeight
                                                                      : minimapHeight + margin));
      if (CurrentMinimap.shouldDrawMinimapHud()) {
        int labelX = startX;
        int labelY = startY + (Config.journeyMapAboveMap.get() ? -topLabelHeight : bottomLabelHeight);

        if (Config.journeyMapMacOS.get()) {
          screenWidth = screenWidth / 2;
          screenHeight = screenHeight / 2;
        }

        seasonStack.pose().pushPose();
        seasonStack.pose().scale(1 / fontScale, 1 / fontScale, 0);
        DrawUtil.sizeDisplay(seasonStack.pose(), screenWidth, screenHeight);
        seasonStack.pose().popPose();
        DrawUtil.drawBatchLabel(seasonStack.pose(), seasonCombined, seasonStack.bufferSource(), labelX, labelY,
            DrawUtil.HAlign.Center, DrawUtil.VAlign.Below, labelColor, labelAlpha, textColor, textAlpha, fontScale,
            fontShadow);
        seasonStack.bufferSource().endBatch();
        DrawUtil.sizeDisplay(seasonStack.pose(), scaledWidth, scaledHeight);
      }
    }
  }
}