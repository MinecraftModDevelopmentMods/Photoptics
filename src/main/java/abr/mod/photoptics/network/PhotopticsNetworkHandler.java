package abr.mod.photoptics.network;

import abr.mod.photoptics.EnumPhotopticsKeys;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PhotopticsNetworkHandler {
	
	private static final String channel = "Photoptics";
	
	private SimpleNetworkWrapper wrapper;
	
	public PhotopticsNetworkHandler() {
		this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(channel);
	}
	
	public void register() {
		wrapper.registerMessage(KeyInputHandleMessage.KeyInputHandler.class,
				KeyInputHandleMessage.class, 0, Side.SERVER);
	}
	
	public void sendKeyInput(EnumPhotopticsKeys key) {
		wrapper.sendToServer(new KeyInputHandleMessage(key));
	}

}
