package club.iananderson.seasonhud.client;

import net.minecraft.client.Minecraft;
import sereneseasons.api.season.Season;

import static club.iananderson.seasonhud.client.SeasonMinimap.SeasonVal;
import static xaero.common.settings.ModOptions.modMain;

public class SeasonMapSettings {
    Minecraft mc = Minecraft.getInstance();

    public Season currentSeason = SeasonVal();
    public String SEASONNAME = currentSeason.name();
    public String seasonLower = SEASONNAME.toLowerCase();
    public String seasonCap = seasonLower.substring(0,1).toUpperCase()+ seasonLower.substring(1);


    //Values
    public int interfaceSize = (int) mc.getWindow().getGuiScale();
    public int scaledX = mc.getWindow().getGuiScaledWidth();
    public int scaledY = mc.getWindow().getGuiScaledHeight();

    public boolean minimapShape = modMain.getSettings().getMinimap(); //Need to factor in?
    public float minimapScale = modMain.getSettings().getMinimapScale();
    public float mapScale = interfaceSize/minimapScale;

    public int minimapSize = modMain.getSettings().getMinimapSize();
    public int AutoUIScale = modMain.getSettings().getAutoUIScale();
    //int text size = (height of text)*(how many lines)
    //Change math by minimap orientation. Look for variable

    public boolean xBiome = modMain.getSettings().showBiome; // Use for line count x 4
    public boolean xDimensionName = modMain.getSettings().showDimensionName;
    public boolean xCoords = modMain.getSettings().getShowCoords();
    public boolean xAngles = modMain.getSettings().showAngles;

    public int x = (int)((float)scaledX*(minimapScale/mapScale));
    public int y = (int)((float)scaledY*(minimapScale/mapScale));


    public int stringWidth = mc.font.width(seasonCap);
    public int size = (int)((float)(Math.min(y, x)) / minimapScale);

    public int stringX = (int)(scaledX - stringWidth - (Math.sqrt((double)(minimapSize*minimapSize)/2)/mapScale)); //might need to center on x

    private int stringY = 0;
    public int stringY() {
        if(this.stringY > 0){
            return this.stringY;
        } else {
            return (int) ((Math.sqrt((double) (minimapSize * minimapSize) / 3)) / mapScale); //Size looks to be diagonal with x + y being equal.
            //Needs to go down a bit
        }
    }
}
