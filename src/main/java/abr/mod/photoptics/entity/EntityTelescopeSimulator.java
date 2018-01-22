package abr.mod.photoptics.entity;

import abr.mod.photoptics.tileentity.TileEntityTelescopeBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import stellarapi.api.SAPIReferences;

public class EntityTelescopeSimulator extends Entity {

	private EntityPlayer usingPlayer;
	private TileEntityTelescopeBase telescope;

	public EntityTelescopeSimulator(World world, TileEntityTelescopeBase telescope) {
		super(world);
		this.telescope = telescope;
	}

	@Override
	protected void entityInit() {
		// TODO Entity Init

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO read entity

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO write entity

	}

	public boolean setPlayer(EntityPlayer player) {
		if(this.usingPlayer != null)
			return false;

		this.usingPlayer = player;
		usingPlayer.startRiding(player, true);

		SAPIReferences.updateScope(player);
		return true;
	}

	public boolean removePlayer(EntityPlayer player) {
		if(this.usingPlayer != player)
			return false;
		usingPlayer.dismountEntity(this);
		this.usingPlayer = null;

		SAPIReferences.updateScope(player);
		return true;
	}

}
