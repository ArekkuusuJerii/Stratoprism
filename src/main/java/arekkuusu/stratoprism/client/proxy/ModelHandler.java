/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.client.proxy;

import arekkuusu.stratoprism.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public final class ModelHandler {

	public static void registerItem(Item item, int damage) {
		ModelLoader.setCustomModelResourceLocation(item, damage, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerItemAllMeta(Item item, int count) {
		registerItemMetas(item, count, item.getRegistryName().getResourcePath());
	}

	public static void registerItemMetas(Item item, int maxExclusive, String itemName) {
		for (int i = 0; i < maxExclusive; i++) {
			ModelLoader.setCustomModelResourceLocation(
					item, i,
					new ModelResourceLocation(LibMod.MOD_ID + ":" + itemName + "_" + i, "inventory")
			);
		}
	}

	public static void registerBlock(Block block, int meta) {
		Item iBlock = Item.getItemFromBlock(block);
		if (iBlock == null) throw new IllegalArgumentException("Tried to register a block that doesn't have an item");
		ModelLoader.setCustomModelResourceLocation(iBlock, meta, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
