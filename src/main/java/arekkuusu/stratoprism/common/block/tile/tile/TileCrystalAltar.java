package arekkuusu.stratoprism.common.block.tile.tile;

import arekkuusu.stratoprism.api.StratoprismAPI;
import arekkuusu.stratoprism.api.item.IPrismaticable;
import arekkuusu.stratoprism.api.recipe.IRecipePrism;
import arekkuusu.stratoprism.common.proxy.PacketDispatcher;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileCrystalAltar extends TileItemInventory implements ITickable {

	private static final String TAG = "hasWater";
	private boolean hasWater;
	public int tickCount;

	public boolean collideItem(EntityItem entityItem) {
		if (!hasWater()) return false;

		ItemStack stack = entityItem.getEntityItem();
		if (stack == null || entityItem.isDead)
			return false;

		boolean added = false;
		if (stack.getItem() instanceof IPrismaticable && ((IPrismaticable) stack.getItem()).canFit(stack))
			if (!worldObj.isRemote) {
				for (int i = 0; i < getSizeInventory(); i++)
					if (itemHandler.getItemSimulate(i) == null) {
						stack.stackSize--;
						if (stack.stackSize == 0)
							entityItem.setDead();

						ItemStack stackToAdd = stack.copy();
						stackToAdd.stackSize = 1;
						itemHandler.insertItem(i, stackToAdd, false);
						added = true;

						worldObj.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 0.1F, 8F);

						PacketDispatcher.sendTileUpdateNearbyPlayers(this);
						break;
					}
			}

		return added;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); i++) {
			if (itemHandler.getItemSimulate(i) != null)
				return false;
		}
		return true;
	}

	@Override
	public boolean addItem(@Nullable EntityPlayer player, ItemStack stack) {
		if (stack.getItem() == Items.GLOWSTONE_DUST) {
			if (player != null && !player.capabilities.isCreativeMode) {
				stack.stackSize--;
				if (stack.stackSize == 0) stack = null;
			}
			checkRecipe();
		} else if (!hasWater() && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			IFluidHandler fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

			FluidStack drainWater = fluidHandler.drain(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), false);

			if (drainWater != null && drainWater.getFluid() == FluidRegistry.WATER
					&& drainWater.amount == Fluid.BUCKET_VOLUME) {
				if (player != null)
					worldObj.playSound(player, player.getPosition(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 1.0F, 5F);

				fluidHandler.drain(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), true);
				setWater(true);
				worldObj.updateComparatorOutputLevel(pos, worldObj.getBlockState(pos).getBlock());
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean removeItem(@Nullable EntityPlayer player) {
		boolean removed = false;
		for (int i = getSizeInventory() - 1; i >= 0; i--)
			if (itemHandler.getItemSimulate(i) != null) {
				ItemStack stackToTake = itemHandler.extractItem(i, 1, false).copy();
				stackToTake.stackSize = 1;

				if (player != null && !player.capabilities.isCreativeMode) {
					ItemHandlerHelper.giveItemToPlayer(player, stackToTake);
				}

				removed = true;
				worldObj.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 0.1F, 10F);
				PacketDispatcher.sendTileUpdateNearbyPlayers(this);
				break;
			}

		return removed;
	}

	@Override
	public void destroy() {
		if (!worldObj.isRemote)
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack output = itemHandler.extractItem(i, 1, false);
				if (output != null) {
					EntityItem outputItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, output);
					worldObj.spawnEntityInWorld(outputItem);
				}
			}
	}

	private boolean checkRecipe() {
		for (IRecipePrism recipe : StratoprismAPI.CRYSTAL_ALTAR) {
			if (recipe.validateRecipe(itemHandler, worldObj)) {
				if (!worldObj.isRemote) {
					for (int i = 0; i < getSizeInventory(); i++)
						itemHandler.extractItem(i, 1, false);

					ItemStack output = recipe.getResult().copy();
					EntityItem outputItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, output);
					worldObj.spawnEntityInWorld(outputItem);

					fancyCrafting();

					worldObj.updateComparatorOutputLevel(pos, worldObj.getBlockState(pos).getBlock());
				}
				setWater(false);
				return true;
			}
		}

		return false;
	}

	private void fancyCrafting() {
		//TODO: Add FX
	}

	@Override
	protected ItemStackHandlerTile createItemHandler() {
		return new ItemStackHandlerTile(this, true) {
			@Override
			protected int getStackLimit(int slot, ItemStack stack) {
				return 1;
			}
		};
	}

	@Override
	public int getSizeInventory() {
		return 16;
	}

	@Override
	public void writeDataNBT(NBTTagCompound nbtTagCompound) {
		super.writeDataNBT(nbtTagCompound);
		nbtTagCompound.setBoolean(TAG, hasWater());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		hasWater = nbtTagCompound.getBoolean(TAG);
	}

	public void setWater(boolean water) {
		hasWater = water;
		PacketDispatcher.dispatchToNearbyPlayers(worldObj, pos);
	}

	public boolean hasWater() {
		return hasWater;
	}

	@Override
	public void update() {
		List<EntityItem> entityItemList = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(getPos()).expand(0, -0.4, 0));
		if (!entityItemList.isEmpty()) {
			entityItemList.forEach(this::collideItem);
		}
		if (!isEmpty() && worldObj.isRemote) {
			BlockPos pos = getPos();
			Random rand = new Random();
			float d3 = ((float) pos.getX() + rand.nextFloat());
			float d4 = ((float) pos.getY() + rand.nextFloat());
			float d5 = ((float) pos.getZ() + rand.nextFloat());
			worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d3, d4, d5, 0.0D, 0.0D, 0.0D);
			if (tickCount % 10 == 0)
				worldObj.playSound(null, getPos(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.PLAYERS, 1.0F, 5F);
		}
		if (!hasWater() && worldObj.isRainingAt(getPos().up())) {
			Random rand = new Random();
			if (worldObj.isRemote)
				for (int i = 0; i < 4; i++) {
					float d3 = ((float) pos.getX() + rand.nextFloat());
					float d4 = ((float) pos.getY() + rand.nextFloat());
					float d5 = ((float) pos.getZ() + rand.nextFloat());
					worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
				}
			worldObj.playSound(null, getPos(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1.0F, 5F);
			setWater(true);
		}
		++tickCount;
	}
}
