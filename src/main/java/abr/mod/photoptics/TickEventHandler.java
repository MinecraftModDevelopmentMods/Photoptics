package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stellarapi.api.StellarAPIReference;

public class TickEventHandler {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingUpdate(LivingUpdateEvent event) {
		EnumHand hand = event.getEntityLiving().getActiveHand();
		ItemStack itemstack = hand != null ? event.getEntityLiving().getHeldItem(hand) : null;
		ItemStack itemInUse = event.getEntityLiving().getActiveItemStack();

		if(itemInUse != null && itemInUse.getItem() instanceof ItemTelescopeBase)
			Photoptics.proxy.forcePerspective(event.getEntityLiving());
		
		ItemStack offHandStack = event.getEntityLiving().getHeldItemOffhand();
		if(offHandStack.getItem() instanceof ItemTelescopeBase)
			StellarAPIReference.updateScope(event.getEntityLiving());
	}
}