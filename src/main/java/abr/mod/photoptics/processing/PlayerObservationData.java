package abr.mod.photoptics.processing;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import stellarapi.api.celestials.ICelestialObject;

public class PlayerObservationData implements IObservationData {

	private Set<String> observedSet = Sets.newHashSet();

	public boolean onObserve(ICelestialObject object) {
		return observedSet.add(object.getName());
	}

	@Override
	public NBTBase serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		for(String observed : this.observedSet)
			tagList.appendTag(new NBTTagString(observed));
		compound.setTag("observedSet", tagList);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NBTTagList tagList = ((NBTTagCompound)nbt).getTagList("observedSet", 8);
		for(int i = 0; i < tagList.tagCount(); i++) {
			observedSet.add(tagList.getStringTagAt(i));
		}
	}

}
