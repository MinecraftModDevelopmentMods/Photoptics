package abr.mod.photoptics.entity;

import abr.mod.photoptics.tileentity.TileEntityTelescopeBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.interact.IViewScopeSimulatorEntity;
import stellarapi.api.optics.IViewScope;

public class EntityTelescopeSimulator extends Entity implements IViewScopeSimulatorEntity {
	
	private EntityPlayer usingPlayer;
	private TileEntityTelescopeBase telescope;

	public EntityTelescopeSimulator(World world, TileEntityTelescopeBase telescope) {
		super(world);
		this.telescope = telescope;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean setPlayer(EntityPlayer player) {
		if(this.usingPlayer != null)
			return false;
		
		this.usingPlayer = player;
		usingPlayer.mountEntity(this);
		
		StellarAPIReference.updateScope(player);
		return true;
	}
	
	public boolean removePlayer(EntityPlayer player) {
		if(this.usingPlayer != player)
			return false;
		usingPlayer.dismountEntity(this);
		this.usingPlayer = null;
		
		StellarAPIReference.updateScope(player);
		return true;
	}

	@Override
	public IViewScope getScope(EntityPlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

}
