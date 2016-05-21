package abr.mod.photoptics.processing;

import abr.mod.photoptics.item.PhotopticsItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PhotopticsRecipes {
	
	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(PhotopticsItems.basicBinoculars),
				"g g", "iii", "g g",
				'g', new ItemStack(Blocks.GLASS_PANE),
				'i', new ItemStack(Items.IRON_INGOT));
		
		GameRegistry.addShapedRecipe(new ItemStack(PhotopticsItems.basicHandheldTelescope),
				" G ", " I ", " g ",
				'G', new ItemStack(Blocks.GLASS),
				'g', new ItemStack(Blocks.GLASS_PANE),
				'I', new ItemStack(Blocks.IRON_BLOCK));
	}

}
