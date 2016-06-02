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

public class PhotopticsItems {
	
	public static final CreativeTabs tabPhotoptics = new CreativeTabs(Photoptics.resourceid) {
		@Override
		public Item getTabIconItem() {
			return basicBinoculars;
		}
	};
	
	public static TelescopeMaterial basic, gold, diamondGreen, diamondBlue, diamondRed;

	public static Item basicBinoculars;
	public static Item basicHandheldTelescope;
	
	public static List<Item> binocularsList = Lists.newArrayList();
	public static List<Item> handheldTelescopeList = Lists.newArrayList();
	
	public static void init() {
		basic = new TelescopeMaterial("basic", 0.6, 0.7, 0.8,
				new ResourceLocation(Photoptics.resourceid, "textures/items/basic_materials.png"));
		
		gold = new TelescopeMaterial("gold", 0.9, 0.8, 0.5,
				new ResourceLocation(Photoptics.resourceid, "textures/items/gold_material.png"))
				.setRecipeItem('i', new ItemStack(Items.gold_ingot))
				.setRecipeItem('I', new ItemStack(Blocks.gold_block));
		
		diamondGreen = new TelescopeMaterial("diamondGreen", 0.3, 1.0, 0.3,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond_green.png"))
				.setRecipeItem('i', new ItemStack(Items.diamond))
				.setRecipeItem('I', new ItemStack(Blocks.diamond_block))
				.setRecipeItem('g', new ItemStack(Blocks.stained_glass_pane, 1, 5))
				.setRecipeItem('G', new ItemStack(Blocks.stained_glass, 1, 5));

		diamondBlue = new TelescopeMaterial("diamondBlue", 0.3, 0.3, 1.0,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond_blue.png"))
				.setRecipeItem('i', new ItemStack(Items.diamond))
				.setRecipeItem('I', new ItemStack(Blocks.diamond_block))
				.setRecipeItem('g', new ItemStack(Blocks.stained_glass_pane, 1, 3))
				.setRecipeItem('G', new ItemStack(Blocks.stained_glass, 1, 3));

		diamondRed = new TelescopeMaterial("diamondRed", 1.0, 0.3, 0.3,
				new ResourceLocation(Photoptics.resourceid, "textures/items/diamond_red.png"))
				.setRecipeItem('i', new ItemStack(Items.diamond))
				.setRecipeItem('I', new ItemStack(Blocks.diamond_block))
				.setRecipeItem('g', new ItemStack(Blocks.stained_glass_pane, 1, 14))
				.setRecipeItem('G', new ItemStack(Blocks.stained_glass, 1, 14));
    	
		addMaterialItem(basic);
		addMaterialItem(gold);
		addMaterialItem(diamondGreen);
		addMaterialItem(diamondBlue);
		addMaterialItem(diamondRed);
		
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
	
	public static void addMaterialItem(TelescopeMaterial material) {
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
