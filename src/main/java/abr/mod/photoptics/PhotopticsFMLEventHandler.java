package abr.mod.photoptics;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PhotopticsFMLEventHandler {
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.modID == Photoptics.modid)
			Photoptics.instance.getConfigManager().syncFromGUI();
	}

}
