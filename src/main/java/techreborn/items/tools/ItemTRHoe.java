package techreborn.items.tools;

import me.modmuss50.jsonDestroyer.api.IHandHeld;
import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.RebornCore;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.lib.ModInfo;

import net.minecraft.item.Item.ToolMaterial;
public class ItemTRHoe extends ItemHoe implements ITexturedItem, IHandHeld
{
	private ToolMaterial material = ToolMaterial.WOOD;
	
	public ItemTRHoe(ToolMaterial material) {
		super(material);
		setUnlocalizedName(material.name().toLowerCase()+"Hoe");
		setCreativeTab(TechRebornCreativeTabMisc.instance);
		RebornCore.jsonDestroyer.registerObject(this);
		this.material=material;
	}

	@Override
	public String getTextureName(int damage)
	{
		return "techreborn:items/tool/"+material.name().toLowerCase()+"_hoe";
	}

	@Override
	public int getMaxMeta()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		return new ModelResourceLocation(ModInfo.MOD_ID + ":" + getUnlocalizedName(stack).substring(5), "inventory");
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}
