package club.iananderson.seasonhud.client.minimaps;

import static club.iananderson.seasonhud.Common.SEASON_STYLE;
import static club.iananderson.seasonhud.client.SeasonHUDClient.mc;
import static club.iananderson.seasonhud.config.Config.enableMod;
import static club.iananderson.seasonhud.config.Config.journeyMapAboveMap;
import static club.iananderson.seasonhud.config.Config.journeyMapMacOS;
import static club.iananderson.seasonhud.impl.seasons.CurrentSeason.getSeasonHudName;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import journeymap.client.JourneymapClient;
import journeymap.client.io.ThemeLoader;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class JourneyMap implements LayeredDraw.Layer {
  private static String getSeason() {
    MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
        getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE), getSeasonHudName().get(1).copy());
    ;

    return seasonCombined.getString();
  }

  @Override
  public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    int scaledWidth = guiGraphics.guiWidth();
    int scaledHeight = guiGraphics.guiHeight();

    MutableComponent seasonCombined = Component.translatable("desc.seasonhud.combined",
        getSeasonHudName().get(0).copy().withStyle(SEASON_STYLE), getSeasonHudName().get(1).copy());

    if (Services.PLATFORM.isModLoaded("journeymap") && !enableMod.get()) {
      ThemeLabelSource.InfoSlot Season = ThemeLabelSource.create(Common.MOD_ID, "menu.seasonhud.infodisplay.season",
          1000L, 1L, JourneyMap::getSeason);
      // Should only show up if the "Enable Mod" option in the SeasonHUD menu/config is disabled. Icon currently doesn't work
    }

    if (CurrentMinimap.minimapLoaded("journeymap")) {
      DisplayVars vars = UIManager.INSTANCE.getMiniMap().getDisplayVars();
      JourneymapClient jm = JourneymapClient.getInstance();

      Font fontRenderer = mc.font;
      float guiScale = (float) mc.getWindow().getGuiScale();

      double screenWidth = mc.getWindow().getWidth();
      double screenHeight = mc.getWindow().getHeight();

      int minimapHeight = vars.minimapHeight;
      int minimapWidth = vars.minimapWidth;

      float fontScale = jm.getActiveMiniMapProperties().infoSlotFontScale.get();

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

      double textureX = vars.textureX;
      double textureY = vars.textureY;

      int startX = (int) (textureX + halfWidth);
      int startY = (int) (textureY + (journeyMapAboveMap.get() ? -margin - labelHeight : minimapHeight + margin));

      if (CurrentMinimap.shouldDrawMinimapHud()) {
        int labelX = (int) startX;
        int labelY = startY + (journeyMapAboveMap.get() ? -topLabelHeight : bottomLabelHeight);

        if (journeyMapMacOS.get()) {
          screenWidth = screenWidth / 2;
          screenHeight = screenHeight / 2;
        }

        //for macOS
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(1 / fontScale, 1 / fontScale, 0);
        DrawUtil.sizeDisplay(screenWidth, screenHeight);
        guiGraphics.pose().popPose();

        DrawUtil.drawBatchLabel(guiGraphics, seasonCombined, labelX, labelY, DrawUtil.HAlign.Center,
            DrawUtil.VAlign.Below, labelColor, labelAlpha, textColor, textAlpha, fontScale, fontShadow, 0);
        DrawUtil.sizeDisplay(scaledWidth, scaledHeight);
      }
    }
  }
}