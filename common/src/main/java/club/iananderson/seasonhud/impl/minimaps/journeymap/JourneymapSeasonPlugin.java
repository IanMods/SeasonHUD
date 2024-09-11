package club.iananderson.seasonhud.impl.minimaps.journeymap;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.JourneyMapPlugin;
import journeymap.api.v2.client.event.InfoSlotDisplayEvent;
import journeymap.api.v2.client.event.RegistryEvent.InfoSlotRegistryEvent;
import journeymap.api.v2.client.event.RegistryEvent.OptionsRegistryEvent;
import journeymap.api.v2.common.event.ClientEventRegistry;
import journeymap.api.v2.common.event.MinimapEventRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@JourneyMapPlugin(
    apiVersion = "2.0.0"
)
public class JourneymapSeasonPlugin implements IClientPlugin {
  private static JourneymapSeasonPlugin INSTANCE;
  private IClientAPI api;
  private ClientProperties clientProperties;
  private Minecraft mc;
  private String seasonKeyString = "menu.seasonhud.infodisplay.season";
  private Component seasonKey = Component.translatable("menu.seasonhud.infodisplay.season");

  public JourneymapSeasonPlugin() {
  }

  public static JourneymapSeasonPlugin getInstance() {
    return INSTANCE;
  }

  public ClientProperties getClientProperties() {
    return clientProperties;
  }

  @Override
  public void initialize(@NotNull IClientAPI api) {
    INSTANCE = this;
    this.api = api;
    this.mc = Minecraft.getInstance();

    ClientEventRegistry.INFO_SLOT_REGISTRY_EVENT.subscribe(this.getModId(), this::infoSlotRegistryEvent);
    ClientEventRegistry.OPTIONS_REGISTRY_EVENT.subscribe(this.getModId(), this::optionsRegistryEvent);
    MinimapEventRegistry.INFO_SLOT_DISPLAY_EVENT.subscribe(this.getModId(), this::infoSlotDisplayEvent);

    Common.LOG.info("Initialized JourneyMapAPI");
  }

  @Override
  public String getModId() {
    return Common.MOD_ID;
  }

  private void infoSlotRegistryEvent(InfoSlotRegistryEvent event) {
    event.register(Common.MOD_ID, seasonKey, 1000L, () -> CurrentSeason.getInstance(mc).getSeasonHudComponent());
  }

  private void optionsRegistryEvent(OptionsRegistryEvent optionsRegistryEvent) {
    this.clientProperties = new ClientProperties();
  }

  private void infoSlotDisplayEvent(InfoSlotDisplayEvent event) {
    if (clientProperties.addAdditional.get()) {
      event.addLast(seasonKeyString, clientProperties.position.get());
    }
  }
}