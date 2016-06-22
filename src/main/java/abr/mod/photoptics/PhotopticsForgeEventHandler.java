package abr.mod.photoptics;

import abr.mod.photoptics.processing.IObservationData;
import abr.mod.photoptics.processing.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer prev = event.getOriginal();
		IObservationData prevData = prev.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP);

		EntityPlayer next = event.getEntityPlayer();
		IObservationData nextData = next.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP);

		nextData.deserializeNBT(prevData.serializeNBT());
	}
}
