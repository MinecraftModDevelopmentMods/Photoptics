package abr.mod.photoptics.item;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stellarapi.api.optics.WaveIntensive;
import stellarapi.api.optics.Wavelength;

public class TelescopeMaterial {
	public final double lumMultiplier;
	public final double zoomMultiplier;
	public final String name;
	public final WaveIntensive filterProperty;
	public final ResourceLocation textureLocation;
	public final Map<Character, ItemStack> recipeItems = Maps.newHashMap();

	public TelescopeMaterial(String name, double red, double green, double blue, ResourceLocation textureLocation, double lumMult, double zoomMult) {
		this.name = name;
		this.filterProperty = new WaveIntensive(
				ImmutableMap.of(Wavelength.red, red,
						Wavelength.V, green, Wavelength.B, blue)
				);
		this.textureLocation = textureLocation;
		
		this.lumMultiplier = lumMult;
		this.zoomMultiplier = zoomMult;

		this.setRecipeItem('g', new ItemStack(Blocks.glass_pane));
		this.setRecipeItem('G', new ItemStack(Blocks.glass));
		this.setRecipeItem('i', new ItemStack(Items.iron_ingot));
		this.setRecipeItem('I', new ItemStack(Blocks.iron_block));
	}
	
	public TelescopeMaterial setRecipeItem(char id, ItemStack item) {
		recipeItems.put(id, item);
		return this;
	}
}
