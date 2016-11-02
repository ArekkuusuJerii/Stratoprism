/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
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
