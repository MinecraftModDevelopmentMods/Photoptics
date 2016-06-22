package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.PhotopticsTelescopeHandler;
import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import abr.mod.photoptics.render.overlay.OverlayTelescopeRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.interact.IOpticalProperties;
import stellarapi.api.lib.math.Spmath;
import stellarapi.api.optics.IOpticalFilter;
import stellarapi.api.optics.IViewScope;
import stellarapi.api.optics.NakedScope;
import stellarapi.api.optics.Wavelength;

public class ItemBasicHandheldTelescope extends ItemTelescopeBase {
	
	private static final int maxZoomInitial = 20;
	
	public int getMaxZoom() {
		return this.maxZoomInitial;
	}
	
	private class HandheldTelescopeOpticalProperty implements ITelescopeProperty {
		private byte zoom;
		
		@Override
		public boolean isFilter() {
			return true;
		}

		@Override
		public IOpticalFilter getFilter(EntityLivingBase viewer) {
			return new IOpticalFilter() {
				@Override
				public double getFilterEfficiency(Wavelength wavelength) {
					return getTelescopeMaterial().filterProperty.apply(wavelength);
				}
			};
		}

		@Override
		public boolean isScope() {
			return true;
		}

		@Override
		public IViewScope getScope(EntityLivingBase viewer) {
			return new IViewScope() {

				@Override
				public double getLGP() {
					double mult = getTelescopeMaterial().lumMultiplier;
					if(getTelescopeMaterial().zoomMultiplier >= 2.0)
						mult *= Spmath.sqr(1.0 + zoom / 15.0);

					return 30.0 * mult;
				}

				@Override
				public double getResolution(Wavelength wl) {
					double zoomFactor = (1.0 + zoom / 15.0);
					
					return NakedScope.DEFAULT_RESOLUTION * 0.6 / Math.sqrt(zoomFactor) / getTelescopeMaterial().zoomMultiplier;
				}

				@Override
				public double getMP() {
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

		@Override
		public void keyControl(EnumPhotopticsKeys key, EntityPlayer controller) {
			if(key == EnumPhotopticsKeys.ZoomIn && this.zoom < getMaxZoom()) {
				this.zoom++;
				StellarAPIReference.updateScope(controller);
			} else if(key == EnumPhotopticsKeys.ZoomOut && this.zoom > 0) {
				this.zoom--;
				StellarAPIReference.updateScope(controller);
			} else if(key == EnumPhotopticsKeys.Observe){
				PhotopticsTelescopeHandler.onObserve(controller, 0.7 / getTelescopeMaterial().zoomMultiplier);
			}
		}

		@Override
		public NBTBase serializeNBT() {
			return new NBTTagByte(this.zoom);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			this.zoom = ((NBTPrimitive)nbt).getByte();
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IOverlayRenderer getOverlayRenderer(ItemStack stack) {
		return new OverlayTelescopeRenderer();
	}

	@Override
	public IOpticalProperties getOpticalProperty(ItemStack stack) {
		return new HandheldTelescopeOpticalProperty();
	}
}
