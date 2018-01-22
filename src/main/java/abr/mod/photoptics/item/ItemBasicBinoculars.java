package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import abr.mod.photoptics.render.overlay.OverlayBinocularsRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stellarapi.api.SAPIReferences;
import stellarapi.api.optics.IOpticalFilter;
import stellarapi.api.optics.IViewScope;
import stellarapi.api.optics.NakedScope;
import stellarapi.api.optics.Wavelength;

public class ItemBasicBinoculars extends ItemTelescopeBase {
	
	public static final int maxZoom = 5;
	
	private class BinocularsOpticalProperty implements ITelescopeProperty {
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
					return 20.0 * getTelescopeMaterial().lumMultiplier;
				}

				@Override
				public double getResolution(Wavelength wl) {
					return NakedScope.DEFAULT_RESOLUTION * 0.6 / Math.sqrt((1.0 + zoom / 10.0)) / getTelescopeMaterial().zoomMultiplier;
				}

				@Override
				public double getMP() {
					return 3.0 * (1.0 + zoom / 10.0) * getTelescopeMaterial().zoomMultiplier;
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
			if(key == EnumPhotopticsKeys.ZoomIn && this.zoom < maxZoom) {
				this.zoom++;
				SAPIReferences.updateScope(controller);
			} else if(key == EnumPhotopticsKeys.ZoomOut && this.zoom > 0) {
				this.zoom--;
				SAPIReferences.updateScope(controller);
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
		return new OverlayBinocularsRenderer();
	}

	@Override
	public ITelescopeProperty getOpticalProperty(ItemStack stack) {
		return new BinocularsOpticalProperty();
	}
}