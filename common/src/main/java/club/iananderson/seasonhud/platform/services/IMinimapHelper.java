package club.iananderson.seasonhud.platform.services;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimaps;

public interface IMinimapHelper {

  /**
   * Checks if the current dimension is whitelisted in the season mod's config.
   *
   * @return True if the current dimension is whitelisted in the season mod's config.
   */
  boolean hideHudInCurrentDimension();

  /**
   * Checks if the provided minimap mod has the minimap hidden
   *
   * @return True if the current minimap mod has the minimap hidden
   */
  boolean hiddenMinimap(Minimaps minimap);

  boolean allMinimapsHidden();
}