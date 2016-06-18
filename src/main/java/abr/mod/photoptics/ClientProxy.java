package abr.mod.photoptics;

import abr.mod.photoptics.item.ItemTelescopeBase;
import abr.mod.photoptics.item.PhotopticsItems;
import abr.mod.photoptics.render.RenderEventHandler;
import abr.mod.photoptics.render.item.ItemBinocularsRenderer;
import abr.mod.photoptics.render.item.ItemHandheldTelescopeRenderer;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderers() {
		MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
		FMLCommonHandler.instance().bus().register(new PhotopticsKeybindings());
		this.registerItemRenderers();
	}

	public void registerItemRenderers() {
		for(Item binoculars : PhotopticsItems.INSTANCE.binocularsList)
			MinecraftForgeClient.registerItemRenderer(binoculars,
					new ItemBinocularsRenderer(((ItemTelescopeBase) binoculars).getTelescopeMaterial()));
		for(Item handheldTelescope : PhotopticsItems.INSTANCE.handheldTelescopeList)
			MinecraftForgeClient.registerItemRenderer(handheldTelescope,
					new ItemHandheldTelescopeRenderer(((ItemTelescopeBase) handheldTelescope).getTelescopeMaterial()));
	}

	public void forcePerspective() {
		Minecraft.getMinecraft().gameSettings.hideGUI = false;
		Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
	}

}
