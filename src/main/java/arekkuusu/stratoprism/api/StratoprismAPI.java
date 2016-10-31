package arekkuusu.stratoprism.api;

import arekkuusu.stratoprism.api.recipe.IRecipePrism;
import arekkuusu.stratoprism.api.recipe.RecipeCrystalItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StratoprismAPI {

	public static final List<IRecipePrism> CRYSTAL_ALTAR = new ArrayList<>();

	public static IRecipePrism registerRecipeCrystalAltar(ItemStack result, Object... inputs) {
		IRecipePrism recipe = new RecipeCrystalItems(result, inputs);
		CRYSTAL_ALTAR.add(recipe);
		return recipe;
	}
}
