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

public class RegionGamemodeExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;

			Region r = UniverseGuard.instance.pendings.get(player);
			if(r != null) {
				if(args.hasAny("gamemode")) {
					String name = args.<String>getOne("gamemode").get();
					if(!name.equalsIgnoreCase("reset") &&
						!name.equalsIgnoreCase("survival") &&
						!name.equalsIgnoreCase("adventure") &&
						!name.equalsIgnoreCase("spectator") &&
						!name.equalsIgnoreCase("creative")) {
						Utils.sendMessage(player, TextColors.RED, "Please specify a valid gamemode!");
						return CommandResult.empty();
					}
					r.setGameMode(name);
					Utils.sendMessage(player, TextColors.GREEN, "Region updated!");
				}
				else {
					Utils.sendMessage(player, TextColors.RED, "Please specify a gamemode!");
				}
			}
			else
				Utils.sendMessage(player, TextColors.RED, "You don't have any pending region to set its gamemode!");
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
