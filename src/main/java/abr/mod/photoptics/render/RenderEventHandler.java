package abr.mod.photoptics.render;

import org.lwjgl.opengl.GL11;

import abr.mod.photoptics.item.ItemTelescopeBase;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import stellarapi.api.helper.PlayerItemAccessHelper;

public class RenderEventHandler {
	
	@SubscribeEvent
	public void renderItemInHand(RenderHandEvent event) {
		ItemStack usingItem = PlayerItemAccessHelper.getUsingItem(Minecraft.getMinecraft().thePlayer);
		if(usingItem != null && usingItem.getItem() instanceof ItemTelescopeBase)
			event.setCanceled(true);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack usingItem = PlayerItemAccessHelper.getUsingItem(mc.thePlayer);
		if(usingItem != null && usingItem.getItem() instanceof ItemTelescopeBase)
		{
			mc.entityRenderer.setupOverlayRendering();
			GL11.glEnable(GL11.GL_BLEND);
			ItemTelescopeBase item = (ItemTelescopeBase) usingItem.getItem();
			item.getOverlayRenderer(usingItem).renderOverlay(event.resolution, event.partialTicks);
			
			event.setCanceled(true);
		}
	}

}
