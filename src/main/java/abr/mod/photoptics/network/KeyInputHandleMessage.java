package abr.mod.photoptics.network;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.Photoptics;
import abr.mod.photoptics.PhotopticsTelescopeHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KeyInputHandleMessage implements IMessage {

	private EnumPhotopticsKeys keyEnum;
	
	public KeyInputHandleMessage() { }

	
	public KeyInputHandleMessage(EnumPhotopticsKeys key) {
		this.keyEnum = key;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.keyEnum = EnumPhotopticsKeys.values()[buf.readShort()];
		} catch(ArrayIndexOutOfBoundsException exc) {
			throw new RuntimeException("There is no such key;"
					+ "This means that networking got corrupted.", exc);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(keyEnum.ordinal());
	}
	
	public static class KeyInputHandler implements IMessageHandler<KeyInputHandleMessage, IMessage> {

		@Override
		public IMessage onMessage(KeyInputHandleMessage message, MessageContext ctx) {
			final EntityPlayerMP player = ctx.getServerHandler().player;
			final EnumPhotopticsKeys key = message.keyEnum;
			Photoptics.proxy.registerTask(new Runnable() {
				@Override
				public void run() {
					PhotopticsTelescopeHandler.onKeyInput(player, key);
				}
			});
			return null;
		}
		
	}

}
