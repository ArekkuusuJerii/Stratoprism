package arekkuusu.stratoprism.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface IRecipe {
	boolean validateRecipe(List<ItemStack> usedItems, World world);
	ItemStack getResult();
}
