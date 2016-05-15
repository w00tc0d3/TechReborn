package techreborn.reactor.fission;

import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import reborncore.RebornCore;
import techreborn.api.reactor.NeighborStack;
import techreborn.api.reactor.fission.INeutron;
import techreborn.api.reactor.fission.Neutron;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.lib.ModInfo;

public class FuelThorium extends FuelBase implements ITexturedItem {
    private boolean isUranium232 = false;
    private int ticks = 0;

    public FuelThorium() {
        setUnlocalizedName("techreborn.fuelThorium");
        setCreativeTab(TechRebornCreativeTabMisc.instance);
        RebornCore.jsonDestroyer.registerObject(this);
    }

    @Override
    public void update() {
        ticks++;
        if(isUranium232) {
            if(ticks >= 40) {
                ticks = 0;
                isUranium232 = false;
            }
        }
    }

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

    @Override
    public String getTextureName(int i) {
        return "techreborn:items/fuel/fuelThorium";
    }

    @Override
    public int getMaxMeta() {
        return 0;
    }

    @Override
    public ModelResourceLocation getModel(ItemStack itemStack, EntityPlayer entityPlayer, int i) {
        return new ModelResourceLocation(ModInfo.MOD_ID + ":" + getUnlocalizedName(itemStack).substring(5), "inventory");
    }
}
