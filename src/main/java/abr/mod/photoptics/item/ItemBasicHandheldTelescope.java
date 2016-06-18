package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.PhotopticsTelescopeHandler;
import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import abr.mod.photoptics.render.overlay.OverlayTelescopeRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.lib.math.Spmath;
import stellarapi.api.optics.IViewScope;
import stellarapi.api.optics.NakedScope;
import stellarapi.api.optics.Wavelength;

public class ItemBasicHandheldTelescope extends ItemTelescopeBase {
	
	private int maxZoomInitial = 20;
	
	public int getMaxZoom() {
		return this.maxZoomInitial;
	}
	
	@Override
	public IViewScope getScope(EntityPlayer player, final ItemStack stack) {
		return new IViewScope() {

			@Override
			public double getLGP() {
				double mult = getTelescopeMaterial().lumMultiplier;
				if(getTelescopeMaterial().zoomMultiplier >= 2.0) {
					if(!stack.hasTagCompound()) {
						stack.setTagCompound(new NBTTagCompound());
						stack.getTagCompound().setInteger("zoom", 0);
					}
					
					int zoom = stack.getTagCompound().getInteger("zoom");
					mult *= Spmath.sqr(1.0 + zoom / 15.0);
				}

				return 30.0 * mult;
			}

			@Override
			public double getResolution(Wavelength wl) {
				if(!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setInteger("zoom", 0);
				}
				
				int zoom = stack.getTagCompound().getInteger("zoom");
				double zoomFactor = (1.0 + zoom / 15.0);
				
				return NakedScope.DEFAULT_RESOLUTION * 0.6 / Math.sqrt(zoomFactor) / getTelescopeMaterial().zoomMultiplier;
			}

			@Override
			public double getMP() {
				if(!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setInteger("zoom", 0);
				}
				
				int zoom = stack.getTagCompound().getInteger("zoom");
			
				return 3.0 * (1.0 + zoom / 15.0) * getTelescopeMaterial().zoomMultiplier;
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
		return new OverlayTelescopeRenderer();
	}

	@Override
	public void keyControl(EnumPhotopticsKeys key, EntityPlayer player, ItemStack usingItem) {
		if(!usingItem.hasTagCompound()) {
			usingItem.setTagCompound(new NBTTagCompound());
			usingItem.getTagCompound().setInteger("zoom", 0);
		}
		
		int current = usingItem.getTagCompound().getInteger("zoom");
		
		switch(key) {
		case ZoomIn:
			current = Math.min(this.getMaxZoom(), current+1);
			break;
		case ZoomOut:
			current = Math.max(0, current-1);
			break;
		case Observe:
			PhotopticsTelescopeHandler.onObserve(player, 0.7 / getTelescopeMaterial().zoomMultiplier);
			break;
		}
		
		usingItem.getTagCompound().setInteger("zoom", current);
		
		StellarAPIReference.updateScope(player);
	}
}
