package abr.mod.photoptics.processing;

import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

import abr.mod.photoptics.Photoptics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import stellarapi.api.CelestialPeriod;
import stellarapi.api.PeriodHelper;
import stellarapi.api.celestials.ICelestialObject;

public class PlayerObservationData implements IObservationData {

	private Multiset<String> observedSet = HashMultiset.create();
	private Multiset<String> limitSet = HashMultiset.create();
	private Map<String, Long> lastTickObserved = Maps.newHashMap();
	private Map<String, Long> neededTickDuration = Maps.newHashMap();
	
	private long getUniversalTick() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTotalWorldTime();
	}

	public boolean onObserve(ICelestialObject object, EntityPlayer player) {
		String id = object.getName();
		
		if(!limitSet.contains(id))
			limitSet.add(id);
		
		long currentTick = this.getUniversalTick();

		if(!neededTickDuration.containsKey(id))
			neededTickDuration.put(id, Long.valueOf(20L));

		if(lastTickObserved.containsKey(id) && lastTickObserved.get(id) + neededTickDuration.get(id) > currentTick) {
			long remainTick = lastTickObserved.get(id) + neededTickDuration.get(id) - currentTick;

			CelestialPeriod period = PeriodHelper.getDayPeriod(player.getEntityWorld());
			if(period != null && remainTick > period.getPeriodLength()) {
				long day = (long) period.getPeriodLength();
				long dayLeft = remainTick / day;
				long secondLeft = (remainTick % day) / 20;
				player.addChatComponentMessage(this.withStyle(new TextComponentTranslation("info.photoptics.observe.timeleftds", id, dayLeft, secondLeft)));
			} else {
				long secondLeft = remainTick / 20L;
				long tickLeft = remainTick % 20L;
				if(secondLeft > 0)
					player.addChatComponentMessage(this.withStyle(new TextComponentTranslation("info.photoptics.observe.timeleftst", id, secondLeft, tickLeft)));
				else player.addChatComponentMessage(this.withStyle(new TextComponentTranslation("info.photoptics.observe.timeleftt", id, tickLeft)));
			}

			return false;
		}

		if(observedSet.count(id) < limitSet.count(id)) {
			observedSet.add(id);
			lastTickObserved.put(id, currentTick);
			return true;
		} else {
			player.addChatComponentMessage(this.withStyle(new TextComponentTranslation("info.photoptics.observe.reachlimit", id, limitSet.count(id))));
			return false;
		}
	}
	
	private ITextComponent withStyle(ITextComponent component) {
		return component.setStyle(component.getStyle().setColor(TextFormatting.GRAY).setItalic(true));
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
			observedInfo.setLong("time", lastTickObserved.get(observed).longValue());
			observedInfo.setLong("duration", neededTickDuration.get(observed).longValue());

			tagList.appendTag(observedInfo);
		}
		compound.setTag("observedInfo", tagList);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		observedSet.clear();
		limitSet.clear();
		lastTickObserved.clear();

		NBTTagList tagList = ((NBTTagCompound)nbt).getTagList("observedInfo", 10);
		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound observedInfo = tagList.getCompoundTagAt(i);
			String observed = observedInfo.getString("name");
			observedSet.setCount(observed, observedInfo.getShort("count"));
			limitSet.setCount(observed, observedInfo.getShort("limit"));
			lastTickObserved.put(observed, observedInfo.getLong("time"));
			neededTickDuration.put(observed, observedInfo.getLong("duration"));
		}
	}

	@Override
	public void reset(String objectID, short count) {
		observedSet.setCount(objectID, count);
		Photoptics.logger.info(String.format("Count reset for %s to %d", objectID, count));
		if(limitSet.count(objectID) < count)
			limitSet.setCount(objectID, count);
	}

	@Override
	public void setLimit(String objectID, short limit) {
		limitSet.setCount(objectID, limit);
	}

	@Override
	public void setWaitDuration(String objectID, long waitDuration) {
		neededTickDuration.put(objectID, Long.valueOf(waitDuration));
	}

}
