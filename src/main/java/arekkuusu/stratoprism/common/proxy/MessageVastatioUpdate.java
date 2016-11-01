package arekkuusu.stratoprism.common.proxy;

import arekkuusu.stratoprism.api.item.capability.VastatioProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageVastatioUpdate implements IMessage {

	private NBTTagCompound tagCompound;

	public MessageVastatioUpdate(){}

	public MessageVastatioUpdate(NBTTagCompound tag){
		tagCompound = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		tagCompound = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, tagCompound);
	}

	public static class CapsMessageHandler implements IMessageHandler<MessageVastatioUpdate, IMessage> {

		@Override
		public IMessage onMessage(final MessageVastatioUpdate message, final MessageContext ctx) { //FIXME: I don't think this is right
			IThreadListener mainThread = (ctx.side.isClient())? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(() -> VastatioProvider.get(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()).loadNBTData(message.tagCompound));
			return null;
		}
	}
}
