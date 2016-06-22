package abr.mod.photoptics.processing;

import net.minecraftforge.common.util.INBTSerializable;
import stellarapi.api.celestials.ICelestialObject;

public interface IObservationData extends INBTSerializable {

	public static final String ID = "ObservationData_v2";

	/**
	 * Adds the object with the name to be observed when it wasn't.
	 * Gives false iff. it has already been observed.
	 * */
	public boolean onObserve(ICelestialObject object);
	
	/**
	 * Resets count for the object.
	 * */
	public void reset(String objectID, short count);
	
	/**
	 * Sets the count limit for the object.
	 * */
	public void setLimit(String objectID, short limit);
}
