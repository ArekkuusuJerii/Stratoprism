/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.api.item.capability;

import arekkuusu.stratoprism.common.net.MessageVastatioUpdate;
import arekkuusu.stratoprism.common.net.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IVastatioCapability {
	float getVastatio();

	float getMaxVastatio();

	/**
	 * Do NOT use unless you want to give an initial amount.
	 *
	 * @param vastatio Initial amount, must be more than 0 and less than Max
	 */
	void setVastatio(float vastatio);

	/**
	 * For use with Players and players only
	 *
	 * @param player   Player
	 * @param item     ItemStack modified
	 * @param vastatio Amount
	 */
	default void setVastatio(EntityPlayer player, ItemStack item, float vastatio) {
		setVastatio(vastatio);
		dataChanged(player, item);
	}

	/**
	 * For use with Entities
	 *
	 * @param entity   Entity
	 * @param item     ItemStack modified
	 * @param vastatio Amount
	 */
	default void setVastatio(Entity entity, ItemStack item, float vastatio) {
		setVastatio(vastatio);
		dataChanged(entity, item);
	}

	/**
	 * For use with TileEntities
	 *
	 * @param tileEntity TileEntity
	 * @param item       ItemStack modified
	 * @param vastatio   Amount
	 */
	default void setVastatio(TileEntity tileEntity, ItemStack item, float vastatio) {
		setVastatio(vastatio);
		dataChanged(tileEntity, item);
	}

	/**
	 * General use
	 *
	 * @param world World
	 * @param pos Pos in the world
	 * @param item ItemStack modified
	 * @param vastatio Amount
	 */
	default void setVastatio(World world, BlockPos pos, ItemStack item, float vastatio) {
		setVastatio(vastatio);
		dataChanged(world, pos, item);
	}

	void setMaxVastatio(float maxVastatio);

	NBTTagCompound saveNBTData();

	void loadNBTData(NBTTagCompound compound);

	/**
	 * For use with Players and players only
	 *
	 * @param player Player
	 * @param item   ItemStack modified
	 */
	default void dataChanged(EntityPlayer player, ItemStack item) {
		if (player instanceof EntityPlayerMP)
			PacketHandler.sendTo((EntityPlayerMP)player, new MessageVastatioUpdate(item, saveNBTData()));
	}

	/**
	 * For use with Entities, this will send updates to all Players around it
	 *
	 * @param entity The Entity
	 * @param item   ItemStack modified
	 */
	default void dataChanged(Entity entity, ItemStack item) {
		PacketHandler.sendToNear(entity, new MessageVastatioUpdate(item, saveNBTData()));
	}

	/**
	 * For use with TileEntities, this will send updates to all Players around it
	 *
	 * @param tileEntity The TileEntity
	 * @param item       ItemStack modified
	 */
	default void dataChanged(TileEntity tileEntity, ItemStack item) {
		PacketHandler.sendToNear(tileEntity.getWorld(), tileEntity.getPos(), new MessageVastatioUpdate(item, saveNBTData()));
	}

	/**
	 * For use with whatever idfc, this will send updates to all nearby Players
	 *
	 * @param world The World
	 * @param item  ItemStack modified
	 * @param pos   Position to check for players
	 */
	default void dataChanged(World world, BlockPos pos, ItemStack item) {
		PacketHandler.sendToNear(world, pos, new MessageVastatioUpdate(item, saveNBTData()));
	}
}
