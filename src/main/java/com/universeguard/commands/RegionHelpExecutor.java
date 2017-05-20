package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.utils.Utils;

public class RegionHelpExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			Utils.sendMessage(player, TextColors.GOLD, "--------------------Help--------------------");
			Utils.sendMessage(player, TextColors.YELLOW, "/region - Get the region selector item");
			Utils.sendMessage(player, TextColors.YELLOW, "/region save - Save the current editing region");
			Utils.sendMessage(player, TextColors.YELLOW, "/region info (region) - Display the details of the region at the current location or the specified one");
			Utils.sendMessage(player, TextColors.YELLOW, "/region delete (region) - Delete the region at the current location or the specified one");
			Utils.sendMessage(player, TextColors.YELLOW, "/region name [name] - Rename the current editing region with the specified name");
			Utils.sendMessage(player, TextColors.YELLOW, "/region list - Show the names of all the saved regions");
			Utils.sendMessage(player, TextColors.YELLOW, "/region gamemode [gamemode] - Set the gamemode of the current editing region");
			Utils.sendMessage(player, TextColors.YELLOW, "/region edit (region) - Allows to edit the region at the current location or the specified one");
			Utils.sendMessage(player, TextColors.YELLOW, "/region flag [flag] [value] - Set the flag value for the current editing region");
			Utils.sendMessage(player, TextColors.YELLOW, "/region add [player] (region) - Add a owner to the region at the current location or the specified one");
			Utils.sendMessage(player, TextColors.YELLOW, "/region remove [player] (region) - Remove a owner to the region at the current location or the specified one");
			Utils.sendMessage(player, TextColors.YELLOW, "/region global [flag] [value] - Set the value of the global flag (used outside of regions)");
			Utils.sendMessage(player, TextColors.YELLOW, "/region globallist - Displays all global flag values");
			Utils.sendMessage(player, TextColors.YELLOW, "/region setteleport [x] [y] [z] - Set the teleport location of the current editing regiont at the specified coordinates");
			Utils.sendMessage(player, TextColors.YELLOW, "/region setspawn [x] [y] [z] - Set the spawn location of the current editing regiont at the specified coordinates");
			Utils.sendMessage(player, TextColors.YELLOW, "/region teleport [region] - Set the player at the teleport location of the specified region");
			Utils.sendMessage(player, TextColors.YELLOW, "/region spawn [region] - Set the player at the spawn location of the specified region");
			Utils.sendMessage(player, TextColors.YELLOW, "/region priority [value] - Set the priority of the current editing region");
			Utils.sendMessage(player, TextColors.YELLOW, "/region command [command] (region) - Allow or disallow a command in the current editing region or the specified one");
			Utils.sendMessage(player, TextColors.YELLOW, "/region help - Displays this list");
			Utils.sendMessage(player, TextColors.YELLOW, "/region flaghelp - Displays the flags help");
			Utils.sendMessage(player, TextColors.GOLD, "--------------------------------------------");
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
