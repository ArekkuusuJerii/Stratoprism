package arekkuusu.stratoprism.common.net;

import arekkuusu.stratoprism.api.item.capability.VastatioProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageVastatioUpdate implements IMessage {

	private NBTTagCompound tagCompound;
	private ItemStack itemStack;

	public MessageVastatioUpdate(){}

	public MessageVastatioUpdate(ItemStack stack, NBTTagCompound tag){
		itemStack = stack;
		tagCompound = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		itemStack = ByteBufUtils.readItemStack(buf);
		tagCompound = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, itemStack);
		ByteBufUtils.writeTag(buf, tagCompound);
	}

	public static class Handler implements IMessageHandler<MessageVastatioUpdate, IMessage> {

		@Override
		public IMessage onMessage(final MessageVastatioUpdate message, final MessageContext context) {
			IThreadListener mainThread = context.side.isClient()? Minecraft.getMinecraft() : (WorldServer)context.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(() -> VastatioProvider.get(message.itemStack == null
					? Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()
					: message.itemStack).loadNBTData(message.tagCompound));
			return null;
		}
	}
}
