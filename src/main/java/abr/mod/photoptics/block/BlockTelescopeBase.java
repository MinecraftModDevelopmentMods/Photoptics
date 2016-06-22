package abr.mod.photoptics.block;

import abr.mod.photoptics.tileentity.TileEntityTelescopeBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTelescopeBase extends BlockContainer {

	public BlockTelescopeBase(Material material) {
		super(material);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileEntityTelescopeBase) {
			TileEntityTelescopeBase telescope = (TileEntityTelescopeBase) tile;
			return telescope.onActivatedBy(world, player, hitX, hitY, hitZ);
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		// TODO Create TileEntity
		return null;
	}
	

}
