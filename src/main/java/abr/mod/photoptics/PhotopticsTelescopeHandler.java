package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import abr.mod.photoptics.processing.PlayerObservationData;
import abr.mod.photoptics.processing.PossibleObservations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.celestials.CelestialCollectionManager;
import stellarapi.api.celestials.ICelestialObject;
import stellarapi.api.helper.PlayerItemAccessHelper;
import stellarapi.api.lib.math.SpCoord;

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

	/**
	 * @param player the observing player
	 * @param observeRange the observing range in degrees
	 * */
	public static void onObserve(EntityPlayer player, double observeRange) {
		if(player.worldObj.isRemote)
			return;
		
		float rotationYaw = player.rotationYaw;
		float rotationPitch = -player.rotationPitch;

		SpCoord currentDirection = new SpCoord(( - rotationYaw) % 360.0, rotationPitch);
		
		CelestialCollectionManager manager = StellarAPIReference.getCollectionManager(player.worldObj);
		if(manager != null) {
			for(ICelestialObject object : manager.findAllObjectsInRange(currentDirection, observeRange)) {
				String name = object.getName();
				String command = PossibleObservations.instance().entryMap().get(name);
				Photoptics.logger.info(name);
				if(command != null && PlayerObservationData.getData(player).onObserve(name)) {
					MinecraftServer server = MinecraftServer.getServer();
					server.getCommandManager().executeCommand(server, command);
					return;
				}
			}
		}
	}

}
