package abr.mod.photoptics.network;

import abr.mod.photoptics.EnumPhotopticsKeys;
import abr.mod.photoptics.PhotopticsTelescopeHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

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
			PhotopticsTelescopeHandler.onKeyInput(ctx.getServerHandler().playerEntity,
					message.keyEnum);
			return null;
		}
		
	}

}
