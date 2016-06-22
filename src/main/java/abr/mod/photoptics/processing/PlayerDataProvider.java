package abr.mod.photoptics.processing;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerDataProvider implements ICapabilityProvider, INBTSerializable {

	@CapabilityInject(IObservationData.class)
	public static final Capability<IObservationData> OBSERVATION_DATA = null;
	private IObservationData observationData = new PlayerObservationData();

	static {
		CapabilityManager.INSTANCE.register(IObservationData.class,
				new Capability.IStorage<IObservationData>() {
					@Override
					public NBTBase writeNBT(Capability<IObservationData> capability, IObservationData instance, EnumFacing side) { return instance.serializeNBT(); }
					@Override
					public void readNBT(Capability<IObservationData> capability, IObservationData instance, EnumFacing side, NBTBase nbt) { instance.deserializeNBT(nbt); }
		}, PlayerObservationData.class);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == OBSERVATION_DATA;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == OBSERVATION_DATA)
			return OBSERVATION_DATA.cast(this.observationData);
		else return null;
	}

	@Override
	public NBTBase serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag(IObservationData.ID, observationData.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NBTTagCompound comp = (NBTTagCompound) nbt;
		observationData.deserializeNBT(comp.getCompoundTag(IObservationData.ID));
	}

}
