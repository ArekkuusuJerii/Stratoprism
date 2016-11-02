/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.api.item.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class VastatioProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

	@CapabilityInject(IVastatioCapability.class)
	public static final Capability<IVastatioCapability> VASTATIO_CAPABILITY = null;

	private IVastatioCapability vCapability = null;

	public VastatioProvider(){
		vCapability = new DefaultVastatioCapability();
	}

	public VastatioProvider(IVastatioCapability vCapability){
		this.vCapability = vCapability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return VASTATIO_CAPABILITY == capability;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (VASTATIO_CAPABILITY != null && capability == VASTATIO_CAPABILITY) return (T)vCapability;
		return null;
	}

	public static IVastatioCapability get(ItemStack stack) {
		return stack != null && stack.hasCapability(VASTATIO_CAPABILITY, null)? stack.getCapability(VASTATIO_CAPABILITY, null): null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return vCapability.saveNBTData();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		vCapability.loadNBTData(nbt);
	}
}
