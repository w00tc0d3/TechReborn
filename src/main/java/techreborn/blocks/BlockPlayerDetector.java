package techreborn.blocks;

import me.modmuss50.jsonDestroyer.api.ITexturedBlock;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.util.ChatUtils;
import techreborn.client.TechRebornCreativeTab;
import techreborn.lib.MessageIDs;
import techreborn.tiles.TilePlayerDectector;

import java.util.List;
import java.util.Random;

public class BlockPlayerDetector extends BlockMachineBase implements ITexturedBlock
{

	public static final String[] types = new String[] { "all", "others", "you" };
	public PropertyInteger METADATA;

	public BlockPlayerDetector()
	{
		super(true);
		setUnlocalizedName("techreborn.playerDetector");
		setCreativeTab(TechRebornCreativeTab.instance);
		setHardness(2f);
		this.setDefaultState(this.getDefaultState().withProperty(METADATA, 0));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int meta = 0; meta < types.length; meta++)
		{
			list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TilePlayerDectector();
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		TileEntity entity = blockAccess.getTileEntity(pos);
		if (entity instanceof TilePlayerDectector)
		{
			return ((TilePlayerDectector) entity).isProvidingPower() ? 15 : 0;
		}
		return 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		TileEntity entity = blockAccess.getTileEntity(pos);
		if (entity instanceof TilePlayerDectector)
		{
			return ((TilePlayerDectector) entity).isProvidingPower() ? 15 : 0;
		}
		return 0;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TilePlayerDectector)
		{
			((TilePlayerDectector) tile).owenerUdid = placer.getUniqueID().toString();
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityPlayer,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		// int newMeta = (world.getBlockMetadata(x, y, z) + 1) % 3;
		int newMeta = getMetaFromState(state);
		String message = "";
		switch (newMeta)
		{
			case 0:
				message = TextFormatting.GREEN + I18n.translateToLocal("techreborn.message.allPlayers");
				break;
			case 1:
				message = TextFormatting.RED + I18n.translateToLocal("techreborn.message.onlyOtherPlayers");
				break;
			case 2:
				message = TextFormatting.BLUE + I18n.translateToLocal("techreborn.message.onlyYou");
		}
		if (!world.isRemote)
		{
			ChatUtils.sendNoSpamMessages(MessageIDs.playerDetectorID, new TextComponentString(
					TextFormatting.GRAY + I18n.translateToLocal("techreborn.message.detects") + " "
							+ message));
			// world.setBlockMetadataWithNotify(x, y, z, newMeta, 2);
		}
		return true;
	}

	@Override
	public String getTextureNameFromState(IBlockState blockState, EnumFacing facing)
	{
		return "techreborn:blocks/machine/greg_machines/player_detector_" + types[getMetaFromState(blockState)];
	}

	@Override
	public int amountOfStates()
	{
		return types.length;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(METADATA);
	}

	protected BlockStateContainer createBlockState()
	{
		METADATA = PropertyInteger.create("type", 0, types.length - 1);
		return new BlockStateContainer(this, METADATA);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(METADATA, meta);
	}
}
