package abr.mod.photoptics.render;

import org.lwjgl.opengl.GL11;

import abr.mod.photoptics.item.ItemTelescopeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stellarapi.api.helper.LivingItemAccessHelper;

public class RenderEventHandler {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack usingItem = mc.thePlayer.getActiveItemStack();
		ItemStack offhandItem = mc.thePlayer.getHeldItemOffhand();
		ItemStack mainhandItem = mc.thePlayer.getHeldItemMainhand();

		if(mainhandItem == null || !(mainhandItem.getItem() instanceof ItemTelescopeBase)) {
			if(offhandItem != null && offhandItem.getItem() instanceof ItemTelescopeBase)
				usingItem = offhandItem;
		}
		
		if(usingItem != null && usingItem.getItem() instanceof ItemTelescopeBase)
		{
			mc.entityRenderer.setupOverlayRendering();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			ItemTelescopeBase item = (ItemTelescopeBase) usingItem.getItem();
			item.getOverlayRenderer(usingItem).renderOverlay(event.getResolution(), event.getPartialTicks());
			GlStateManager.disableBlend();
			
			event.setCanceled(true);
		}
	}
}
