/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.api.item.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class VastatioCapabilityStorage implements Capability.IStorage<IVastatioCapability>{

	public static final VastatioCapabilityStorage cap = new VastatioCapabilityStorage();

	@Override
	public NBTBase writeNBT(Capability<IVastatioCapability> capability, IVastatioCapability instance, EnumFacing side) {
		NBTTagCompound nbt= new NBTTagCompound();
		nbt.setFloat("vastatio", ((DefaultVastatioCapability)instance).vastatio);
		nbt.setFloat("max_vastatio", ((DefaultVastatioCapability)instance).maxVastatio);
		return nbt;
	}

	@Override
	public void readNBT(Capability<IVastatioCapability> capability, IVastatioCapability instance, EnumFacing side,NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		((DefaultVastatioCapability)instance).vastatio = tag.getFloat("vastatio");
		((DefaultVastatioCapability)instance).maxVastatio = tag.getFloat("max_vastatio");
	}
}
