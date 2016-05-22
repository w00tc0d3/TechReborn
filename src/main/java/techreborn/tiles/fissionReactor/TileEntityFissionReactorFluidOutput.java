package techreborn.tiles.fissionReactor;

import reborncore.common.multiblock.MultiblockValidationException;

public class TileEntityFissionReactorFluidOutput extends TileEntityFissionReactorPart {
    @Override
    public void isGoodForFrame() throws MultiblockValidationException {
        throw new MultiblockValidationException("Fluid Outputs are not valid as frame blocks.");
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
        throw new MultiblockValidationException("Fluid Outputs are not valid as interior blocks.");
    }
}
