package abr.mod.photoptics;

import net.minecraft.server.MinecraftServer;

public class CommonProxy {

	public void registerRenderers() { }
	
	public void forcePerspective() { }

	public void registerTask(Runnable task) {
		MinecraftServer.getServer().addScheduledTask(task);
	}


}
