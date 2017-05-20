package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.utils.Utils;

public class RegionExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src.hasPermission(Utils.getPermission("permission"))) {
			if(src instanceof Player) {
				Player player = (Player)src;

				player.getInventory().offer(Utils.getSelector());
				
				return CommandResult.success();
			}
		}
		else
			src.sendMessage(Text.of(TextColors.RED, "You do not have permission to use this command!"));
		return CommandResult.empty();
	}

}
