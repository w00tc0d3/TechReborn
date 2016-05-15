package techreborn;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.blocks.IAdvancedRotationTexture;
import techreborn.client.GuiHandler;
import techreborn.client.TechRebornCreativeTab;
import techreborn.tiles.fusionReactor.TileEntityFusionController;
import techreborn.utils.damageSources.FusionDamageSource;

public class BlockFissionControlComputer extends BlockMachineBase implements IAdvancedRotationTexture {
    private final String prefix = "techreborn:blocks/machine/fission/";

    public BlockFissionControlComputer(Material material)
    {
        super();
        setUnlocalizedName("techreborn.fissioncontrolcomputer");
        setCreativeTab(TechRebornCreativeTab.instance);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
                                    float hitY, float hitZ)
    {
        TileEntityFissionController tileEntityFissionController = (TileEntityFissionController) world
                .getTileEntity(new BlockPos(x, y, z));
        tileEntityFissionController.checkCoils();
        if (!player.isSneaking())
            player.openGui(Core.INSTANCE, GuiHandler.fissionID, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityFissionController();
    }

    @Override
    public String getFront(boolean isActive) {
        return prefix + "fission_control_computer_front";
    }

    @Override
    public String getSide(boolean isActive) {
        return prefix + "machine_side";
    }

    @Override
    public String getTop(boolean isActive) {
        return prefix + "machine_side";
    }

    @Override
    public String getBottom(boolean isActive) {
        return prefix + "machine_side";
    }
}
