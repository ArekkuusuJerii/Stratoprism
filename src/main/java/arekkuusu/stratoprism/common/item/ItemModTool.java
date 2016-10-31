package arekkuusu.stratoprism.common.item;

import arekkuusu.stratoprism.api.item.IModelRegister;
import arekkuusu.stratoprism.common.Stratoprism;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

import java.util.Set;

public abstract class ItemModTool extends ItemTool implements IModelRegister{

	public ItemModTool(float attackDamageIn, float attackSpeedIn, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn, String id) {
		super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
		setRegistryName(id);
		setUnlocalizedName(id);
		setCreativeTab(Stratoprism.CREATIVE_TAB);
	}
}
