package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stellarapi.api.helper.PlayerItemAccessHelper;

public class TickEventHandler {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void tickStart(TickEvent.PlayerTickEvent e) {
		if(e.phase == TickEvent.Phase.START) {
			ItemStack itemstack = e.player.inventory.getCurrentItem();
			ItemStack itemInUse = PlayerItemAccessHelper.getUsingItem(e.player);
            
            if(itemInUse != null && itemInUse.getItem() instanceof ItemTelescopeBase)
            	Photoptics.proxy.forcePerspective();
		}
	}
}
