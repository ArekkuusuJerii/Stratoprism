/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.api.item.capability;

import arekkuusu.stratoprism.common.net.PacketHandler;
import arekkuusu.stratoprism.common.proxy.CommonProxy;
import arekkuusu.stratoprism.common.net.MessageVastatioUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DefaultVastatioCapability implements IVastatioCapability {

	public float maxVastatio = 1000;
	public float vastatio = 0;

	@Override
	public float getVastatio() {
		return vastatio;
	}

	@Override
	public float getMaxVastatio() {
		return maxVastatio;
	}

	@Override
	public void setVastatio(float vastatio) {
		this.vastatio = vastatio;
		if (vastatio < 0) {
			this.vastatio = 0;
		}
		if (vastatio > getMaxVastatio()) {
			this.vastatio = getMaxVastatio();
		}
	}

	@Override
	public void setMaxVastatio(float maxVastatio) {
		this.maxVastatio = maxVastatio;
	}

	@Override
	public NBTTagCompound saveNBTData() {
		return (NBTTagCompound) VastatioCapabilityStorage.cap.writeNBT(VastatioProvider.VASTATIO_CAPABILITY, this, null);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		VastatioCapabilityStorage.cap.readNBT(VastatioProvider.VASTATIO_CAPABILITY, this, null, compound);
	}
}
