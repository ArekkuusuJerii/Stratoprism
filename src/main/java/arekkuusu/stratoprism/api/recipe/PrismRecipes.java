/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.api.recipe;

import arekkuusu.stratoprism.api.StratoprismAPI;
import arekkuusu.stratoprism.common.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class PrismRecipes {

	public static IRecipePrism SLIME_BALL;

	public static void init() {

		SLIME_BALL = StratoprismAPI.registerRecipeCrystalAltar(new ItemStack(Items.FLINT), new ItemStack(ModItems.PRISM_SHARD, 1, 0), new ItemStack(ModItems.PRISM_SHARD, 1, 1), new ItemStack(ModItems.PRISM_SHARD, 1, 2));
	}
}
