package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.UniverseGuard;
import com.universeguard.region.Region;
import com.universeguard.utils.Utils;

public class RegionFlagExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;

			Region r = UniverseGuard.instance.pendings.get(player);
			if(r != null) {
				if(args.hasAny("flag") && args.hasAny("value")) {
					String name = args.<String>getOne("flag").get();
					boolean value = args.<Boolean>getOne("value").get();
					if(r.setFlag(name, value))
						Utils.sendMessage(player, TextColors.GREEN, "Region updated!");
					else
						Utils.sendMessage(player, TextColors.RED, "Invalid flag!");
				}
				else {
					if(!args.hasAny("flag"))
						Utils.sendMessage(player, TextColors.RED, "Please specify a flag!");
					else
						Utils.sendMessage(player, TextColors.RED, "Please specify a valid value!");
				}
			}
			else
				Utils.sendMessage(player, TextColors.RED, "You don't have any pending region to set its flags!");
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}
	
	

}
