package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import abr.mod.photoptics.render.overlay.OverlayBinocularsRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.optics.IViewScope;
import stellarapi.api.optics.Wavelength;

public class ItemBasicBinoculars extends ItemTelescopeBase {
	
	public final int maxZoom = 5;
	
	@Override
	public IViewScope getScope(EntityPlayer player, final ItemStack stack) {
		return new IViewScope() {

				@Override
				public double getLGP() {
					return 20.0;
				}

				@Override
				public double getResolution(Wavelength wl) {
					if(!stack.hasTagCompound()) {
						stack.setTagCompound(new NBTTagCompound());
						stack.getTagCompound().setInteger("zoom", 0);
					}
					
					int zoom = stack.getTagCompound().getInteger("zoom");
					
					return 0.2 / Math.sqrt(1.0 + zoom / 10.0);
				}

				@Override
				public double getMP() {
					if(!stack.hasTagCompound()) {
						stack.setTagCompound(new NBTTagCompound());
						stack.getTagCompound().setInteger("zoom", 0);
					}
					
					int zoom = stack.getTagCompound().getInteger("zoom");
					
					return 3.0 * (1.0 + zoom / 10.0);
				}

				@Override
				public boolean forceChange() {
					return true;
				}

				@Override
				public boolean isFOVCoverSky() {
					return true;
				}
		};
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IOverlayRenderer getOverlayRenderer(ItemStack stack) {
		return new OverlayBinocularsRenderer();
	}

	@Override
	public void keyControl(EnumPhotopticsKeys key, EntityPlayer player,ItemStack usingItem) {
		if(!usingItem.hasTagCompound()) {
			usingItem.setTagCompound(new NBTTagCompound());
			usingItem.getTagCompound().setInteger("zoom", 0);
		}
		
		int current = usingItem.getTagCompound().getInteger("zoom");
		
		switch(key) {
		case ZoomIn:
			current = Math.min(this.maxZoom, current+1);
			break;
		case ZoomOut:
			current = Math.max(0, current-1);
			break;
		}
		
		usingItem.getTagCompound().setInteger("zoom", current);
		
		StellarAPIReference.updateScope(player);
	}

}
