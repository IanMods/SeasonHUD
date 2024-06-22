package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;

public class JourneyMap {
  private static JourneyMap instance;
  private Minecraft mc;
  private int scaledWidth;
  private int scaledHeight;
  private MutableComponent seasonCombined;

  public JourneyMap(Minecraft mc) {
    instance = this;
    this.mc = mc;
    this.scaledWidth = mc.getWindow().getGuiScaledWidth();
    this.scaledHeight = mc.getWindow().getGuiScaledWidth();
    this.seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
  }

  public static JourneyMap getInstance() {
    return instance;
  }

}
