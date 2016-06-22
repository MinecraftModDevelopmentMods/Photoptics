package abr.mod.photoptics;

import abr.mod.photoptics.processing.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PhotopticsForgeEventHandler {

	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent.Entity event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			event.addCapability(
					new ResourceLocation(Photoptics.resourceid, "opticsdata"),
					new PlayerDataProvider());
		}
	}
}
