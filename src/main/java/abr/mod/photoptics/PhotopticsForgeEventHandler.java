package abr.mod.photoptics;

import abr.mod.photoptics.processing.PlayerObservationData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PhotopticsForgeEventHandler {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			PlayerObservationData.registerData(player);
		}
	}

}
