package techreborn.items.tools;

import me.modmuss50.jsonDestroyer.api.IHandHeld;
import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.RebornCore;
import reborncore.api.power.IEnergyItemInfo;
import reborncore.common.powerSystem.PoweredItem;
import reborncore.common.util.TorchHelper;
import techreborn.client.TechRebornCreativeTab;
import techreborn.lib.ModInfo;

import java.util.List;
import java.util.Random;

import net.minecraft.item.Item.ToolMaterial;
public class ItemDrill extends ItemPickaxe implements IEnergyItemInfo, ITexturedItem , IHandHeld
{

	public static int tier = 1;
	public int maxCharge = 1;
	public int cost = 250;
	public float unpoweredSpeed = 2.0F;
	public double transferLimit = 100;

	public ItemDrill(ToolMaterial material, String unlocalizedName, int energyCapacity, int tier, float unpoweredSpeed,  float efficiencyOnProperMaterial)
	{
		super(material);
		this.efficiencyOnProperMaterial = efficiencyOnProperMaterial;
		setCreativeTab(TechRebornCreativeTab.instance);
		setMaxStackSize(1);
		setMaxDamage(240);
		setUnlocalizedName(unlocalizedName);
		RebornCore.jsonDestroyer.registerObject(this);
		this.maxCharge = energyCapacity;
		this.tier = tier;
		this.unpoweredSpeed = unpoweredSpeed;
	}

	@Override public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos,
			EntityLivingBase entityLiving)
	{
		Random rand = new Random();
		if (rand.nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(34), stack) + 1) == 0)
		{
			PoweredItem.useEnergy(cost, stack);
		}
		return true;
	}

	@Override public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		if (!PoweredItem.canUseEnergy(cost, stack))
		{
			return unpoweredSpeed;
		}
		if (Items.WOODEN_PICKAXE.getStrVsBlock(stack, state) > 1.0F
				|| Items.WOODEN_SHOVEL.getStrVsBlock(stack, state) > 1.0F)
		{
			return efficiencyOnProperMaterial;
		} else
		{
			return super.getStrVsBlock(stack, state);
		}
	}

	@Override public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving,
			EntityLivingBase entityliving1)
	{
		return true;
	}

	@Override public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return TorchHelper.placeTorch(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, hand);
	}

	@Override public boolean isRepairable()
	{
		return false;
	}

	@Override public double getMaxPower(ItemStack stack)
	{
		return maxCharge;
	}

	@Override public boolean canAcceptEnergy(ItemStack stack)
	{
		return true;
	}

	@Override public boolean canProvideEnergy(ItemStack stack)
	{
		return false;
	}

	@Override public double getMaxTransfer(ItemStack stack)
	{
		return transferLimit;
	}

	@Override public int getStackTier(ItemStack stack)
	{
		return tier;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) @SideOnly(Side.CLIENT) public void getSubItems(Item item,
			CreativeTabs par2CreativeTabs, List itemList)
	{
		ItemStack itemStack = new ItemStack(this, 1);
		itemList.add(itemStack);

		ItemStack charged = new ItemStack(this, 1);
		PoweredItem.setEnergy(getMaxPower(charged), charged);
		itemList.add(charged);
	}

	@Override public double getDurabilityForDisplay(ItemStack stack)
	{
		double charge = (PoweredItem.getEnergy(stack) / getMaxPower(stack));
		return 1 - charge;

	}

	@Override public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	@Override public String getTextureName(int damage)
	{
		return "techreborn:items/tool/nullDrill";
	}

	@Override public int getMaxMeta()
	{
		return 1;
	}

	@Override @SideOnly(Side.CLIENT) public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player,
			int useRemaining)
	{
		return new ModelResourceLocation(ModInfo.MOD_ID + ":" + getUnlocalizedName(stack).substring(5), "inventory");
	}
}
