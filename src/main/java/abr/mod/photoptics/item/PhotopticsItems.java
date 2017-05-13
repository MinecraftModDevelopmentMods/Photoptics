package abr.mod.photoptics.item;

import java.util.List;

import com.google.common.collect.Lists;

import abr.mod.photoptics.Photoptics;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

@Mod.EventBusSubscriber
public enum PhotopticsItems {
	INSTANCE;
	
	public final CreativeTabs tabPhotoptics = new CreativeTabs(Photoptics.resourceid) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(basicBinoculars);
		}
	};

	public TelescopeMaterial basic, gold, diamondGreen, diamondBlue, diamondRed, ultimate;

	public Item basicBinoculars;
	public Item basicHandheldTelescope;

	public List<ItemTelescopeBase> binocularsList = Lists.newArrayList();
	public List<ItemTelescopeBase> handheldTelescopeList = Lists.newArrayList();

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> itemRegistry) {
		INSTANCE.registerItems(itemRegistry);
	}

	private void registerItems(RegistryEvent.Register<Item> itemRegistry) {
		IForgeRegistry<Item> registry = itemRegistry.getRegistry();

		basic = new TelescopeMaterial("basic", 0.6, 0.7, 0.8,
				"basic_materials", 1.0, 1.2);
		
		gold = new TelescopeMaterial("gold", 0.9, 0.8, 0.5,
				"gold_material", 3.5, 2.0)
				.setRecipeItem('i', new ItemStack(Items.GOLD_INGOT))
				.setRecipeItem('I', new ItemStack(Blocks.GOLD_BLOCK));
		
		diamondGreen = new TelescopeMaterial("diamondGreen", 0.3, 1.0, 0.3,
				"diamond_green", 7.0, 2.5)
				.setRecipeItem('i', new ItemStack(Items.DIAMOND))
				.setRecipeItem('I', new ItemStack(Blocks.DIAMOND_BLOCK))
				.setRecipeItem('g', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 5))
				.setRecipeItem('G', new ItemStack(Blocks.STAINED_GLASS, 1, 5));

		diamondBlue = new TelescopeMaterial("diamondBlue", 0.3, 0.3, 1.0,
				"diamond_blue", 7.0, 2.5)
				.setRecipeItem('i', new ItemStack(Items.DIAMOND))
				.setRecipeItem('I', new ItemStack(Blocks.DIAMOND_BLOCK))
				.setRecipeItem('g', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 3))
				.setRecipeItem('G', new ItemStack(Blocks.STAINED_GLASS, 1, 3));

		diamondRed = new TelescopeMaterial("diamondRed", 1.0, 0.3, 0.3,
				"diamond_red", 7.0, 2.5)
				.setRecipeItem('i', new ItemStack(Items.DIAMOND))
				.setRecipeItem('I', new ItemStack(Blocks.DIAMOND_BLOCK))
				.setRecipeItem('g', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 14))
				.setRecipeItem('G', new ItemStack(Blocks.STAINED_GLASS, 1, 14));

		ultimate = new TelescopeMaterial("ultimate", 0.5, 1.0, 0.5,
				"ultimate", 90.0, 5.0)
				.setRecipeItem('i', new ItemStack(Blocks.ENDER_CHEST))
				.setRecipeItem('I', new ItemStack(Blocks.ENCHANTING_TABLE))
				.setRecipeItem('g', new ItemStack(Items.ENDER_PEARL))
				.setRecipeItem('G', new ItemStack(Items.ENDER_EYE));

		addMaterialItem(basic);
		addMaterialItem(gold);
		addMaterialItem(diamondGreen);
		addMaterialItem(diamondBlue);
		addMaterialItem(diamondRed);
		addMaterialItem(ultimate);
		
		for(ItemTelescopeBase binoculars : binocularsList) {
			TelescopeMaterial material = ((ItemTelescopeBase) binoculars).getTelescopeMaterial();
			if(material == basic) {
				basicBinoculars = binoculars;
				binoculars.setTelescopeRegistryName(new ResourceLocation(Photoptics.resourceid, "photopticsbasicbinoculars"));
			}

			binoculars.preRegister();
			registry.register(binoculars);
		}
		
		for(ItemTelescopeBase handheldTelescope : handheldTelescopeList) {
			TelescopeMaterial material = ((ItemTelescopeBase) handheldTelescope).getTelescopeMaterial();
			if(material == basic) {
				basicHandheldTelescope = handheldTelescope;
				handheldTelescope.setTelescopeRegistryName(new ResourceLocation(Photoptics.resourceid, "basichandheldtelescope"));
			}

			handheldTelescope.preRegister();
			registry.register(handheldTelescope);
		}
	}

	public void addMaterialItem(TelescopeMaterial material) {
		binocularsList.add((ItemTelescopeBase) new ItemBasicBinoculars()
    			.setTelescopeMaterial(material)
    			.setTelescopeRegistryName(new ResourceLocation(Photoptics.resourceid, String.format("binoculars_%s", material.name.toLowerCase())))
    			.setUnlocalizedName(String.format("photoptics.binoculars.%s", material.name))
    			.setCreativeTab(tabPhotoptics).setMaxStackSize(1));

		handheldTelescopeList.add((ItemTelescopeBase) new ItemBasicHandheldTelescope()
    			.setTelescopeMaterial(material)
    			.setTelescopeRegistryName(new ResourceLocation(Photoptics.resourceid, String.format("handheldtelescope_%s", material.name.toLowerCase())))
    			.setUnlocalizedName(String.format("photoptics.handheldtelescope.%s", material.name))
    			.setCreativeTab(tabPhotoptics).setMaxStackSize(1));
	}
}
