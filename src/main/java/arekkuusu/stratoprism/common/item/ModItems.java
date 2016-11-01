package arekkuusu.stratoprism.common.item;

import arekkuusu.stratoprism.common.lib.LibMod;
import arekkuusu.stratoprism.common.lib.LibNameItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(LibMod.MOD_ID)
public final class ModItems {

	@ObjectHolder(LibNameItem.PRISM)
	public static final Item PRISM = new Item();

	@ObjectHolder(LibNameItem.PRISM_SHARD)
	public static final Item PRISM_SHARD = new Item();

	@ObjectHolder(LibNameItem.BROKEN_PRISM)
	public static final Item BROKEN_PRISM = new Item();
}
