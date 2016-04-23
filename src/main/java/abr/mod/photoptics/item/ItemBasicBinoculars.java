package abr.mod.photoptics.item;

import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import abr.mod.photoptics.render.overlay.OverlayBinocularsRenderer;
import net.minecraft.item.ItemStack;
import stellarapi.api.optics.IViewScope;
import stellarapi.api.optics.Wavelength;

public class ItemBasicBinoculars extends ItemTelescopeBase {
	
	private IViewScope scope = new IViewScope() {

		@Override
		public double getLGP() {
			return 20.0;
		}

		@Override
		public double getResolution(Wavelength wl) {
			return 0.2;
		}

		@Override
		public double getMP() {
			return 3.0;
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

	@Override
	public IViewScope getScope(ItemStack stack) {
		return this.scope;
	}

	@Override
	public IOverlayRenderer getOverlayRenderer(ItemStack stack) {
		return new OverlayBinocularsRenderer();
	}

}
