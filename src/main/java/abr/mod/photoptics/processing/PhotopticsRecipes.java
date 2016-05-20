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
				'g', new ItemStack(Blocks.glass_pane),
				'i', new ItemStack(Items.iron_ingot));
		
		GameRegistry.addShapedRecipe(new ItemStack(PhotopticsItems.basicHandheldTelescope),
				" G ", " I ", " g ",
				'G', new ItemStack(Blocks.glass),
				'g', new ItemStack(Blocks.glass_pane),
				'I', new ItemStack(Blocks.iron_block));
	}

}
