package club.iananderson.seasonhud.client.gui.components.buttons;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class CycleButton<T> extends AbstractButton implements TooltipAccessor {
  static final BooleanSupplier DEFAULT_ALT_LIST_SELECTOR = Screen::hasAltDown;
  private static final List<Boolean> BOOLEAN_OPTIONS;

  static {
    BOOLEAN_OPTIONS = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
  }

  private final Component name;
  private final ValueListSupplier<T> values;
  private final Function<T, Component> valueStringifier;
  private final OnValueChange<T> onValueChange;
  private final TooltipSupplier<T> tooltipSupplier;
  private final boolean displayOnlyValue;
  private int index;
  private T value;

  CycleButton(int i, int j, int k, int l, Component component, Component component2, int m, T object,
      ValueListSupplier<T> valueListSupplier, Function<T, Component> function, OnValueChange<T> onValueChange,
      TooltipSupplier<T> tooltipSupplier, boolean bl) {
    super(i, j, k, l, component);
    this.name = component2;
    this.index = m;
    this.value = object;
    this.values = valueListSupplier;
    this.valueStringifier = function;
    this.onValueChange = onValueChange;
    this.tooltipSupplier = tooltipSupplier;
    this.displayOnlyValue = bl;
  }

  public static MutableComponent optionNameValue(Component component, Component component2) {
    return new TranslatableComponent("options.generic_value", component, component2);
  }

  public static <T> Builder<T> builder(Function<T, Component> function) {
    return new Builder(function);
  }

  public static Builder<Boolean> onOffBuilder() {
    return (new Builder(
        (boolean_) -> (Boolean) boolean_ ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF)).withValues(
        BOOLEAN_OPTIONS);
  }

  public static Builder<Boolean> onOffBuilder(boolean bl) {
    return onOffBuilder().withInitialValue(bl);
  }

  public void onPress() {
    if (Screen.hasShiftDown()) {
      this.cycleValue(-1);
    } else {
      this.cycleValue(1);
    }

  }

  private void cycleValue(int i) {
    List<T> list = this.values.getSelectedList();
    this.index = Mth.positiveModulo(this.index + i, list.size());
    T object = list.get(this.index);
    this.updateValue(object);
    this.onValueChange.onValueChange(this, object);
  }

  private T getCycledValue(int i) {
    List<T> list = this.values.getSelectedList();
    return list.get(Mth.positiveModulo(this.index + i, list.size()));
  }

  public boolean mouseScrolled(double d, double e, double f) {
    if (f > 0.0) {
      this.cycleValue(-1);
    } else if (f < 0.0) {
      this.cycleValue(1);
    }

    return true;
  }

  private void updateValue(T object) {
    Component component = this.createLabelForValue(object);
    this.setMessage(component);
    this.value = object;
  }

  private Component createLabelForValue(T object) {
    return (Component) (this.displayOnlyValue ? (Component) this.valueStringifier.apply(object)
                                              : this.createFullName(object));
  }

  private MutableComponent createFullName(T object) {
    return optionNameValue(this.name, (Component) this.valueStringifier.apply(object));
  }

  public T getValue() {
    return this.value;
  }

  public void setValue(T object) {
    List<T> list = this.values.getSelectedList();
    int i = list.indexOf(object);
    if (i != -1) {
      this.index = i;
    }

    this.updateValue(object);
  }

  @Override
  public Optional<List<FormattedCharSequence>> getTooltip() {
    return Optional.empty();
  }

  private interface ValueListSupplier<T> {
    static <T> ValueListSupplier<T> create(List<T> list) {
      final List<T> list2 = ImmutableList.copyOf(list);
      return new ValueListSupplier<T>() {
        public List<T> getSelectedList() {
          return list2;
        }

        public List<T> getDefaultList() {
          return list2;
        }
      };
    }

    static <T> ValueListSupplier<T> create(final BooleanSupplier booleanSupplier, List<T> list, List<T> list2) {
      final List<T> list3 = ImmutableList.copyOf(list);
      final List<T> list4 = ImmutableList.copyOf(list2);
      return new ValueListSupplier<T>() {
        public List<T> getSelectedList() {
          return booleanSupplier.getAsBoolean() ? list4 : list3;
        }

        public List<T> getDefaultList() {
          return list3;
        }
      };
    }

    List<T> getSelectedList();

    List<T> getDefaultList();
  }

  public interface OnValueChange<T> {
    void onValueChange(CycleButton cycleButton, T object);
  }

  public interface TooltipSupplier<T> extends Function<T, List<FormattedCharSequence>> {
  }

  public static class Builder<T> {
    private final Function<T, Component> valueStringifier;
    private int initialIndex;
    @Nullable
    private T initialValue;
    private TooltipSupplier<T> tooltipSupplier = (object) -> {
      return ImmutableList.of();
    };
    private ValueListSupplier<T> values = CycleButton.ValueListSupplier.create(ImmutableList.of());
    private boolean displayOnlyValue;

    public Builder(Function<T, Component> function) {
      this.valueStringifier = function;
    }

    public Builder<T> withValues(List<T> list) {
      this.values = CycleButton.ValueListSupplier.create(list);
      return this;
    }

    public final Builder<T> withValues(T... objects) {
      return this.withValues(ImmutableList.copyOf(objects));
    }

    public Builder<T> withValues(List<T> list, List<T> list2) {
      this.values = CycleButton.ValueListSupplier.create(CycleButton.DEFAULT_ALT_LIST_SELECTOR, list, list2);
      return this;
    }

    public Builder<T> withValues(BooleanSupplier booleanSupplier, List<T> list, List<T> list2) {
      this.values = CycleButton.ValueListSupplier.create(booleanSupplier, list, list2);
      return this;
    }

    public Builder<T> withTooltip(TooltipSupplier<T> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public Builder<T> withInitialValue(T object) {
      this.initialValue = object;
      int i = this.values.getDefaultList().indexOf(object);
      if (i != -1) {
        this.initialIndex = i;
      }

      return this;
    }

    public Builder<T> displayOnlyValue() {
      this.displayOnlyValue = true;
      return this;
    }

    public CycleButton<T> create(int i, int j, int k, int l, Component component) {
      return this.create(i, j, k, l, component, (cycleButton, object) -> {
      });
    }

    public CycleButton<T> create(int i, int j, int k, int l, Component component, OnValueChange<T> onValueChange) {
      List<T> list = this.values.getDefaultList();
      if (list.isEmpty()) {
        throw new IllegalStateException("No values for cycle button");
      } else {
        T object = this.initialValue != null ? this.initialValue : list.get(this.initialIndex);
        Component component2 = (Component) this.valueStringifier.apply(object);
        Component component3 = this.displayOnlyValue ? component2 : optionNameValue(component, component2);
        return new CycleButton(i, j, k, l, (Component) component3, component, this.initialIndex, object, this.values,
                               this.valueStringifier, onValueChange, this.tooltipSupplier, this.displayOnlyValue);
      }
    }
  }
}