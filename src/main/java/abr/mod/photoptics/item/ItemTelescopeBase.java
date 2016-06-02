package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import stellarapi.api.StellarAPIReference;
import stellarapi.api.interact.IOpticalFilterItem;
import stellarapi.api.interact.IViewScopeItem;
import stellarapi.api.optics.IOpticalFilter;
import stellarapi.api.optics.Wavelength;

public abstract class ItemTelescopeBase extends Item implements IViewScopeItem, IOpticalFilterItem {
	
	private TelescopeMaterial material;
	
	public Item setTelescopeMaterial(TelescopeMaterial material) {
		this.material = material;
		return this;
	}
	
	public TelescopeMaterial getTelescopeMaterial() {
		return this.material;
	}
	
	@Override
	public IOpticalFilter getFilter(EntityPlayer player, ItemStack stack) {
		return new IOpticalFilter() {
			@Override
			public double getFilterEfficiency(Wavelength wavelength) {
				return material.filterProperty.apply(wavelength);
			}
		};
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		this.onUse(stack, player);
		return stack;
	}
	
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		this.onUse(stack, player);
    	return true;
    }
	
	public void onUse(ItemStack stack, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		
		StellarAPIReference.updateScope(player);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int slot)
    {
		player.clearItemInUse();
		StellarAPIReference.updateScope(player);
    }
	
	@Override
	public boolean isSame(ItemStack arg0, ItemStack arg1) {
		return arg0.getItem() == arg1.getItem();
	}
		
	/**
	 * Triggers on both side.
	 * */
	public abstract void keyControl(EnumPhotopticsKeys zoomin, EntityPlayer player, ItemStack usingItem);
	
	@SideOnly(Side.CLIENT)
	public abstract IOverlayRenderer getOverlayRenderer(ItemStack stack);

}
