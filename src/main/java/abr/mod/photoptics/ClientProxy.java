package abr.mod.photoptics;

import abr.mod.photoptics.item.PhotopticsItems;
import abr.mod.photoptics.render.RenderEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
		OBJLoader.instance.addDomain(Photoptics.resourceid);
		ModelLoader.setCustomModelResourceLocation(PhotopticsItems.basicBinoculars,
				0, new ModelResourceLocation(Photoptics.resourceid + ":" + "binoculars", "inventory"));
		ModelLoader.setCustomModelResourceLocation(PhotopticsItems.basicHandheldTelescope,
				0, new ModelResourceLocation(Photoptics.resourceid + ":" + "handheldtelescope", "inventory"));
	}
	
	@Override
	public void forcePerspective() {
		Minecraft.getMinecraft().gameSettings.hideGUI = false;
		Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
	}
	
	@Override
	public void registerTask(Runnable task) {
		Minecraft.getMinecraft().addScheduledTask(task);
	}

}
