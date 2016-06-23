package abr.mod.photoptics;

import abr.mod.photoptics.item.ITelescopeProperty;
import abr.mod.photoptics.processing.PlayerDataProvider;
import abr.mod.photoptics.processing.PossibleObservations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import stellarapi.api.StellarAPICapabilities;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.celestials.CelestialCollectionManager;
import stellarapi.api.celestials.ICelestialObject;
import stellarapi.api.interact.IOpticalProperties;
import stellarapi.api.lib.math.SpCoord;
import stellarapi.api.optics.IViewScope;

public class PhotopticsTelescopeHandler {
	
	/**
	 * Triggered on both side.
	 * */
	public static void onKeyInput(EntityPlayer player, EnumPhotopticsKeys key) {
		ItemStack usingItem = player.getActiveItemStack();
		
		if(usingItem != null && usingItem.hasCapability(StellarAPICapabilities.OPTICAL_PROPERTY, EnumFacing.UP)) {
			IOpticalProperties capability = usingItem.getCapability(StellarAPICapabilities.OPTICAL_PROPERTY, EnumFacing.UP);
			if(capability instanceof ITelescopeProperty) {
				ITelescopeProperty property = (ITelescopeProperty) capability;
				property.keyControl(key, player);
			}
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
		IViewScope scope = StellarAPIReference.getScope(player);

		if(manager != null && scope != null) {
			for(ICelestialObject object : manager.findAllObjectsInRange(currentDirection, observeRange)) {
				double magnitude = object.getStandardMagnitude();
				magnitude -= 2.5 * Math.log10(scope.getLGP());
				if(magnitude > 6.0)
					continue;

				String name = object.getName();
				String command = PossibleObservations.instance().entryMap().get(name);
				Photoptics.logger.info(name);
				if(command != null && player.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP).onObserve(object, player)) {
					MinecraftServer server = player.getServer();
					for(String splitted : command.split(";")) {
						splitted = splitted
								.replace("@p", player.getUniqueID().toString())
								.replace("@o", name.replace(' ', '_'))
								.replace("@x", String.valueOf(MathHelper.floor_double(player.posX)))
								.replace("@y", String.valueOf(MathHelper.floor_double(player.posY)))
								.replace("@z", String.valueOf(MathHelper.floor_double(player.posZ)))
								.trim();
						server.getCommandManager().executeCommand(server, splitted);
					}
					Photoptics.logger.info("Found");
					return;
				}
			}
			
			Photoptics.logger.info("Ended search");
		}
	}

}