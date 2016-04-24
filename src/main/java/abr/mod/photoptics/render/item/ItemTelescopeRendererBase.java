package abr.mod.photoptics.render.item;

import org.lwjgl.opengl.GL11;

import abr.mod.photoptics.Photoptics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemTelescopeRendererBase implements IItemRenderer {
	
	private IModelCustom model = AdvancedModelLoader.loadModel(
			new ResourceLocation(Photoptics.resourceid, "textures/items/binoculars.obj"));
	private ResourceLocation modelTexture =
			new ResourceLocation(Photoptics.resourceid, "textures/items/basic_binoculars_model.png");
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		//return type != ItemRenderType.INVENTORY;
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		if(type == ItemRenderType.INVENTORY && helper == ItemRendererHelper.INVENTORY_BLOCK)
			return true;
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		texturemanager.bindTexture(this.modelTexture);
				
		GL11.glPushMatrix();
		if(type != ItemRenderType.INVENTORY)
		{
			GL11.glTranslated(0.5, 0.0, 0.0);
			GL11.glScaled(0.01, 0.01, 0.01);
			GL11.glRotated(180.0, 0.0, 1.0, 0.0);
		}
		else {
			GL11.glTranslated(-0.75, -0.75, 0.0);
			GL11.glScaled(0.018, 0.018, 0.018);
			GL11.glRotated(180.0, 0.0, 1.0, 0.0);
			GL11.glRotated(90.0, 1.0, 0.0, 0.0);
		}
		model.renderAll();
		GL11.glPopMatrix();
		
		texturemanager.bindTexture(texturemanager.getResourceLocation(1));
	}

}
