package abr.mod.photoptics.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PhotopticsItems {
	
	public static final Item basicBinoculars = new ItemBasicBinoculars();
	public static final Item basicHandheldTelescope= new ItemBasicHandheldTelescope();
	
	public static void init() {
    	basicBinoculars
    			.setUnlocalizedName("photoptics.basicbinoculars")
    			.setCreativeTab(CreativeTabs.tabTools).setMaxStackSize(1);
    	
    	basicHandheldTelescope 
    			.setUnlocalizedName("photoptics.basichandheldtelescope")
    			.setCreativeTab(CreativeTabs.tabTools).setMaxStackSize(1);
    	
    	GameRegistry.registerItem(basicBinoculars, "photopticsbasicbinoculars");
    	GameRegistry.registerItem(basicHandheldTelescope, "basichandheldtelescope");
	}

}
