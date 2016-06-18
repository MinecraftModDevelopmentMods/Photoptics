package abr.mod.photoptics.processing;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property.Type;
import stellarapi.api.lib.config.INBTConfig;
import stellarapi.api.lib.config.SimpleNBTConfig;
import stellarapi.api.lib.config.property.ConfigProperty;
import stellarapi.api.lib.config.property.ConfigPropertyList;

public class PossibleObservations extends SimpleNBTConfig {

	public static final String categoryName = "Observations";
	private static final PossibleObservations observations = new PossibleObservations();

	public static PossibleObservations instance() {
		return observations;
	}

	private ConfigPropertyStringMap rewardProperty;

	public PossibleObservations() {
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.put("Sun", "/effect @p 15 3 1");
		builder.put("Moon", "/summon Enderman");

		builder.put("Cep0Mu", "/give @p redstone 16");//photoptics.binoculars.golden
		builder.put("Lyr4Eps", "/give @p iron_pickaxe 2");

		builder.put("Andromeda Galaxy", "/give @p milk_bucket");
		builder.put("Messier 15", "/give @p snowball 16");
		builder.put("Gem5", "/give @p iron 16");
		builder.put("Pup2", "/give @p iron 16");
		builder.put("Aur24Phi", "/give @p iron 16");
		builder.put("Per7", "/give @p bone 8");
		builder.put("Orion Nebula", "/give @p carrot 2");
		builder.put("Crab Nebula", "/give @p nether_star");
		this.rewardProperty = new ConfigPropertyStringMap("Observation_Rewards", "observationRewards", builder.build());
		
		this.addConfigProperty(this.rewardProperty);
	}

	@Override
	public void setupConfig(Configuration config, String category) {
		config.setCategoryLanguageKey(category, "config.photoptics.category.observations");
		config.setCategoryComment(category, "Configuration for rewards when certain object is observed.");
		config.setCategoryRequiresWorldRestart(category, true);

		super.setupConfig(config, category);
		
		rewardProperty.setLanguageKey("config.photoptics.property.observations.rewardmap");
		rewardProperty.setComment("Rewards for Observation of objects.\n"
				+ "Each line should be in form of '(object name):(reward command)'.");
		rewardProperty.setRequiresWorldRestart(true);
	}
	
	public Map<String, String> entryMap() {
		return rewardProperty.currentMap;
	}


	private class ConfigPropertyStringMap extends ConfigPropertyList {
		private Map<String, String> defaultMap;
		private Map<String, String> currentMap;

		public ConfigPropertyStringMap(String configKey, String dataKey, Map<String, String> defaultValue) {
			super(configKey, dataKey);
			this.defaultMap = defaultValue;
			this.setAsDefault();
		}
		
		@Override
		protected Type getType() {
			return Type.STRING;
		}
		
		private Function<Map.Entry<String, String>, String> merger = new Function<Map.Entry<String, String>, String>() {
			@Override
			public String apply(Entry<String, String> input) {
				return String.format("%s:%s", input.getKey(), input.getValue());
			}
		};

		@Override
		protected String[] getDefaultValues() {
			return Collections2.transform(defaultMap.entrySet(), this.merger).toArray(new String[0]);
		}

		@Override
		protected void resizeValuesToLength(boolean fixed, int length) { }

		@Override
		public void setAsDefault() {
			this.currentMap = Maps.newHashMap(this.defaultMap);
		}

		@Override
		public void setAsProperty(ConfigProperty property) {
			if(property instanceof ConfigPropertyStringMap) {
				this.currentMap = Maps.newHashMap(((ConfigPropertyStringMap) property).currentMap);
			}
		}

		@Override
		public void loadFromConfig() {
			String[] strList = property.getStringList();
			currentMap.clear();
			for(String entry : strList) {
				int index = entry.indexOf(':');
				currentMap.put(entry.substring(0, index), entry.substring(index + 1));
			}
		}

		@Override
		public void saveToConfig() {
			property.set(Collections2.transform(currentMap.entrySet(), this.merger).toArray(new String[0]));
		}

		@Override
		public void readFromNBT(NBTTagCompound compound) {
			NBTTagCompound tag = compound.getCompoundTag(this.dataKey);

			currentMap.clear();
			for(Object keyobj : tag.func_150296_c()) {
				String key = (String) keyobj;
				currentMap.put(key, tag.getString(key));
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound compound) {
			NBTTagCompound tag = new NBTTagCompound();
			for(Entry<String, String> entry : currentMap.entrySet())
				tag.setString(entry.getKey(), entry.getValue());

			compound.setTag(this.dataKey, tag);
		}
	}

	@Override
	public INBTConfig copy() {
		PossibleObservations copied = new PossibleObservations();
		copied.applyCopy(this);
		return copied;
	}

}
