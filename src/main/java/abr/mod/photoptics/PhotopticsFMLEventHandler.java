package abr.mod.photoptics;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PhotopticsFMLEventHandler {
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(Photoptics.modid))
			Photoptics.instance.getConfigManager().syncFromGUI();
	}

}
