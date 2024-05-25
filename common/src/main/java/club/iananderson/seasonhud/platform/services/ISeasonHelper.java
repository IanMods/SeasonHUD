package club.iananderson.seasonhud.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface ISeasonHelper {
	/**
	 * Checks if the tropical season should be displayed (SereneSeasons only).
	 * Always false for FabricSeasons.
	 *
	 * @return If the tropical season should be displayed for the platform.
	 */
	boolean isTropicalSeason();

	/**
	 * Checks if "isSeasonTiedWithSystemTime" config option is enabled (FabricSeasons only).
	 * Always false for SereneSeasons.
	 *
	 * @return If "isSeasonTiedWithSystemTime" config option is enabled for the platform.
	 */
	boolean isSeasonTiedWithSystemTime();

	/**
	 * Gets the name of the current season for the platform.
	 *
	 * @return The name of the current season for the platform.
	 */
	String getCurrentSeasonState();

	/**
	 * Gets the current season's file name for the platform.
	 *
	 * @return The current season's file name for the platform.
	 */
	String getSeasonFileName();

	/**
	 * Gets the current day of the season/sub-season.
	 *
	 * @return The current day of the season/sub-season.
	 */
	int getDate();

	/**
	 * Checks the duration of the current season/sub-season.
	 *
	 * @return The duration of the current season/sub-season.
	 */
	int seasonDuration();

	/**
	 * @return The calendar item for the loaded season mod.
	 */
	Item calendar();

	/**
	 * @param player The player whose Curios/Trinket inventory will be searched.
	 * @param item The item that is being searched for.
	 *
	 * @return The int for the Curios/Trinket inventory location
	 */
	int findCuriosCalendar(Player player, Item item);
}
