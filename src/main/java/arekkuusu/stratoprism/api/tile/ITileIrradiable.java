package arekkuusu.stratoprism.api.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITileIrradiable {

	boolean addIrradiatio(EntityPlayer player, ItemStack stack);

	boolean addIrradiatio(ItemStack stack, float amount);

	boolean addIrradiatio(ItemStack stack);

	boolean addIrradiatio(Entity entity);

	boolean addIrradiatio(float amount);

	boolean removeIrradiatio(EntityPlayer player, ItemStack stack);

	boolean removeIrradiatio(ItemStack stack, float amount);

	boolean removeIrradiatio(Entity entity);

	boolean removeIrradiatio(float amount);
}
