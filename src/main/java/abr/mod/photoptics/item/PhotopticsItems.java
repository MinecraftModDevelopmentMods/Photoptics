package abr.mod.photoptics.item;

import java.util.List;

import com.google.common.collect.Lists;

import abr.mod.photoptics.Photoptics;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stellarapi.api.lib.config.ConfigManager;

public enum PhotopticsItems {
	INSTANCE;
	
	public final CreativeTabs tabPhotoptics = new CreativeTabs(Photoptics.resourceid) {
		@Override
		public Item getTabIconItem() {
			return basicBinoculars;
		}
	};

	public TelescopeMaterial basic, gold, diamondGreen, diamondBlue, diamondRed, ultimate;

	public Item basicBinoculars;
	public Item basicHandheldTelescope;
	
	public List<Item> binocularsList = Lists.newArrayList();
	public List<Item> handheldTelescopeList = Lists.newArrayList();
	
	public void preInit() {
		basic = new TelescopeMaterial("basic", 0.6, 0.7, 0.8,
				new ResourceLocation(Photoptics.resourceid, "textures/items/basic_materials.png"), 0.9, 1);
		
		gold = new TelescopeMaterial("gold", 0.9, 0.8, 0.5,
				new ResourceLocation(Photoptics.resourceid, "textures/items/gold_material.png"), 2.0, 1.5)
				.setRecipeItem('i', new ItemStack(Items.gold_ingot))
				.setRecipeItem('I', new ItemStack(Blocks.gold_block));
		
		diamondGreen = new TelescopeMaterial("diamondGreen", 0.3, 1.0, 0.3,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond_green.png"), 5.0, 1.5)
				.setRecipeItem('i', new ItemStack(Items.diamond))
				.setRecipeItem('I', new ItemStack(Blocks.diamond_block))
				.setRecipeItem('g', new ItemStack(Blocks.stained_glass_pane, 1, 5))
				.setRecipeItem('G', new ItemStack(Blocks.stained_glass, 1, 5));

		diamondBlue = new TelescopeMaterial("diamondBlue", 0.3, 0.3, 1.0,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond_blue.png"), 5.0, 1.5)
				.setRecipeItem('i', new ItemStack(Items.diamond))
				.setRecipeItem('I', new ItemStack(Blocks.diamond_block))
				.setRecipeItem('g', new ItemStack(Blocks.stained_glass_pane, 1, 3))
				.setRecipeItem('G', new ItemStack(Blocks.stained_glass, 1, 3));

		diamondRed = new TelescopeMaterial("diamondRed", 1.0, 0.3, 0.3,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond_red.png"), 5.0, 1.5)
				.setRecipeItem('i', new ItemStack(Items.diamond))
				.setRecipeItem('I', new ItemStack(Blocks.diamond_block))
				.setRecipeItem('g', new ItemStack(Blocks.stained_glass_pane, 1, 14))
				.setRecipeItem('G', new ItemStack(Blocks.stained_glass, 1, 14));

		ultimate = new TelescopeMaterial("ultimate", 0.5, 1.0, 0.5,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond.png"), 50.0, 2.0)
				.setRecipeItem('i', new ItemStack(Blocks.ender_chest))
				.setRecipeItem('I', new ItemStack(Blocks.enchanting_table))
				.setRecipeItem('g', new ItemStack(Items.ender_pearl))
				.setRecipeItem('G', new ItemStack(Items.ender_eye));

		addMaterialItem(basic);
		addMaterialItem(gold);
		addMaterialItem(diamondGreen);
		addMaterialItem(diamondBlue);
		addMaterialItem(diamondRed);
		addMaterialItem(ultimate);
		
		for(Item binoculars : binocularsList) {
			TelescopeMaterial material = ((ItemTelescopeBase) binoculars).getTelescopeMaterial();
			if(material == basic)
				GameRegistry.registerItem(basicBinoculars = binoculars, "photopticsbasicbinoculars");
			else GameRegistry.registerItem(binoculars, String.format("photoptics.binoculars.%s", material.name));
		}
		
		for(Item handheldTelescope : handheldTelescopeList) {
			TelescopeMaterial material = ((ItemTelescopeBase) handheldTelescope).getTelescopeMaterial();
			if(material == basic)
				GameRegistry.registerItem(basicHandheldTelescope = handheldTelescope, "basichandheldtelescope");
			else GameRegistry.registerItem(handheldTelescope, String.format("photoptics.handheldtelescope.%s", material.name));
		}
	}

	public void addMaterialItem(TelescopeMaterial material) {
		binocularsList.add(new ItemBasicBinoculars()
    			.setTelescopeMaterial(material)
    			.setUnlocalizedName(String.format("photoptics.binoculars.%s", material.name))
    			.setCreativeTab(tabPhotoptics).setMaxStackSize(1));
    	
		handheldTelescopeList.add(new ItemBasicHandheldTelescope()
    			.setTelescopeMaterial(material)
    			.setUnlocalizedName(String.format("photoptics.handheldtelescope.%s", material.name))
    			.setCreativeTab(tabPhotoptics).setMaxStackSize(1));
	}

}
