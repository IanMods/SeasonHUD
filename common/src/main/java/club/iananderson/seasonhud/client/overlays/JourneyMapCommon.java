package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import java.util.List;
import journeymap.api.v2.client.util.tuple.Tuple2;
import journeymap.client.JourneymapClient;
import journeymap.client.io.ThemeLoader;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.RenderWrapper;
import journeymap.client.render.draw.DrawStep.Pass;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.draw.DrawUtil.HAlign;
import journeymap.client.render.draw.DrawUtil.VAlign;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme.LabelSpec;
import journeymap.client.ui.theme.Theme.Minimap.MinimapSpec;
import journeymap.client.ui.theme.ThemeLabelSource.InfoSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;

public class JourneyMapCommon {
  private final MutableComponent seasonCombined;
  private final Component seasonText;
  private final float infoSlotScale;
  private final float labelAlpha;
  private final float textAlpha;
  private final int halfWidth;
  private final int labelColor;
  private final int textColor;
  private final int labelHeight;
  private final int topLabelAreaHeight;
  private final int bottomLabelAreaHeight;
  private final double scaledWidth;
  private final double scaledHeight;
  private final DisplayVars vars;
  private final LabelSpec labelSpec;
  private final MinimapSpec minimapSpec;
  private double screenWidth;
  private double screenHeight;

  public JourneyMapCommon(Minecraft mc) {
    this.seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudComponent();
    this.seasonText = CurrentSeason.getInstance(mc).getSeasonText();
    Font fontRenderer = mc.font;
    JourneymapClient jm = JourneymapClient.getInstance();
    MiniMapProperties mapProperties = jm.getActiveMiniMapProperties();
    this.vars = UIManager.INSTANCE.getMiniMap().getDisplayVars();
    Tuple2<List<InfoSlot>, List<InfoSlot>> labelList = vars.getInfoSlotLabels(mapProperties);
    List<InfoSlot> topLabels = labelList.a();
    List<InfoSlot> bottomLabels = labelList.b();
    this.minimapSpec = ThemeLoader.getCurrentTheme().minimap.square;
    this.labelSpec = minimapSpec.labelBottom;
    this.infoSlotScale = mapProperties.infoSlotFontScale.get();
    this.labelAlpha = mapProperties.infoSlotAlpha.get();
    this.textAlpha = labelSpec.foreground.alpha;
    this.halfWidth = vars.minimapWidth / 2;
    this.labelColor = labelSpec.background.getColor();
    this.textColor = labelSpec.foreground.getColor();
    this.labelHeight = (int) ((double) (DrawUtil.getLabelHeight(fontRenderer, labelSpec.shadow) + labelSpec.margin)
        * infoSlotScale);
    this.topLabelAreaHeight = vars.getInfoLabelAreaHeight(fontRenderer, minimapSpec.labelTop, topLabels);
    this.bottomLabelAreaHeight = vars.getInfoLabelAreaHeight(fontRenderer, minimapSpec.labelBottom, bottomLabels);
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

  private double labelX() {
    return (vars.textureX + halfWidth);
  }

  private double labelY() {
    double startY = vars.textureY;

    if (Config.getJourneyMapAboveMap()) {
      startY -= ((minimapSpec.margin + topLabelAreaHeight + labelHeight));
    } else {
      startY += (vars.minimapHeight + minimapSpec.margin + bottomLabelAreaHeight);
    }

    return startY;
  }

  public float getInfoSlotScale() {
    return infoSlotScale;
  }

  public double getScreenWidth() {
    return screenWidth;
  }

  public double getScreenHeight() {
    return screenHeight;
  }

  public void drawSeasonLabel(GuiGraphics graphics) {
    MultiBufferSource.BufferSource buffers = graphics.bufferSource();
    buffers.endBatch();
    RenderWrapper.enableBlend();
    DrawUtil.drawBatchLabel(graphics.pose(), seasonCombined, Pass.TextBG, buffers, labelX(), labelY(), HAlign.Center,
                            VAlign.Below, labelColor, labelAlpha, textColor, textAlpha, infoSlotScale,
                            !(labelAlpha >= 0.05F) || labelSpec.shadow, 0.0);
    graphics.pose().translate(0.0F, 0.0F, 1.0F);
    DrawUtil.drawBatchLabel(graphics.pose(), seasonCombined, Pass.Text, buffers, labelX(), labelY(), HAlign.Center,
                            VAlign.Below, labelColor, labelAlpha, textColor, textAlpha, infoSlotScale,
                            !(labelAlpha >= 0.05F) || labelSpec.shadow, 0.0);
    RenderWrapper.disableBlend();
    buffers.endBatch();
    DrawUtil.sizeDisplay(scaledWidth, scaledHeight);
  }
}