package abr.mod.photoptics.render.item;

import org.lwjgl.opengl.GL11;

import abr.mod.photoptics.Photoptics;
import abr.mod.photoptics.item.ItemBasicHandheldTelescope;
import abr.mod.photoptics.item.TelescopeMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemHandheldTelescopeRenderer implements IItemRenderer {
	
	private IModelCustom model = AdvancedModelLoader.loadModel(
			new ResourceLocation(Photoptics.resourceid, "textures/items/handheldtelescope.obj"));
	private ResourceLocation modelTexture;
	
	public ItemHandheldTelescopeRenderer(TelescopeMaterial material) {
		this.modelTexture = material.textureLocation;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
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
		
		if(!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
			item.getTagCompound().setInteger("zoom", 0);
		}
		
		int zoom = item.getTagCompound().getInteger("zoom");
		double retro = 1.0 - zoom / (double)ItemBasicHandheldTelescope.maxZoom;
		
		GL11.glPushMatrix();
		if(type != ItemRenderType.INVENTORY)
		{
			GL11.glTranslated(0.3, 0.0, 0.0);
			GL11.glScaled(0.01, 0.01, 0.01);
			GL11.glRotated(180.0, 0.0, 1.0, 0.0);
			GL11.glRotated(-25.0, 1.0, 0.0, 0.0);
			GL11.glRotated(15.0, 1.0, 0.0, 1.0);
		}
		else {
			GL11.glTranslated(-1.2, -1.0, 0.0);
			GL11.glScaled(0.018, 0.018, 0.018);
			GL11.glRotated(180.0, 0.0, 1.0, 0.0);
			GL11.glRotated(90.0, 1.0, 0.0, 0.0);
		}
		
		model.renderPart("Eyepiece_part");
		GL11.glTranslated(0.0, -retro*32.0, 0.0);
		model.renderPart("Medium_part_1");
		GL11.glTranslated(0.0, -retro*32.0, 0.0);
		model.renderPart("Medium_part_2");
		model.renderPart("Objective_part");

		GL11.glPopMatrix();
		
		texturemanager.bindTexture(texturemanager.getResourceLocation(1));
	}

}
