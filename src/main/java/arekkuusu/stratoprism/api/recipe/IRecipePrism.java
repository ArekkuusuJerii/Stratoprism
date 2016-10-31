package arekkuusu.stratoprism.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public interface IRecipePrism {
	boolean validateRecipe(IItemHandler usedItems, World world);
	ItemStack getResult();
}
