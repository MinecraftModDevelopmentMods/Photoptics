package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		this.onUse(player, hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		this.onUse(player, hand);
		return EnumActionResult.SUCCESS;
    }
	
	public void onUse(EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
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
