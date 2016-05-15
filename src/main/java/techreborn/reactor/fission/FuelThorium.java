package techreborn.reactor.fission;

import net.minecraft.item.ItemStack;
import techreborn.api.reactor.NeighborStack;
import techreborn.api.reactor.fission.INeutron;
import techreborn.api.reactor.fission.Neutron;

public class FuelThorium extends FuelBase {
    private boolean isUranium232 = false;

    @Override
    public void hitByNeutron(ItemStack stack, INeutron neutron) {
        readInfoFromNBT(stack);
        if(absorbedNeutrons <= 3) {
            absorbedNeutrons++;
            writeChangesToNBT(stack);
            return;
        }
        // absorbedNeutrons == 4
        if(!isUranium232) {
            isUranium232 = true;
            writeChangesToNBT(stack);
        }
    }

    @Override
    public int emittedHeat(ItemStack stack) {
        readInfoFromNBT(stack);
        if(!isUranium232)
            return 0;
        return 100;
    }

    @Override
    public INeutron[] emitNeutron(ItemStack stack) {
        readInfoFromNBT(stack);
        if(!isUranium232)
            return null;
        boolean rng_fast[] = {
                Math.random() < 0.5D,
                Math.random() < 0.5D,
                Math.random() < 0.5D };
        int rng_dir[] = { (int) (Math.random()*7), (int) (Math.random()*7), (int) (Math.random()*7) };
        return new Neutron[] {
            new Neutron(rng_fast[0], NeighborStack.convertNumberToDirection(rng_dir[0])),
            new Neutron(rng_fast[1], NeighborStack.convertNumberToDirection(rng_dir[1])),
            new Neutron(rng_fast[2], NeighborStack.convertNumberToDirection(rng_dir[2]))
        };
    }


    @Override
    protected void writeChangesToNBT(ItemStack itemStack) {
        super.writeChangesToNBT(itemStack);
        getTagCompound(itemStack).setBoolean("isUranium232", isUranium232);
    }

    @Override
    protected void readInfoFromNBT(ItemStack itemStack) {
        super.readInfoFromNBT(itemStack);
        isUranium232 = getTagCompound(itemStack).getBoolean("isUranium232");
    }
}
