package arekkuusu.stratoprism.common.item.prism;

import arekkuusu.stratoprism.api.item.IPrismaticable;
import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.item.ItemMod;
import arekkuusu.stratoprism.common.lib.LibNameItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class ItemPrismShard extends ItemMod implements IPrismaticable {

	public ItemPrismShard() {
		super(LibNameItem.PRISM_SHARD);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.GRAY + I18n.format("stratoprism.tooltip.prism_shard.header.name"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage();
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0; i < 12; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerItemAllMeta(this, 12);
	}

	@Override
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean canFit(ItemStack stack) {
		return true;
	}
}
