package abr.mod.photoptics;

import abr.mod.photoptics.processing.PlayerObservationData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

public class PhotopticsForgeEventHandler {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			PlayerObservationData.registerData(player);
		}
	}

}
