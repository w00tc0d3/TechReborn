package techreborn.tiles.fissionReactor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import reborncore.api.tile.IInventoryProvider;
import reborncore.common.tile.TileMachineBase;
import reborncore.common.util.Inventory;
import techreborn.api.reactor.NeighborStack;
import techreborn.api.reactor.fission.INeutron;
import techreborn.api.reactor.fission.IReactorFuel;
import techreborn.api.reactor.fission.IReactorModerator;

public class TileEntityFissionController extends TileMachineBase implements ITickable, IInventoryProvider {
    private Inventory inventory = new Inventory(49, "TileEntityFissionController", 1, this);
    private int heat = 0;
    private boolean isActive = false;

    @Override
    public void update() {
        if(worldObj.isRemote || !isActive)
            return;
        int slotNumber = 0;

        for(ItemStack is : inventory.getStacks()) {
            if(is.getItem() instanceof IReactorFuel) {
                IReactorFuel fuel = (IReactorFuel) is.getItem();
                // update fuel rod.
                fuel.update();
                // emit neutrons.
                for(INeutron in : fuel.emitNeutron(is)) {
                    if(NeighborStack.getAdjacentSlotNumber(slotNumber, 7, in.getDirection()) == slotNumber)
                        continue;
                    ItemStack dir_slot = inventory.getStackInSlot(NeighborStack.getAdjacentSlotNumber(slotNumber, 7, in.getDirection()));
                    if(dir_slot.getItem() instanceof IReactorFuel) {
                        ((IReactorFuel) dir_slot.getItem()).hitByNeutron(dir_slot, in);
                    } else if(dir_slot.getItem() instanceof IReactorModerator) {
                        ((IReactorModerator) dir_slot.getItem()).convertNeutron(in);
                    }
                }
                // add heat to reactor
                heat += fuel.emittedHeat(is);
            } //TODO implement moderators
            slotNumber++;
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
