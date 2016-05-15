package techreborn.reactor.fission;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import techreborn.api.reactor.fission.IReactorFuel;

public abstract class FuelBase extends Item implements IReactorFuel {
    protected int emittedNeutrons = 0;
    protected int absorbedNeutrons = 0;

    public FuelBase() {
        this.setMaxStackSize(1);
    }

    protected void writeChangesToNBT(ItemStack itemStack) {
        getTagCompound(itemStack).setInteger("emittedNeutrons", emittedNeutrons);
        getTagCompound(itemStack).setInteger("absorbedNeutrons", absorbedNeutrons);
    }

    protected void readInfoFromNBT(ItemStack itemStack) {
        emittedNeutrons = getTagCompound(itemStack).getInteger("emittedNeutrons");
        absorbedNeutrons = getTagCompound(itemStack).getInteger("absorbedNeutrons");
    }

    //TODO: move this to RebornCore (base class for items).
    protected NBTTagCompound getTagCompound(ItemStack itemStack) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if(nbt != null)
            return nbt;
        nbt = new NBTTagCompound();
        itemStack.setTagCompound(nbt);
        return nbt;
    }
}
