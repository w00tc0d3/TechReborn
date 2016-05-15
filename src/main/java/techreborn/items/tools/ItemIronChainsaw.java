package techreborn.items.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import techreborn.config.ConfigTechReborn;

import net.minecraft.item.Item.ToolMaterial;
public class ItemIronChainsaw extends ItemChainsaw
{

	public ItemIronChainsaw()
	{
		super(ToolMaterial.IRON, "techreborn.ironChainsaw", ConfigTechReborn.IronChainsawCharge,
				ConfigTechReborn.IronChainsawTier, 2.0F);
		this.cost = 50;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state)
	{
		return Items.IRON_AXE.canHarvestBlock(state);
	}

	@Override
	public String getTextureName(int damage)
	{
		return "techreborn:items/tool/ironChainsaw";
	}

	@Override
	public int getMaxMeta()
	{
		return 1;
	}
}
