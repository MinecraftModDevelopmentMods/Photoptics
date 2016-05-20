package abr.mod.photoptics.render.overlay;

import abr.mod.photoptics.Photoptics;
import abr.mod.photoptics.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class OverlayBinocularsRenderer implements IOverlayRenderer {
	
	private final double imgWidthPerHeight = 1.5;
	
	private final ResourceLocation overlayTexture = new ResourceLocation(
			Photoptics.resourceid, "textures/overlays/binoculars_overlay.png");

	@Override
	public void renderOverlay(ScaledResolution resolution, float partialTicks) {
		double width = resolution.getScaledWidth_double();
		double height = resolution.getScaledHeight_double();
		Minecraft mc = Minecraft.getMinecraft();
		mc.renderEngine.bindTexture(this.overlayTexture);
		double imgWidth = (width + height * this.imgWidthPerHeight) * 0.6;
		double imgHeight = imgWidth / this.imgWidthPerHeight;
		RenderHelper.drawTexturedRect((width - imgWidth)/2, (height - imgHeight)/2, imgWidth, imgHeight);
	}

}
