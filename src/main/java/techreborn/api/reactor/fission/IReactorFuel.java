package techreborn.api.reactor.fission;

import net.minecraft.item.ItemStack;

/* Implement this to add reactor fuel. Recommended to extend the Item class. */
public interface IReactorFuel {
    /* Should be called by the reactor every tick. */
    void update();

    /* Should be called by the reactor whenever the fuel gets hit by a neutron. */
    void hitByNeutron(ItemStack stack, INeutron neutron);

    /* Should be called by the reactor to determine the thermal energy to be added to the coolant. */
    int emittedHeat(ItemStack stack);

    /* Should be called by the reactor whenever the fuel should emit (a) neutron(s). */
    INeutron[] emitNeutron(ItemStack stack);
}
