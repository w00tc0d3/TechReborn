package techreborn.proxies;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import reborncore.RebornCore;
import reborncore.client.multiblock.MultiblockRenderEvent;
import reborncore.common.blocks.BlockMachineBase;
import techreborn.client.ClientMultiBlocks;
import techreborn.client.IconSupplier;
import techreborn.client.RegisterItemJsons;
import techreborn.client.StackToolTipEvent;
import techreborn.client.VersionCheckerClient;
import techreborn.client.hud.ChargeHud;
import techreborn.client.keybindings.KeyBindings;
import techreborn.client.render.entitys.RenderNukePrimed;
import techreborn.entitys.EntityNukePrimed;
import techreborn.init.ModSounds;
import techreborn.manual.loader.ManualLoader;

import java.io.File;
import java.io.IOException;

public class ClientProxy extends CommonProxy
{

	public static MultiblockRenderEvent multiblockRenderEvent;

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		RenderingRegistry.registerEntityRenderingHandler(EntityNukePrimed.class, new RenderManagerNuke());

		ManualLoader loader = new ManualLoader(new File(event.getModConfigurationDirectory(), "techreborn"));

		new Thread(() ->
		{
			try {
				loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

		for(Object object : RebornCore.jsonDestroyer.objectsToDestroy) {
			if (object instanceof BlockMachineBase) {
				BlockMachineBase base = (BlockMachineBase) object;
				registerItemModel(Item.getItemFromBlock(base));
			}
		}
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		RegisterItemJsons.registerModels();
		MinecraftForge.EVENT_BUS.register(new IconSupplier());
		MinecraftForge.EVENT_BUS.register(new ChargeHud());
		MinecraftForge.EVENT_BUS.register(new VersionCheckerClient());
		MinecraftForge.EVENT_BUS.register(new StackToolTipEvent());
		multiblockRenderEvent = new MultiblockRenderEvent();
		// MinecraftForge.EVENT_BUS.register(multiblockRenderEvent);
		// TODO FIX ME
		ClientRegistry.registerKeyBinding(KeyBindings.config);
		ClientMultiBlocks.init();
	}

	protected void registerItemModel(ItemStack item, String name) {
		// tell Minecraft which textures it has to load. This is resource-domain sensitive
		ModelLoader.registerItemVariants(item.getItem(), new ResourceLocation(name));
		// tell the game which model to use for this item-meta combination
		ModelLoader.setCustomModelResourceLocation(item.getItem(), item
				.getMetadata(), new ModelResourceLocation(name, "inventory"));
	}

	public ResourceLocation registerItemModel(Item item) {
		ResourceLocation itemLocation = getItemLocation(item);
		if(itemLocation == null) {
			return null;
		}

		return registerIt(item, itemLocation);
	}

	public static ResourceLocation getItemLocation(Item item) {
		Object o = item.getRegistryName();
		if(o == null) {
			return null;
		}
		return (ResourceLocation) o;
	}

	private static ResourceLocation registerIt(Item item, final ResourceLocation location) {
		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(location, "inventory");
			}
		});
		ModelLoader.registerItemVariants(item, location);

		return location;
	}

	public class RenderManagerNuke implements IRenderFactory<EntityNukePrimed>
	{

		@Override
		public Render<? super EntityNukePrimed> createRenderFor(RenderManager manager)
		{
			return new RenderNukePrimed(manager);
		}
	}

}
