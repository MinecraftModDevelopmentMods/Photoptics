package abr.mod.photoptics.config;

import java.util.List;

import com.google.common.collect.Lists;

import abr.mod.photoptics.Photoptics;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class POGuiConfig extends GuiConfig {

	public POGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, gatherConfigElements(), Photoptics.modid,
				false, false, "Photoptics Configurations");
	}
	
	private static List<IConfigElement> gatherConfigElements() {
		List<IConfigElement> configElements = Lists.newArrayList();
		Configuration config = Photoptics.instance.getConfigManager().getConfig();
		for(String category : config.getCategoryNames())
			configElements.add(new ConfigElement(config.getCategory(category)));
		return configElements;
	}
}
