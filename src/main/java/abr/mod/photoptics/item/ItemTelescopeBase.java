package abr.mod.photoptics.item;

import abr.mod.photoptics.render.overlay.IOverlayRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemTelescopeBase extends Item {

	private ResourceLocation registryName;
	private TelescopeMaterial material;
	
	public ItemTelescopeBase setTelescopeRegistryName(ResourceLocation registryName) {
		this.registryName = registryName;
		return this;
	}
	
	public void preRegister() {
		super.setRegistryName(this.registryName);
	}
	
	public ItemTelescopeBase setTelescopeMaterial(TelescopeMaterial material) {
		this.material = material;
		return this;
	}
	
	public TelescopeMaterial getTelescopeMaterial() {
		return this.material;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		player.setActiveHand(hand);
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
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound tag) {
		return new PhotopticsPropertyProvider(this.getOpticalProperty(stack));
	}
	
	public abstract ITelescopeProperty getOpticalProperty(ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	public abstract IOverlayRenderer getOverlayRenderer(ItemStack stack);

	public String getSubDomain() {
		return material.resourceName;
	}

}
