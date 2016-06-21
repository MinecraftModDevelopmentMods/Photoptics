package abr.mod.photoptics.processing;

import abr.mod.photoptics.item.ItemTelescopeBase;
import abr.mod.photoptics.item.PhotopticsItems;
import abr.mod.photoptics.item.TelescopeMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PhotopticsRecipes {
	
	public static void init() {
		for(Item item : PhotopticsItems.INSTANCE.binocularsList) {
			TelescopeMaterial material = ((ItemTelescopeBase) item).getTelescopeMaterial();
			GameRegistry.addShapedRecipe(new ItemStack(item),
					"g g", "iii", "g g",
					'g', material.recipeItems.get('g'),
					'i', material.recipeItems.get('i'));
		}

		for(Item item : PhotopticsItems.INSTANCE.handheldTelescopeList) {
			TelescopeMaterial material = ((ItemTelescopeBase) item).getTelescopeMaterial();
			GameRegistry.addShapedRecipe(new ItemStack(item),
					" G ", " I ", " g ",
					'G', material.recipeItems.get('G'),
					'g', material.recipeItems.get('g'),
					'I', material.recipeItems.get('I'));
		}
	}

}
