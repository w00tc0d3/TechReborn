package techreborn.tiles.fissionReactor;

import reborncore.common.multiblock.MultiblockValidationException;

public class TileEntityFissionReactorInterior extends TileEntityFissionReactorPart {
    @Override
    public void isGoodForFrame() throws MultiblockValidationException {
        throw new MultiblockValidationException("Inventory Handlers are not valid as frame blocks.");
    }

    @Override
    public void isGoodForSides() throws MultiblockValidationException {
        throw new MultiblockValidationException("Inventory Handlers are not valid as frame blocks.");
    }

    @Override
    public void isGoodForTop() throws MultiblockValidationException {
        throw new MultiblockValidationException("Inventory Handlers are not valid as frame blocks.");
    }

    @Override
    public void isGoodForBottom() throws MultiblockValidationException {
        throw new MultiblockValidationException("Inventory Handlers are not valid as frame blocks.");
    }

    @Override
    public void isGoodForInterior() throws MultiblockValidationException {

    }
}
