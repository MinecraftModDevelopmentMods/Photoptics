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
}
