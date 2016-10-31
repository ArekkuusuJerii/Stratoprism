package arekkuusu.stratoprism.api.item;

import net.minecraft.item.ItemStack;

public interface IItemIlluminatum {

	boolean canIrradiate();

	boolean hasTag(ItemStack itemStack);

	void addIlluminatum(ItemStack itemStack, int amount);

	void setIlluminatum(ItemStack itemStack, int amount);

	int getIlluminatum(ItemStack itemStack);
}
