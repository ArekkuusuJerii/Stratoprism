package arekkuusu.stratoprism.api.item.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IVastatioCapability {
	float getVastatio();
	float getMaxVastatio();
	void setVastatio(EntityPlayer stack, float vastatio);
	void setMaxVastatio(float maxVastatio);
	NBTTagCompound saveNBTData();
	void loadNBTData(NBTTagCompound compound);
	void dataChanged(EntityPlayer stack);
}
