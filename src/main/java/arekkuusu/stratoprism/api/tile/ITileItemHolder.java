package arekkuusu.stratoprism.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITileItemHolder {
	boolean isEmpty();
	boolean addItem(EntityPlayer player, ItemStack stack);
	boolean removeItem(EntityPlayer player);
}
