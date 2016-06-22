package abr.mod.photoptics.item;

import abr.mod.photoptics.EnumPhotopticsKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import stellarapi.api.interact.IOpticalProperties;

public interface ITelescopeProperty extends IOpticalProperties, INBTSerializable {
	public void keyControl(EnumPhotopticsKeys key, EntityPlayer controller);
}