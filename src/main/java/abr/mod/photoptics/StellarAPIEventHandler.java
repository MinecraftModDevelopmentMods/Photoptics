package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import stellarapi.api.event.UpdateScopeEvent;
import stellarapi.api.helper.PlayerItemAccessHelper;
import stellarapi.api.optics.IViewScope;

public class StellarAPIEventHandler {
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void updateScope(UpdateScopeEvent event) {
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			ItemStack itemToCheck = PlayerItemAccessHelper.getUsingItem(player);
			
			if(itemToCheck != null && itemToCheck.getItem() instanceof ItemTelescopeBase)
			{
				ItemTelescopeBase telescope = (ItemTelescopeBase) itemToCheck.getItem();
				event.setScope(telescope.getScope(itemToCheck));
			}
		}
	}

}
