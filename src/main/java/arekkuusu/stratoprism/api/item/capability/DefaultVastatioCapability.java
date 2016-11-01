package arekkuusu.stratoprism.api.item.capability;

import arekkuusu.stratoprism.common.proxy.CommonProxy;
import arekkuusu.stratoprism.common.proxy.MessageVastatioUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

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
	public void setVastatio(EntityPlayer player, float vastatio) {
		this.vastatio = vastatio;
		if (vastatio < 0){
			this.vastatio = 0;
		}
		if (vastatio > getMaxVastatio()){
			this.vastatio = getMaxVastatio();
		}
		dataChanged(player);
	}

	@Override
	public void setMaxVastatio(float maxVastatio) {
		this.maxVastatio = maxVastatio;
	}

	@Override
	public NBTTagCompound saveNBTData() {
		return (NBTTagCompound)VastatioCapabilityStorage.cap.writeNBT(VastatioProvider.VASTATIO_CAPABILITY, this, null);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		VastatioCapabilityStorage.cap.readNBT(VastatioProvider.VASTATIO_CAPABILITY, this, null, compound);
	}

	@Override
	public void dataChanged(EntityPlayer player) {
		if(player instanceof EntityPlayerMP) CommonProxy.network.sendTo(new MessageVastatioUpdate(saveNBTData()), (EntityPlayerMP)player);
	}
}
