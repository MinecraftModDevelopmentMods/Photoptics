package abr.mod.photoptics;

import abr.mod.photoptics.item.PhotopticsItems;
import abr.mod.photoptics.render.RenderEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
		FMLCommonHandler.instance().bus().register(new PhotopticsKeybindings());
		this.registerItemRenderers();
	}

	public void registerItemRenderers() {
		OBJLoader.INSTANCE.addDomain(Photoptics.resourceid);
		for(Item binocular : PhotopticsItems.INSTANCE.binocularsList) {
			ModelLoader.setCustomModelResourceLocation(binocular,
					0, new ModelResourceLocation(Photoptics.resourceid + ":" + "binoculars", "inventory"));
		}

		for(Item handheldTelescope : PhotopticsItems.INSTANCE.handheldTelescopeList) {
			ModelLoader.setCustomModelResourceLocation(handheldTelescope,
					0, new ModelResourceLocation(Photoptics.resourceid + ":" + "handheldtelescope", "inventory"));
		}
	}

	@Override
	public void forcePerspective(EntityLivingBase viewer) {
		if(!(viewer instanceof EntityPlayerSP))
			return;

		Minecraft.getMinecraft().gameSettings.hideGUI = false;
		Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
	}
	
	@Override
	public void registerTask(Runnable task) {
		Minecraft.getMinecraft().addScheduledTask(task);
	}

}
