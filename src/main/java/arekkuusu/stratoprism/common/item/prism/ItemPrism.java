package arekkuusu.stratoprism.common.item.prism;

import arekkuusu.stratoprism.api.item.capability.DefaultVastatioCapability;
import arekkuusu.stratoprism.api.item.capability.IVastatioCapability;
import arekkuusu.stratoprism.api.item.capability.VastatioProvider;
import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.item.ItemMod;
import arekkuusu.stratoprism.common.item.ModItems;
import arekkuusu.stratoprism.common.lib.LibNameItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class ItemPrism extends ItemMod {

	@CapabilityInject(IVastatioCapability.class)
	private static final Capability<IVastatioCapability> VASTATIO_CAPABILITY = null;
	@CapabilityInject(IItemHandler.class)
	private static final Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

	public ItemPrism() {
		super(LibNameItem.PRISM);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add("");
		tooltip.add(TextFormatting.ITALIC + I18n.format("stratoprism.tooltip.prism.use.name"));
		tooltip.add(TextFormatting.BLUE + I18n.format("stratoprism.tooltip.prism.how_to_use.name"));
		tooltip.add("");
		tooltip.add(TextFormatting.GOLD + I18n.format("stratoprism.tooltip.prism.uses_remaining.name") + " "
				+ TextFormatting.AQUA + stack.getCapability(VASTATIO_CAPABILITY, null).getVastatio());
		tooltip.add("");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		IVastatioCapability capability = itemStackIn.getCapability(VASTATIO_CAPABILITY, null);

		capability.setVastatio(playerIn, capability.getVastatio() - 10F);

		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getCapability(VASTATIO_CAPABILITY, null).getVastatio() == 0 && entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (player.hasCapability(ITEM_HANDLER_CAPABILITY, null)) {
				IItemHandler inventory = player.getCapability(ITEM_HANDLER_CAPABILITY, null);

				for (int i = 0; i < inventory.getSlots(); i++)
					if (inventory.getStackInSlot(i) == stack) {
						inventory.extractItem(i, 1, false);
						worldIn.playSound(player, entityIn.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0F, 10F);
						//inventory.insertItem(i, new ItemStack(ModItems.BROKEN_PRISM), false);
					}
			}
		}
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public Item getContainerItem() {
		return ModItems.BROKEN_PRISM;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		float uses = stack.getCapability(VASTATIO_CAPABILITY, null).getVastatio();
		return 1.0 - (uses / stack.getCapability(VASTATIO_CAPABILITY, null).getMaxVastatio());
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		DefaultVastatioCapability capability = new DefaultVastatioCapability();
		capability.vastatio = 1000;
		return new VastatioProvider(capability);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerItem(this, 0);
	}
}
