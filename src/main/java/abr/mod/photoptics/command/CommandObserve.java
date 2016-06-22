package abr.mod.photoptics.command;

import abr.mod.photoptics.processing.PlayerDataProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;

public class CommandObserve extends CommandBase {

	@Override
	public String getCommandName() {
		return "observe";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "command.photoptics.observe.description";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 3)
			throw new CommandException("command.photoptics.observe.description");
		
		EntityPlayer player = this.getPlayer(server, sender, args[1]);
		if(args[0].equals("setlimit")) {
			short count = 1;
			if(args.length > 3)
				count = (short) this.parseInt(args[3], 0, Short.MAX_VALUE);
			player.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP).setLimit(args[2], count);
		} else if(args[0].equals("resetcount")) {
			short count = 0;
			if(args.length > 3)
				count = (short) this.parseInt(args[3], 0, Short.MAX_VALUE);
			player.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP).reset(args[2], count);
		}
	}
}
