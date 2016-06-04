package abr.mod.photoptics.item;

import abr.mod.photoptics.Photoptics;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PhotopticsItems {
	
	public static final Item basicBinoculars = new ItemBasicBinoculars();
	public static final Item basicHandheldTelescope= new ItemBasicHandheldTelescope();
	
	public static void init() {
    	basicBinoculars
    			.setUnlocalizedName("photoptics.basicbinoculars")
    			.setCreativeTab(CreativeTabs.TOOLS).setMaxStackSize(1);
    	
    	basicHandheldTelescope 
    			.setUnlocalizedName("photoptics.basichandheldtelescope")
    			.setCreativeTab(CreativeTabs.TOOLS).setMaxStackSize(1);

    	GameRegistry.register(basicBinoculars,
    			new ResourceLocation(Photoptics.resourceid, "photopticsbasicbinoculars"));
    	GameRegistry.register(basicHandheldTelescope,
    			new ResourceLocation(Photoptics.resourceid, "basichandheldtelescope"));
	}
}
