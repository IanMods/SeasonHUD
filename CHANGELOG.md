# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),

## [1.11.1] - 2024-10-16

### Added

- Added Korean translations (via @hibiyasleep)
- (Fabric Seasons Only) Added config option to assign a custom day length value, if using a value
  other than the vanilla 24,000 ticks.
    - Not needed if using SereneSeasons, as it has its own config for day length value.

## [1.11.0] - 2024-10-07

### Added

- Added support for both "Fabric Seasons" and "Serene Seasons" on Fabric.

### Changed

- Slight change to the button layout in the config screen.
- If "Enable Mod" is off, then the other buttons will no longer be interactable.

## [1.10.8] - 2024-09-30

### Fixed

- Fixed crash with FTB Chunks

## [1.10.7] - 2024-09-21

### Fixed

- Fixed the "Calendar Detail Mode" option showing the wrong total days

### Changed

- Changed to allow "Calendar Detail Mode" season/day display mode to be customized like normal.
    - Now, if the option is enabled, it should force just the season name to be displayed when there
      is not a calendar in the player's inventory.
    - If a calendar is found, it will display using the season/day settings set in the
      config/options screen.

## [1.10.6] - 2024-09-15

### Added

- Added a config option to only show the detailed season info if the player has a calendar.

### Changed

- (Xaero's Minimap) Changed the season InfoDisplay to be at the top by default on initial load.
- Cleaned up the config screen a bit.
    - Added a "Season Display Options" sub-screen for customizing the properties of the season
      component.
    - Moved several options into this sub-screen
- Cancel will now properly discard changes made to any of the options.

## [1.10.5] - 2024-09-11

### Changed

- General cleanup and moved more common code.

## [1.10.4] - 2024-09-10

### Changed

- Updated logo with current icons.
- Updated Simplified Chinese translation (via @zrll12)

### Added

- (1.21) Added support JourneyMap's new custom InfoSlots (JourneyMap 1.21-6.0.0-beta.25+ required).
    - By default, "Season" will be added as an InfoSlot option in JourneyMap's minimap settings.
    - If you are already using all the InfoSlots:
        - Go into the JourneyMap addon settings ("Addon Settings" button at the top of the
          JourneyMap
          settings menu).
            - Alternatively, I added a shortcut button in the SeasonHUD config menu (default "H")
              when
              JourneyMap is loaded.
        - Enable the "Add an additional InfoSlot?" option.
        - Choose if you want it added to the top set or the bottom set.

## [1.10.3] - 2024-09-05

### Changed

- Slight cleanup and built against latest version of each minimap mod.

### Fixed

- (1.21.x) Fixed crash with Journeymap 1.21-6.0.0-beta.23

## [1.10.2] - 2024-08-30

### Fixed

- Corrected Fabric version ranges.

### Changed

- Cleaned up season detection.

## [1.10.1] - 2024-08-27

### Changed

- Improved the Accessories support and fixed a crash.

## [1.10.0] - 2024-08-25

### Changed

- Starting with this release, the **1.20.2**, **1.20.4**, and **1.20.6** versions will no longer be
  updated.
    - I updated the **1.16.5** version to be in line with all the current features, and ported it to
      Fabric.
        - Current active versions are: **1.16.5**, **1.18.2**, **1.19.x**, **1.20.1**, **1.21.x**.
- Switched Curios to use a custom Calendar slot instead of Charm.
- Switched Accessories to use a custom Calendar slot instead of Charm.
- Improved detection of curios/trinkets/accessories.
- Switched to Architectury Loom (not the API) for developing

### Added

- Added 1.21.1 support.

### Fixed

- (1.21.x) Fixed the crash with FTB Chunks.
    - Added a custom Minimap Component that can be controlled in the FTB Chunks minimap menu.

## [1.9.8] - 2024-07-28

### Fixed

- Fixed JourneyMap hud appearing when the gui is hidden via F1

## [1.9.7] - 2024-07-26

### Added

- Added custom Trinkets slot for the calendar, rather than using the ring slot
- Added the ability to disable the slot via data pack. See description page for the link to the data
  pack.

## [1.9.6] - 2024-07-22

### Added

- Added Finnish translation (via @N0aW)

## [1.9.5] - 2024-07-21

### Added

- Added Traditional Chinese translation (via @yichifauzi)

## [1.9.4] - 2024-07-19

### Fixed

- Fixed translation key for FTB Chunks menu entry.

## [1.9.3] - 2024-07-16

### Fixed

- Crash when using Accessories mod instead of Curios.

## [1.9.2] - 2024-07-15

### Fixed

- Fixed translation key not being updated in the keybind menu.

## [1.9.1] - 2024-07-12

### Changed

- Updated ru_ru.json translations (via @mpustovoi)

## [1.9.0] - 2024-07-11

### Added

- Added tooltips to the options screen buttons
- Added sliders for the Hud offset in the options screen
    - Right click will set them back to default

### Changed

- Changed the default offsets from 0,0 from 2,2 due to some changes in the offset calculation
    - If you aren't using a minimap mod, you may need to manually change these values if the text
      isn't offset.
- More common code for JourneyMap and MapAtlases to make porting easier.
- Improved logic for detecting hidden minimaps if multiple minimap mods are installed.
- Updated Curios support to more modern implementation.

### Fixed

- Made the season name color sliders not editable and grayed out if the "Season Name Color" option
  is not enabled.
- Fixed the bottom Hud location presets being too low and cutting off text.

## [1.8.9] - 2024-06-28

### Added

- Added a check to ensure the text is hidden if the HideGui option is used in newer versions of
  Minecraft.
    - Included the check in versions before 1.20.6 for consistency.

## [1.8.8] - 2024-06-23

### Fixed

- Fixed version number format for MapAtlases minimum version.

## [1.8.7] - 2024-06-20

### Changed

- Cleaned up the code between different versions a bit.

### Fixed

- (1.21) Fixed crash with new Journeymap update.

## [1.8.6] - 2024-06-15

### Fixed

- Fixed dependency version range for real this time.

## [1.8.5] - 2024-06-15

### Fixed

- Fixed dependency version range.

## [1.8.4] - 2024-06-13

### Added

- Added Spanish (Spain) translation (via @Caapri).

## [1.8.3] - 2024-06-11

### Added

- Added option to color the season text based on the current season.
    - This can be disabled and edited in the menu (Default key H) or through the config.

## [1.8.2] - 2024-05-28

### Fixed

- Fixed crash if installed on a server

## [1.8.1] - 2024-05-27

### Changed

- Updated simplified Chinese translations (via @zrll_)

## [1.8.0] - 2024-05-26

### Changed

- Updated dependencies to the latest versions
- Rewrote a bit to allow for more common code between Forge and Fabric
- (1.20.4 only) Added support for MapAtlases Fabric v2.6.1.
    - This version of Map Atlases is only available on Modrinth currently and is different from the
      multiplat fork.

### Fixed

- (1.20.1) Fixed position with latest version of MapAtlases
- (1.20.6) Fixed the menu title appearing behind the background in the SeasonHUD option screen.

## [1.7.19] - 2024-04-08

### Fixed

- (Forge) Removed the "SHOW_WITH_MONTH" option from showing in the Forge versions config, since it
  is a feature of FabricSeasons.
- Fixed a typo in the config

## [1.7.18] - 2024-04-07

### Changed

- (Fabric) Changed the hud to show up in other dimensions if they are whitelisted in the
  FabricSeasons config file.

## [1.7.17] - 2024-04-02

### Added

- Added local time season support from FabricSeasons (via @Piecuuu)

## [1.7.16] - 2024-03-22

### Added

- Added an option disable minimap integration and continue to use the default hud option.

## [1.7.15] - 2024-03-19

### Changed

- Built against Xaero's Minimap 24.x.x and set that as the minimum version.

### Fixed

- Fixed the Fabric versions icon pointing to the wrong file for like a year now.
- Fixed the "enableMod" option not working correctly.

## [1.7.14] - 2024-03-10

### Added

- Added Portuguese translation (provided by @t0piy)

## [1.7.13] - 2024-02-18

### Added

- Added an option to display the total days in the current season/sub-season.

### Fixed

- Fixed tropical seasons displaying incorrectly when "Show Sub-Season" was off.
- Fixed calculation for the current tropical season day to account for changes in the Serene Seasons
  config.

## [1.7.12] - 2024-02-14

### Fixed

- Fixed season still displaying when Journeymap is hidden

## [1.7.11] - 2024-01-30

### Fixed

- Fixed the season text drawing while using a spyglass

## [1.7.10] - 2024-01-29

### Changed

- (1.20.4) Switched to Serene Seasons for Fabric, and dropped support for FabricSeasons since it has
  not been updated in some time.
- (1.20.4) Added support for the NeoForge version of Serene Seasons
- Now uses the whitelisted dimensions in the SereneSeason config to determine if seasons are active
  in a dimension, and if the hud should be applied

## [1.7.9] - 2024-01-25

### Added

- Updated zn_cn translation (by zrll12)

## [1.7.8] - 2024-01-07

### Fixed

- Fixed issue with scaling above 1 with MapAtlases

## [1.7.7] - 2023-12-29

### Changed

- Made the config file more organized. You may need to reapply any changes you made to it
  previously.

### Fixed

- Fixed crash with latest version of MapAtlases

## [1.7.6] - 2023-12-26

### Fixed

- Fixed "Show when minimap is hidden" setting not working correctly with MapAtlases

## [1.7.5] - 2023-12-25

### Fixed

- Fixed tropical season icons not displaying correctly

## [1.7.4] - 2023-12-25

### Added

- Added a custom spacing font to reduce distance between the icon and season name

## [1.7.3] - 2023-12-24

### Added

- Added a toggle in the settings menu/config, to correct the scaling on a retina display on macOS

## [1.7.2] - 2023-12-23

### Changed

- Changed the FTBChunks implementation to prevent crashes
- Rewrote the Journeymap implementation

## [1.7.1] - 2023-12-21

### Fixed

- Made Mixin config more compatible to prevent crashes

## [1.7.0] - 2023-12-19

### Changed

- Redid how icons are handled. Should always be lined up with the text properly now
- Edited the season icons, so they are an odd amount of pixels, so that they can be properly
  centered on the text
- Made a new winter icon since I couldn't get the snowflake to look right with an odd amount of
  pixels

## [1.6.4] - 2023-12-08

### Changed

- (1.20.2) Updated to autumn icon to the new design from SereneSeasons
- Added SereneSeasons as a required dependency on Mondrinth, now that is uploaded there as well
- Slight cleanup

## [1.6.3] - 2023-12-03

### Changed

- (1.19.2) Rewrote the options screen to allow for the 1.19.2 version to be used in 1.19.4 as well
- (1.19.x) Going forward, only one 1.19 version will be uploaded and will be marked as compatible
  with both versions
- Rewrote some code to allow for more common classes to be used
- Ported 1.19+ changes to 1.18.2 and made a Fabric version as well

## [1.6.2] - 2023-11-26

### Fixed

- Fixed misalignment with latest version of MapAtlases

## [1.6.1] - 2023-11-18

### Fixed

- Fixed Trinkets tag error when FabricSeason's Extra isn't installed

## [1.6.0] - 2023-11-15

### Added

- Added support for the MapAtlases mod

### Changed

- Changed Mixins to only attempt to load if the appropriate minimap mod is loaded

### Fixed

- Fixed icon alignment if Xaero's Minimap size is chunk coordinate size is wider than the minimap
  size
- Fixed icon alignment when using the Enlarged Minimap feature in Xaero's Minimap
- Fixed icon alignment if using OpenPartiesAndClaims, and the claim name splits into more than one
  line.

## [1.5.13] - 2023-11-01

### Fixed

- Adjusted math behind icon position
- (Xaero's Minimap) Fixed icon adjusting when hidden potion effects are active

## [1.5.12] - 2023-10-22

### Fixed

- Fixed a potential error with season names, if system language is not english

## [1.5.11] - 2023-10-18

### Fixed

- Fixed background transparency to match the JourneyMap setting

## [1.5.10] - 2023-10-09

### Fixed

- Fixed layering issue with newest JourneyMap

## [1.5.9] - 2023-09-30

### Added

- (1.20.2) Ported to 1.20.2

### Fixed

- Fixed season icon being misaligned when potion effects shift Xaero's Minimap

## [1.5.8] - 2023-09-19

### Fixed

- Fixed issue with the config screen when using InvMove

## [1.5.7] - 2023-09-18

### Added

- Ported changes to localization made in 1.16.5 version

### Fixed

- Fixed incorrect calendar item name for FabricSeasons Extras
- Fixed issue with the config screen when using InvMove

## [1.5.6] - 2023-09-07

### Changed

- Removed requirement of FabricSeasons Extras and made it optional

## [1.5.5] - 2023-09-04

### Added

- Added Russian translation provided by mpustovoi

## [1.5.4] - 2023-08-27

### Fixed

- Fixed Fall displaying incorrectly in the Fabric Version

## [1.5.3] - 2023-08-16

### Added

- Added JourneyMap support for Fabric

### Fixed

- Fixed label placement when using JourneyMap

## [1.5.2] - 2023-08-16

### Fixed

- Fixed Simplified Chinese

## [1.5.1] - 2023-08-05

### Added

- 1.20 update
- (Fabric) Updated to latest FabricSeasons
- (Fabric) Added FabricSeasons Extras as a required dependency, as the Calendar item was moved there

## [1.5.0] - 2023-08-02

### Added

- Added ability to change position of the season text when using Xaero's Minimap
    - Minimap Menu ('Y' by default) -> Information Settings -> Info Display Manager

## [1.4.19] - 2023-06-29

### Added

- Added 1.20.1 support

### Fixed

- Fixed crash with latest version of FTBChunks

## [1.4.18] - 2023-06-03

### Added

- Added 1.20 support
- Added an option to display the default SeasonHUD overlay if the minimap is hidden

### Fixed

- Will now disable if Journeymap is hidden

## [1.4.17] - 2023-06-02

### Fixed

- Will now disable if Xaero's Minimap is hidden

## [1.4.16] - 2023-05-24

### Added

- Added support for handling custom sub-season days

## [1.4.15] - 2023-05-16

### Fixed

- Fixed localization spacing

## [1.4.14] - 2023-04-27

### Fixed

- Overlapping with Open Parties and Claims

## [1.4.13] - 2023-04-26

### Fixed

- Fixed the HUD staying rendered while inventories are open, while using Journeymap or no minimap

## [1.4.12] - 2023-04-18

### Fixed

- Fixed background color of Journeymap labels if the theme is changed

## [1.4.11] - 2023-04-18

### Fixed

- The season HUD not showing up while using FTBChunks on a server

## [1.4.10] - 2023-04-05

### Fixed

- Fixed alignment issue with JourneyMap on the bottom half the screen

### Added

- Added config option to display above the JourneyMap minimap

## [1.4.9] - 2023-04-02

### Changed

- Slight code cleanup

## [1.4.8] - 2023-03-15

### Changed

- Re-enabled Xaero's Minimap Fairplay support
- Updated to support the latest version of Xaero's Minimap Fairplay

## [1.4.7] - 2023-03-02

### Changed

- Updated to the release version of Serene Seasons

# [1.4.6] - 2023-02-20

### Changed

- Slight code cleanup
- Xaero's Minimap Fairplay still hasn't been updated to match the regular version, so continue
  using [1.4.4] if using it

## [1.4.5] - 2023-02-12

### Fixed

- Fixed crash with Xaero's Minimap v23.1.0, due to the info display changes
- Temporarily disabled Fairplay functionality, until it is updated with those same changes

## [1.4.4] - 2023-02-04

### Added

- If the player is not in the overworld, the HUD will disable itself.

### Changed

- Some slight code cleanup.

## [1.4.3] - 2023-02-03

### Added

- Added a config option to show normal seasons in tropical biomes

## [1.4.2] - 2023-02-02

### Fixed

- Attempt #2 of fixing UI overlap in a claimed chunk

## [1.4.1] - 2023-02-02

### Fixed

- Fixed UI overlap if in a claimed chunk while using FTB Chunks

## [1.4.0] - 2023-01-30

### Added

- Added optional support for CuriosAPI
    - If CuriosAPI is loaded, then the Calendar item from SereneSeason can be equipped in the "
      Charm" slot
    - If the "Need Calendar" option is enabled, then a Calendar in the "Charm" slot will meet the
      requirement

## [1.3.6] - 2023-01-21

### Changed

- Some code cleanup

## [1.3.5] - 2023-01-20

### Added

- Added support for Xaero's Minimap Fair-play

## [1.3.4] - 2023-01-19

### Added

- Added an option to show the HUD only if the player has the Calendar item in their inventory

## [1.3.3] - 2023-01-11

### Added

- Added config option with presets for HUD location (when no minimap is present)
- Added button in config screen for HUD location

## [1.3.2] - 2023-01-10

### Added

- Added config options to offset the default HUD

### Changed

- Built against latest recommended Forge version

## [1.3.1] - 2023-01-06

### Added

- Added an option to disable the mod ingame.

### Changed

- Slight code cleanup

## [1.3.0] - 2022-12-29

### Added

- Added am in-game config screen and a keybind for it
    - Default key is 'H'

### Changed

- Due to some changes from 1.19.2 -> 1.19.3, I had to change to separate builds for each, rather
  than a unified version
- Fixed an issue with tropical season dates. Should be accurate now