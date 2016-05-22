package techreborn.tiles.fissionReactor;

import reborncore.api.tile.IInventoryProvider;
import reborncore.common.multiblock.MultiblockValidationException;
import reborncore.common.util.Inventory;
import techreborn.multiblocks.MultiBlockFissionReactor;

public class TileEntityFissionReactorInvHandler extends TileEntityFissionReactorPart implements IInventoryProvider {
    @Override
    public void isGoodForFrame() throws MultiblockValidationException {
        throw new MultiblockValidationException("Inventory Handlers are not valid as frame blocks.");
    }

    @Override
    public void isGoodForSides() throws MultiblockValidationException {

    }

    @Override
    public void isGoodForTop() throws MultiblockValidationException {

    }

    @Override
    public void isGoodForBottom() throws MultiblockValidationException {

    }

    @Override
    public void isGoodForInterior() throws MultiblockValidationException {
        throw new MultiblockValidationException("Inventory Handlers are not valid as interior blocks.");
    }

    @Override
    public Inventory getInventory() {
        return ((MultiBlockFissionReactor) getMultiblockController()).getInventory();
    }
}
