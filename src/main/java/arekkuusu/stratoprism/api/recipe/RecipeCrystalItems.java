/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.api.recipe;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeCrystalItems implements IRecipePrism {

	private final ItemStack result;
	private final ImmutableList<Object> neededItems;

	public RecipeCrystalItems(ItemStack result, Object... inputs) {
		this.result = result;

		List<Object> stackedList = Arrays.stream(inputs).map(obj -> {
			if(obj instanceof Item) {
				return new ItemStack((Item)obj);
			}
			else if(obj instanceof Block) {
				return new ItemStack((Block)obj);
			}
			else return obj;
		}).collect(Collectors.toList());

		neededItems = ImmutableList.copyOf(stackedList);
	}

	@Override
	public boolean validateRecipe(IItemHandler inv, World world) {
		List<Object> toCompare = new ArrayList<>(neededItems);
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.extractItem(i, 1, true);
			if (stack == null)
				break;
			int index = -1;
			for (int j = 0; j < toCompare.size(); j++) {
				Object obj = toCompare.get(j);
				if (obj instanceof ItemStack && ItemStack.areItemStacksEqual(stack, (ItemStack) obj)) {
					index = j;
					break;
				} else if (obj instanceof String) { //TODO: Add oreRegistry names

				}
			}
			if (index != -1) {
				toCompare.remove(index);
			} else return false;
		}
		return toCompare.isEmpty();
	}

	@Override
	public ItemStack getResult() {
		return result;
	}
}
