package club.iananderson.seasonhud.impl.minimaps.journeymap;

import club.iananderson.seasonhud.Common;
import journeymap.api.v2.client.event.InfoSlotDisplayEvent.Position;
import journeymap.api.v2.client.option.BooleanOption;
import journeymap.api.v2.client.option.EnumOption;
import journeymap.api.v2.client.option.OptionCategory;

public class ClientProperties {
  public final BooleanOption addAdditional;
  public final EnumOption<Position> position;
  private final OptionCategory seasonCategory = new OptionCategory(Common.MOD_ID, "key.seasonhud.category",
                                                                   "key.seasonhud.options");

  public ClientProperties() {
    this.addAdditional = new BooleanOption(seasonCategory, "addAdditional", "Add an additional InfoSlot?", true);
    this.position = new EnumOption<>(seasonCategory, "position", "Location of the additional InfoSlot",
                                     Position.Bottom);
  }
}