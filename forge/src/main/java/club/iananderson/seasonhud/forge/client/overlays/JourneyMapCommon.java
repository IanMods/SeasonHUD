package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import journeymap.client.JourneymapClient;
import journeymap.client.io.ThemeLoader;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme.LabelSpec;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;

public class JourneyMapCommon {
  private final Component seasonCombined;
  private final boolean fontShadow;
  private final float fontScale;
  private final float labelAlpha;
  private final float textAlpha;
  private final double textureX;
  private final double textureY;
  private final int minimapHeight;
  private final int halfWidth;
  private final int margin;
  private final int labelColor;
  private final int textColor;
  private final int labelHeight;
  private final int topLabelHeight;
  private final int bottomLabelHeight;
  private final double scaledWidth;
  private final double scaledHeight;
  private double screenWidth;
  private double screenHeight;

  public JourneyMapCommon(Minecraft mc) {
    Font fontRenderer = mc.font;
    this.seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
    JourneymapClient jm = JourneymapClient.getInstance();
    DisplayVars vars = UIManager.INSTANCE.getMiniMap().getDisplayVars();
    MiniMapProperties mapProperties = jm.getActiveMiniMapProperties();
    LabelSpec currentTheme = ThemeLoader.getCurrentTheme().minimap.square.labelBottom;
    this.fontShadow = currentTheme.shadow;
    this.fontScale = jm.getActiveMiniMapProperties().fontScale.get();
    this.labelAlpha = jm.getActiveMiniMapProperties().infoSlotAlpha.get();
    this.textAlpha = currentTheme.foreground.alpha;
    this.textureX = vars.textureX;
    this.textureY = vars.textureY;
    int minimapWidth = vars.minimapWidth;
    this.minimapHeight = minimapWidth;
    this.halfWidth = minimapWidth / 2;
    this.margin = ThemeLoader.getCurrentTheme().minimap.square.margin;
    this.labelColor = currentTheme.background.getColor();
    this.textColor = currentTheme.foreground.getColor();
    this.labelHeight = (int) ((DrawUtil.getLabelHeight(fontRenderer, fontShadow) + currentTheme.margin) * fontScale);
    this.topLabelHeight = vars.getInfoLabelAreaHeight(fontRenderer, currentTheme,
                                                      ThemeLabelSource.values.get(mapProperties.info1Label.get()),
                                                      ThemeLabelSource.values.get(mapProperties.info2Label.get()));
    this.bottomLabelHeight = vars.getInfoLabelAreaHeight(fontRenderer, currentTheme,
                                                         ThemeLabelSource.values.get(mapProperties.info3Label.get()),
                                                         ThemeLabelSource.values.get(mapProperties.info4Label.get()));
    this.screenWidth = mc.getWindow().getWidth();
    this.screenHeight = mc.getWindow().getHeight();
    if (Config.getJourneyMapMacOS()) {
      this.screenWidth /= 2;
      this.screenHeight /= 2;
    }
    double guiScale = mc.getWindow().getGuiScale();
    this.scaledWidth = screenWidth / guiScale;
    this.scaledHeight = screenHeight / guiScale;
  }

  public static JourneyMapCommon getInstance(Minecraft mc) {
    return new JourneyMapCommon(mc);
  }

  private int labelX() {
    return (int) (textureX + halfWidth);
  }

  private int labelY() {
    int startY = (int)(textureY + (Config.getJourneyMapAboveMap() ? 0 : (Config.getJourneyMapMacOS() ?
                                                            -margin - labelHeight : minimapHeight + margin)));

    return startY + (Config.getJourneyMapAboveMap() ? -topLabelHeight - margin - labelHeight : bottomLabelHeight);
  }

  public float getFontScale() {
    return fontScale;
  }

  public double getScreenWidth() {
    return screenWidth;
  }

  public double getScreenHeight() {
    return screenHeight;
  }

  public void drawSeasonLabel(PoseStack graphics) {
    MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
    buffers.endBatch();
    DrawUtil.drawLabel(graphics, seasonCombined, labelX(), labelY(), DrawUtil.HAlign.Center, DrawUtil.VAlign.Below,
                       labelColor, labelAlpha, textColor, textAlpha, fontScale, fontShadow);
    buffers.endBatch();
    DrawUtil.sizeDisplay(scaledWidth, scaledHeight);
  }
}