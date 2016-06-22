package abr.mod.photoptics.processing;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import stellarapi.api.celestials.ICelestialObject;

public class PlayerObservationData implements IObservationData {

	private Multiset<String> observedSet = HashMultiset.create();
	private Multiset<String> limitSet = HashMultiset.create();

	public boolean onObserve(ICelestialObject object) {
		if(!limitSet.contains(object.getName()))
			limitSet.add(object.getName());

		if(observedSet.count(object.getName()) < limitSet.count(object.getName())) {
			observedSet.add(object.getName());
			return true;
		} else return false;
	}

	@Override
	public NBTBase serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		for(String observed : this.limitSet) {
			NBTTagCompound observedInfo = new NBTTagCompound();
			observedInfo.setString("name", observed);
			observedInfo.setShort("count", (short) observedSet.count(observed));
			observedInfo.setShort("limit", (short) limitSet.count(observed));

			tagList.appendTag(observedInfo);
		}
		compound.setTag("observedInfo", tagList);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		observedSet.clear();
		limitSet.clear();

		NBTTagList tagList = ((NBTTagCompound)nbt).getTagList("observedInfo", 10);
		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound observedInfo = tagList.getCompoundTagAt(i);
			String observed = observedInfo.getString("name");
			observedSet.setCount(observed, observedInfo.getShort("count"));
			limitSet.setCount(observed, observedInfo.getShort("limit"));
		}
	}

	@Override
	public void reset(String objectID, short count) {
		observedSet.setCount(objectID, count);
		if(limitSet.count(objectID) < count)
			limitSet.setCount(objectID, count);
	}

	@Override
	public void setLimit(String objectID, short limit) {
		limitSet.setCount(objectID, limit);
	}

}
