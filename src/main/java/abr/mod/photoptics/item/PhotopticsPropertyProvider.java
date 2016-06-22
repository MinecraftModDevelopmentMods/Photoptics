package abr.mod.photoptics.item;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import stellarapi.api.StellarAPICapabilities;
import stellarapi.api.interact.IOpticalProperties;

public class PhotopticsPropertyProvider implements ICapabilityProvider, INBTSerializable {

	private final ITelescopeProperty opticalProperty;
	
	public PhotopticsPropertyProvider(ITelescopeProperty opticalProperty) {
		this.opticalProperty = opticalProperty;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == StellarAPICapabilities.OPTICAL_PROPERTY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == StellarAPICapabilities.OPTICAL_PROPERTY)
			return StellarAPICapabilities.OPTICAL_PROPERTY.cast(this.opticalProperty);
		else return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return opticalProperty.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		opticalProperty.deserializeNBT(nbt);
	}

}
