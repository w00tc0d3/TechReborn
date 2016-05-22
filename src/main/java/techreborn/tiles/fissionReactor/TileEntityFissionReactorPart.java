package techreborn.tiles.fissionReactor;

import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.MultiblockValidationException;
import reborncore.common.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import techreborn.multiblocks.MultiBlockFissionReactor;

public abstract class TileEntityFissionReactorPart extends RectangularMultiblockTileEntityBase {

    @Override
    public void onMachineActivated() {

    }

    @Override
    public void onMachineDeactivated() {

    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new MultiBlockFissionReactor(this.worldObj);
    }

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
        return MultiBlockFissionReactor.class;
    }

    @Override
    public void update() {

    }
}
