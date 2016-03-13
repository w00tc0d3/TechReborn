package techreborn.blocks;

import me.modmuss50.jsonDestroyer.api.ITexturedBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.common.BaseBlock;
import techreborn.client.TechRebornCreativeTabMisc;

public class BlockReinforcedGlass extends BaseBlock implements ITexturedBlock {
	
	public BlockReinforcedGlass(Material materialIn) {
		super(materialIn);
		setUnlocalizedName("techreborn.reinforcedglass");
		setCreativeTab(TechRebornCreativeTabMisc.instance);
		setHardness(4.0F);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	public boolean isFullCube()
	{
		return false;
	}
	
    private final String prefix = "techreborn:blocks/machine/";

	@Override
	public int amountOfStates() {
		return 1;
	}

	@Override
	public String getTextureNameFromState(IBlockState arg0, EnumFacing arg1) {
		return prefix + "reinforcedglass";
	}
	
}