/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
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
