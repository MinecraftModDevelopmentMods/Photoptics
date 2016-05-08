package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.item.ItemStack;
import stellarapi.api.helper.PlayerItemAccessHelper;

public class TickEventHandler {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void tickStart(TickEvent.PlayerTickEvent e) {
		if(e.phase == Phase.START) {
			ItemStack itemstack = e.player.inventory.getCurrentItem();
			ItemStack itemInUse = PlayerItemAccessHelper.getUsingItem(e.player);
            
            if(itemInUse != null && itemInUse.getItem() instanceof ItemTelescopeBase)
            	Photoptics.proxy.forcePerspective();
		}
	}
}
