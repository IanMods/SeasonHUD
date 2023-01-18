package club.iananderson.seasonhud.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import sereneseasons.api.SSItems;


public class Calendar {
    public Calendar(){
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if(player != null){
            Inventory inv = player.getInventory();
            int slot = findCalendar(inv, SSItems.CALENDAR.get());

            boolean invCalendar = (slot >= 0);
        }

    }
    private int findCalendar(Inventory inv, Item calendar) {
        for(int i = 0; i < inv.items.size(); ++i) {
            if (!inv.items.get(i).isEmpty() && inv.items.get(i).is(calendar)) {
                return i;
            }
        }
        return -1;
    }
}

