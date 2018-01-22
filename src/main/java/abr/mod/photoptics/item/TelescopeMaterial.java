package abr.mod.photoptics.item;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stellarapi.api.optics.WaveIntensive;
import stellarapi.api.optics.Wavelength;

public class TelescopeMaterial {
	public final double lumMultiplier;
	public final double zoomMultiplier;
	public final String name;
	public final WaveIntensive filterProperty;
	public final String resourceName;

	public TelescopeMaterial(String name, double red, double green, double blue, String resourceName, double lumMult, double zoomMult) {
		this.name = name;
		this.filterProperty = new WaveIntensive(
				ImmutableMap.of(Wavelength.red, red,
						Wavelength.V, green, Wavelength.B, blue)
				);
		this.resourceName = resourceName;
		
		this.lumMultiplier = lumMult;
		this.zoomMultiplier = zoomMult;
	}
}
