package abr.mod.photoptics.command;

import abr.mod.photoptics.processing.PlayerDataProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import stellarapi.api.PeriodHelper;

public class CommandObserve extends CommandBase {

	@Override
	public String getName() {
		return "observe";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.photoptics.observe.description";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 3)
			throw new CommandException("command.photoptics.observe.description");
		
		EntityPlayer player = getPlayer(server, sender, args[1]);
		
		if(args[0].equals("setlimit")) {
			short count = 1;
			if(args.length > 3)
				count = (short) this.parseInt(args[args.length-1], 0, Short.MAX_VALUE);
			player.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP).setLimit(args[2].replace('_', ' '), count);
			if(sender == player) {
				player.sendMessage(new TextComponentTranslation("command.photoptics.observe.setlimit.info", player, args[2].replace('_', ' '), count));
			}
		} else if(args[0].equals("resetcount")) {
			short count = 0;
			if(args.length > 3)
				count = (short) this.parseInt(args[args.length-1], 0, Short.MAX_VALUE);
			player.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP).reset(args[2].replace('_', ' '), count);
			if(sender == player) {
				player.sendMessage(new TextComponentTranslation("command.photoptics.observe.resetcount.info", player, args[2].replace('_', ' '), count));
			}
		} else if(args[0].equals("setwait")) {
			long count = 1;
			long unit = 20;
			if(args.length > 3) {
				String arg = args[args.length-1];
				if(arg.endsWith("d")) {
					try {
						unit = (long) PeriodHelper.getDayPeriod(player.getEntityWorld()).getPeriodLength();
					} catch(NullPointerException exception) {
						throw new CommandException("command.photoptics.observe.nodaytime");
					}
					arg = arg.substring(0, arg.length()-1);
				} else if(arg.endsWith("s")) {
					unit = 20;
					arg = arg.substring(0, arg.length()-1);
				} else if(arg.endsWith("t")) {
					unit = 1;
					arg = arg.substring(0, arg.length()-1);
				}

				count = this.parseLong(arg, 0, Long.MAX_VALUE / unit);
			}

			player.getCapability(PlayerDataProvider.OBSERVATION_DATA, EnumFacing.UP).setWaitDuration(args[2].replace('_', ' '), unit * count);
			if(sender == player) {
				player.sendMessage(new TextComponentTranslation("command.photoptics.observe.setwait.info", player, args[2].replace('_', ' '), unit * count));
			}
		}
	}
}
