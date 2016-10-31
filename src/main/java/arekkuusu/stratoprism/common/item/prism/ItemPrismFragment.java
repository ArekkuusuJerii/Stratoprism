package arekkuusu.stratoprism.common.item.prism;

import arekkuusu.stratoprism.api.item.IItemIlluminatum;
import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.item.ItemMod;
import arekkuusu.stratoprism.common.lib.LibNameItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
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
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class ItemPrismFragment extends ItemMod implements IItemIlluminatum {

	@CapabilityInject(IItemHandler.class)
	private static final Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
	private static final String TAG = "Illuminatum";

	public ItemPrismFragment() {
		super(LibNameItem.PRISM_FRAGMENT);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.GRAY + "Broken piece from a Crystal");
		tooltip.add("");
		tooltip.add(TextFormatting.ITALIC + "Use:");
		tooltip.add(TextFormatting.BLUE + "Right Click to release energy");
		tooltip.add("");
		tooltip.add(TextFormatting.GRAY + "Illuminatum Left: " + TextFormatting.AQUA + getIlluminatum(stack));
		tooltip.add("");
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!hasTag(stack)) setIlluminatum(stack, 2);

		if (getIlluminatum(stack) <= 0 && entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (player.hasCapability(ITEM_HANDLER_CAPABILITY, null)) {
				IItemHandler inventory = player.getCapability(ITEM_HANDLER_CAPABILITY, null);

				for (int i = 0; i < inventory.getSlots(); i++)
					if (inventory.getStackInSlot(i) == stack) {
						inventory.extractItem(i, 1, false);
						worldIn.playSound(player, entityIn.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.5F, 10F);
					}

			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if(count == 1000) player.stopActiveHand();
		//TODO: Add FX
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		addIlluminatum(stack, -1);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 1000;
	}

	@Override
	public boolean canIrradiate() {
		return true;
	}

	@Override
	public boolean hasTag(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		return nbt != null && nbt.hasKey(TAG);
	}

	@Override
	public void addIlluminatum(ItemStack itemStack, int amount) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			itemStack.setTagCompound(nbt);
			nbt.setInteger(TAG, amount);
		} else if (nbt.getInteger(TAG) >= 0) {
			nbt.setInteger(TAG, (short) (nbt.getInteger(TAG) + amount));
		}
	}

	@Override
	public void setIlluminatum(ItemStack itemStack, int amount) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			itemStack.setTagCompound(nbt);
			nbt.setInteger(TAG, amount);
		} else if (nbt.getInteger(TAG) >= 0) {
			nbt.setInteger(TAG, amount);
		}
	}

	@Override
	public int getIlluminatum(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		return nbt == null ? 0 : nbt.getInteger(TAG);
	}

	//@Override
	//public boolean showDurabilityBar(ItemStack stack) {
	//	return true;
	//}

	//@Override
	//public double getDurabilityForDisplay(ItemStack stack) {
	//	int uses = getIlluminatum(stack);

	//	return 1.0 - ((double) uses / (double) 2);
	//}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage();
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i < 12; i++){
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

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
}
