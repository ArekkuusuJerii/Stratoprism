/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimore Of Alice is Open Source and distributed under the
 * Grimore Of Alice license: https://github.com/ArekkuusuJerii/Grimore-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.stratoprism.common.item;

import arekkuusu.stratoprism.api.item.IModelRegister;
import arekkuusu.stratoprism.common.Stratoprism;
import net.minecraft.item.Item;

public abstract class ItemMod extends Item implements IModelRegister {

	public ItemMod(String id) {
		super();
		setRegistryName(id);
		setUnlocalizedName(id);
		setCreativeTab(Stratoprism.CREATIVE_TAB);
	}
}
