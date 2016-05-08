package abr.mod.photoptics;

import abr.mod.photoptics.entity.EntityTelescopeSimulator;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import stellarapi.api.event.UpdateFilterEvent;
import stellarapi.api.event.UpdateScopeEvent;

public class StellarAPIEventHandler {
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onUpdateScope(UpdateScopeEvent event) {
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if(player.riddenByEntity instanceof EntityTelescopeSimulator) {
				
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onUpdateFilter(UpdateFilterEvent event) {
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if(player.riddenByEntity instanceof EntityTelescopeSimulator) {
				
			}
		}
	}

}
