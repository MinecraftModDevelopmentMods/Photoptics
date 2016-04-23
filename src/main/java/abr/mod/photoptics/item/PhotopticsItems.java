package abr.mod.photoptics.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PhotopticsItems {
	
	public static Item basicBinoculars;
	
	public static void init() {
    	basicBinoculars = new ItemBasicBinoculars()
    			.setUnlocalizedName("photoptics.basicbinoculars")
    			.setCreativeTab(CreativeTabs.tabTools).setMaxStackSize(1)
    			.setTextureName("photoptics:basic_binoculars");
    	
    	GameRegistry.registerItem(basicBinoculars, "photopticsbasicbinoculars");
	}

}
