package club.iananderson.seasonhud.impl.minimaps;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;


import java.util.Objects;

import static net.minecraft.world.level.Level.OVERWORLD;


public class CurrentMinimap {
    public static boolean loadedMinimap(String minimap){
       if(!hideMinimap()){
        return FabricLoader.getInstance().isModLoaded(minimap);
       }
       else return false;
    }

    public static boolean noMinimap(){
        if(!hideMinimap()) {
            return !loadedMinimap("xaerominimap")  && !loadedMinimap("xaerominimapfair")
                    && !loadedMinimap("journeymap") && !loadedMinimap("ftbchunks");
        }
        else return false;
    }

    public static boolean hideMinimap(){
        Minecraft mc = Minecraft.getInstance();
        ResourceKey<Level> currentDim = Objects.requireNonNull(mc.level).dimension();

        return currentDim != OVERWORLD;

    }
}
