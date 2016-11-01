package arekkuusu.stratoprism.common.item.prism;

import arekkuusu.stratoprism.api.item.IPrismaticable;
import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.item.ItemMod;
import arekkuusu.stratoprism.common.lib.LibNameItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrokenPrism extends ItemMod implements IPrismaticable {

	public ItemBrokenPrism() {
		super(LibNameItem.BROKEN_PRISM);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerItem(this, 0);
	}

	@Override
	public boolean canFit(ItemStack stack) {
		return stack.hasEffect();
	}
}
