package arekkuusu.stratoprism.common.block.tile.tile;

import arekkuusu.stratoprism.api.tile.ITileItemHolder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class TileItemInventory extends TileEntity implements ITileItemHolder {

	public ItemStackHandlerTile itemHandler = createItemHandler();

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
		NBTTagCompound ret = super.writeToNBT(nbtTagCompound);
		writeDataNBT(ret);
		return ret;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		readDataNBT(nbtTagCompound);
	}

	@Nonnull
	@Override
	public final NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeDataNBT(tag);
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readDataNBT(packet.getNbtCompound()); //FIXME: ClientSide method
	}

	public void readDataNBT(NBTTagCompound tagCompound) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(tagCompound);
	}

	public void writeDataNBT(NBTTagCompound nbtTagCompound) {
		nbtTagCompound.merge(itemHandler.serializeNBT());
	}

	protected ItemStackHandlerTile createItemHandler() {
		return new ItemStackHandlerTile(this, true);
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nonnull EnumFacing side) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side);
	}

	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		return super.getCapability(capability, side);
	}

	public abstract int getSizeInventory();

	public static class ItemStackHandlerTile extends ItemStackHandler {

		private boolean allow;
		private final TileItemInventory tile;

		ItemStackHandlerTile(TileItemInventory tile, boolean allow) {
			super(tile.getSizeInventory());
			this.tile = tile;
			this.allow = allow;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (allow) {
				return super.insertItem(slot, stack, simulate);
			} else return stack;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (allow) {
				return super.extractItem(slot, 1, simulate);
			} else return null;
		}


		public ItemStack getItemSimulate(int slot) {
			if (allow) {
				return super.extractItem(slot, 1, true);
			} else return null;
		}

		@Override
		public void onContentsChanged(int slot) {
			tile.markDirty();
		}
	}
}
