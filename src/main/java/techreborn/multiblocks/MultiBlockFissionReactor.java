package techreborn.multiblocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import reborncore.api.tile.IInventoryProvider;
import reborncore.common.multiblock.IMultiblockPart;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.rectangular.RectangularMultiblockControllerBase;
import reborncore.common.util.Inventory;
import techreborn.api.reactor.NeighborStack;
import techreborn.api.reactor.fission.INeutron;
import techreborn.api.reactor.fission.IReactorFuel;
import techreborn.api.reactor.fission.IReactorModerator;
import techreborn.tiles.fissionReactor.*;

import java.util.ArrayList;

public class MultiBlockFissionReactor extends RectangularMultiblockControllerBase implements IInventoryProvider {
    private boolean isActive = false;
    private Inventory inventory;
    private int heat = 0;

    private TileEntityFissionReactorController attachedReactorController;
    private ArrayList<TileEntityFissionReactorInvHandler> attachedInvHandlers = new ArrayList<>();
    private ArrayList<TileEntityFissionReactorEnergyPort> attachedEnergyPorts = new ArrayList<>();
    private ArrayList<TileEntityFissionReactorFluidInput> attachedFluidInputs = new ArrayList<>();
    private ArrayList<TileEntityFissionReactorFluidOutput> attachedFluidOutputs = new ArrayList<>();

    public MultiBlockFissionReactor(World world) {
        super(world);
        TileEntity te = world.getTileEntity(getReferenceCoord().toBlockPos());
        if(te != null)
            inventory = new Inventory(49, "MultiBlockFissionReactor", 1, te);
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {

    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        if(newPart instanceof TileEntityFissionReactorController)
            attachedReactorController = (TileEntityFissionReactorController) newPart;
        else if(newPart instanceof TileEntityFissionReactorEnergyPort)
            attachedEnergyPorts.add((TileEntityFissionReactorEnergyPort) newPart);
        else if(newPart instanceof TileEntityFissionReactorInvHandler)
            attachedInvHandlers.add((TileEntityFissionReactorInvHandler) newPart);
        else if(newPart instanceof TileEntityFissionReactorFluidInput)
            attachedFluidInputs.add((TileEntityFissionReactorFluidInput) newPart);
        else if(newPart instanceof TileEntityFissionReactorFluidOutput)
            attachedFluidOutputs.add((TileEntityFissionReactorFluidOutput) newPart);
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart) {
    }

    @Override
    protected void onMachineAssembled() {

    }

    @Override
    protected void onMachineRestored() {

    }

    @Override
    protected void onMachinePaused() {

    }

    @Override
    protected void onMachineDisassembled() {

    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 125;
    }

    @Override
    protected int getMaximumXSize() {
        return 5;
    }

    @Override
    protected int getMaximumZSize() {
        return 5;
    }

    @Override
    protected int getMaximumYSize() {
        return 5;
    }

    @Override
    protected void onAssimilate(MultiblockControllerBase assimilated) {

    }

    @Override
    protected void onAssimilated(MultiblockControllerBase assimilator) {

    }

    @Override
    protected boolean updateServer() {
        if(worldObj.isRemote || !isActive)
            return false;
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
        return true;
    }

    @Override
    protected void updateClient() {

    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        data.setInteger("heat", heat);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        heat = data.getInteger("heat");
    }

    @Override
    public void formatDescriptionPacket(NBTTagCompound data) {
        writeToNBT(data);
    }

    @Override
    public void decodeDescriptionPacket(NBTTagCompound data) {
        readFromNBT(data);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
