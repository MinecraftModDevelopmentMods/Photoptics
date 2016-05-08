package abr.mod.photoptics;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import abr.mod.photoptics.item.PhotopticsItems;
import abr.mod.photoptics.network.PhotopticsNetworkHandler;
import abr.mod.photoptics.processing.PhotopticsRecipes;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import stellarapi.api.StellarAPIReference;

@Mod(modid=Photoptics.modid, version=Photoptics.version,
dependencies="required-after:StellarAPI@[0.1.2.1, 0.1.3.0)", guiFactory="abr.mod.photoptics.config.POConfigGuiFactory")
public class Photoptics {
	public static final String modid = "Photoptics";
	public static final String resourceid = "photoptics";
	public static final String version = "@VERSION@";

    // The instance of Photoptics
    @Instance(Photoptics.modid)
    public static Photoptics instance;
    
    @SidedProxy(clientSide="abr.mod.photoptics.ClientProxy", serverSide="abr.mod.photoptics.CommonProxy")
    public static CommonProxy proxy;
    
    public static Logger logger;
    
    private PhotopticsNetworkHandler networkHandler = new PhotopticsNetworkHandler();
    
    public PhotopticsNetworkHandler getNetworkHandler() {
    	return this.networkHandler;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) { 
    	logger = event.getModLog();
    	
    	StellarAPIReference.getEventBus().register(new StellarAPIEventHandler());
    	FMLCommonHandler.instance().bus().register(new TickEventHandler());
    	networkHandler.register();
    	
    	PhotopticsItems.init();
    	
    	proxy.registerRenderers();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) throws IOException {
    	PhotopticsRecipes.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) { }
}
