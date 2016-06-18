package abr.mod.photoptics.processing;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerObservationData implements IExtendedEntityProperties {
	
	private static final String ID = "ObservationData_v1";
	
	private Set<String> observedSet = Sets.newHashSet();
	
	public static void registerData(EntityPlayer player) {
		PlayerObservationData data = new PlayerObservationData();
		player.registerExtendedProperties(ID, data);
	}
	
	public static PlayerObservationData getData(EntityPlayer player) {
		return (PlayerObservationData) player.getExtendedProperties(ID);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("observedSet", 8);
		for(int i = 0; i < tagList.tagCount(); i++) {
			observedSet.add(tagList.getStringTagAt(i));
		}
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		for(String observed : this.observedSet)
			tagList.appendTag(new NBTTagString(observed));
		compound.setTag("observedSet", tagList);
	}
	
	/**
	 * Adds the object with the name to be observed when it wasn't.
	 * Gives false iff. it has already been observed.
	 * */
	public boolean onObserve(String name) {
		return observedSet.add(name);
	}

	@Override
	public void init(Entity entity, World world) { }

}
