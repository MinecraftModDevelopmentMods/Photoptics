package abr.mod.photoptics.tileentity;

import abr.mod.photoptics.entity.EntityTelescopeSimulator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityTelescopeBase extends TileEntity {

	private EntityTelescopeSimulator simulator;
	
    public void setWorldObj(World world) {
        this.world = world;
        this.simulator = new EntityTelescopeSimulator(this.world, this);
        world.spawnEntity(this.simulator);
    }


	public boolean onActivatedBy(World world, EntityPlayer player, float hitX, float hitY, float hitZ) {
		// TODO Activation Code
		if(simulator.setPlayer(player)) {
		} else if(simulator.removePlayer(player)) {
		}
		return true;
	}

}
