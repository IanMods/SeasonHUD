package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class Calendar {
    public static final boolean CALENDAR_LOADED = (Services.PLATFORM.getPlatformName().equals("Forge") || (
            Services.PLATFORM.getPlatformName().equals("Fabric") && Common.extrasLoaded()));

    private Calendar() {
    }

    public static boolean calendarFound() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (Config.getNeedCalendar() && CALENDAR_LOADED && player != null) {
            Inventory inv = player.inventory;
            ItemStack calendar = Services.SEASON.calendar();
            int slot = findCalendar(inv, calendar) + Services.SEASON.findCuriosCalendar(player, calendar);

            return (slot >= 0);
        } else {
            return true;
        }
    }

    private static int findCalendar(Inventory inv, ItemStack item) {
        for (int i = 0; i < inv.items.size(); ++i) {
            if ((!inv.items.get(i).isEmpty() && inv.items.get(i).sameItem(item)) || (!inv.offhand.get(0).isEmpty()
                    && inv.offhand.get(0).sameItem(item))) {
                return i;
            }
        }
        return -1;
    }
}