//package club.iananderson.seasonhud.impl.minimaps;
//
//import club.iananderson.seasonhud.SeasonHUD;
//import journeymap.client.api.IClientAPI;
//import journeymap.client.api.IClientPlugin;
//import journeymap.client.api.event.ClientEvent;
//
//import javax.annotation.ParametersAreNonnullByDefault;
//
//@ParametersAreNonnullByDefault
//public class JourneyMapAPI implements IClientPlugin {
//    // API reference
//    private IClientAPI jmAPI = null;
//
//    private static JourneyMapAPI INSTANCE;
//
//    public JourneyMapAPI()
//    {
//        INSTANCE = this;
//    }
//
//    public static JourneyMapAPI getInstance()
//    {
//        return INSTANCE;
//    }
//
//
//    @Override
//    public void initialize(IClientAPI jmClientApi) {
//        // Set ClientProxy.SampleWaypointFactory with an implementation that uses the JourneyMap IClientAPI under the covers.
//        jmAPI = jmClientApi;
//
//    }
//
//    @Override
//    public String getModId() {
//        return SeasonHUD.MOD_ID;
//    }
//
//    @Override
//    public void onEvent(ClientEvent event) {
////        RegistryEvent registryEvent = (RegistryEvent) event;
////        ((RegistryEvent.InfoSlotRegistryEvent) registryEvent).register(getModId(), "Season", 1000, () -> String.valueOf(getSeasonName()));
//    }
//}
