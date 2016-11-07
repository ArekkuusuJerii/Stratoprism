package arekkuusu.stratoprism.common.block.tile.tile;

import arekkuusu.stratoprism.api.tile.ITileVastatioContainer;
import arekkuusu.stratoprism.common.net.PacketHandler;
import net.minecraft.nbt.NBTTagCompound;

public class TilePrism extends TileMod implements ITileVastatioContainer {

	private static final String TAG_VASTATIO = "vastatio";
	private static final float MAX_VASTATIO = 1000000F;
	private boolean updated = false;
	private float vastatio;
	public int tickCount;

	@Override
	public boolean add(float amount) {
		vastatio = Math.max(0, Math.min(getVastatio() + amount, MAX_VASTATIO));
		updated = true;
		worldObj.updateComparatorOutputLevel(pos, worldObj.getBlockState(pos).getBlock());
		return false;
	}

	@Override
	public float take(float amount) {
		if (vastatio > 0 && vastatio - amount >= 0) {
			vastatio -= amount;
			updated = true;
			worldObj.updateComparatorOutputLevel(pos, worldObj.getBlockState(pos).getBlock());
			return amount;
		}
		return 0;
	}

	@Override
	public float getVastatio() {
		return vastatio;
	}

	@Override
	public boolean canRecieve() {
		return vastatio < MAX_VASTATIO;
	}

	@Override
	public void writeDataNBT(NBTTagCompound cmp) {
		cmp.setFloat(TAG_VASTATIO, vastatio);
	}

	@Override
	public void readDataNBT(NBTTagCompound cmp) {
		vastatio = cmp.getInteger(TAG_VASTATIO);
	}

	@Override
	public void update() {
		if (tickCount % 500 == 0 && !worldObj.isRemote && canRecieve()) add(0.5F);
		if (updated && !worldObj.isRemote) {
			PacketHandler.sendTileUpdateNearbyPlayers(this);
			updated = false;
		}
		++tickCount;
	}
}
