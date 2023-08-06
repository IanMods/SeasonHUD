package club.iananderson.seasonhud.data;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocale {
    //Currently implemented languages
    public static String getCurrentLocale(){
        Minecraft mc = Minecraft.getInstance();
        return mc.getLanguageManager().getSelected().toLowerCase();
    }
    //Improve later, will work for now
    public static List<String> supportedLanguages(){
        List<String> language = new ArrayList<>();
        language.add("en_au");
        language.add("en_ca");
        language.add("en_gb");
        language.add("en_nz");
        language.add("en_us");
        language.add("english");

        language.add("zh_cn");
        language.add("chinese");
        return language;
    }


}
