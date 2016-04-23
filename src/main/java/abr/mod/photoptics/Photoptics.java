package abr.mod.photoptics;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid=Photoptics.modid, version=Photoptics.version,
dependencies="required-after:StellarAPI@[0.1.1.4d, 0.1.2.0)", guiFactory="abr.mod.photoptics.config.POConfigGuiFactory")
public class Photoptics {
	public static final String modid = "stellarsky";
	public static final String version = "@VERSION@";

    // The instance of Stellarium
    @Instance(Photoptics.modid)
    public static Photoptics instance;
    
    @SidedProxy(clientSide="abr.mod.photoptics.ClientProxy", serverSide="abr.mod.photoptics.CommonProxy")
    public static CommonProxy proxy;
    
    public static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) { 
    	logger = event.getModLog();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) throws IOException {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) { }
}
