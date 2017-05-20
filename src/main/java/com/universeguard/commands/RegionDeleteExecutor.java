package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.universeguard.utils.RegionUtils;

public class RegionDeleteExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			if(args.hasAny(Text.of("name"))) {
				RegionUtils.delete(player, args.<String>getOne("name").get());
			}
			else {
				RegionUtils.delete(player, player.getLocation());
			}
				
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
