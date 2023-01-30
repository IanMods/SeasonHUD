package club.iananderson.seasonhud.impl.sereneseasons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import sereneseasons.api.SSItems;

import static club.iananderson.seasonhud.config.Config.needCalendar;
import static club.iananderson.seasonhud.impl.curios.CuriosCalendar.isInCharmSlot;

public class Calendar {
    public static boolean invCalendar;

    public static Item calendar = SSItems.CALENDAR.get();

    public static boolean calendar() {
        if (needCalendar.get()) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player != null) {
                Inventory inv = player.getInventory();
                int slot = findCalendar(inv, calendar);

                invCalendar = (slot >= 0) || isInCharmSlot(); //TODO crashing when selecting Need Calendar in menu

            }

        }
        else invCalendar = true;

        return invCalendar;
    }

    private static int findCalendar(Inventory inv, Item item) {
        for (int i = 0; i < inv.items.size(); ++i) {
            if ((!inv.items.get(i).isEmpty() && inv.items.get(i).is(item))
                    || (!inv.offhand.get(0).isEmpty() && inv.offhand.get(0).is(item))) {
                return i;
            }
        }
        return -1;
    }

}
