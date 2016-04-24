package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import stellarapi.api.helper.PlayerItemAccessHelper;

public class PhotopticsTelescopeHandler {
	
	/**
	 * Triggered on both side.
	 * */
	public static void onKeyInput(EntityPlayer player, EnumPhotopticsKeys key) {
		ItemStack usingItem = PlayerItemAccessHelper.getUsingItem(player);
		
		if(usingItem != null && usingItem.getItem() instanceof ItemTelescopeBase)
		{
			ItemTelescopeBase item = (ItemTelescopeBase) usingItem.getItem();
			item.keyControl(key, player, usingItem);
		}
	}

}
