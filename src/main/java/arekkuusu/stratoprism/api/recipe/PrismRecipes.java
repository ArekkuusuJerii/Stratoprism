package arekkuusu.stratoprism.api.recipe;

import arekkuusu.stratoprism.api.StratoprismAPI;
import arekkuusu.stratoprism.common.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class PrismRecipes {

	public static IRecipePrism CRYSTAL_SHARD;

	public static void init() {

		CRYSTAL_SHARD = StratoprismAPI.registerRecipeCrystalAltar(new ItemStack(Items.SLIME_BALL), Blocks.VINE,  Blocks.VINE, Items.CLAY_BALL);
	}
}
