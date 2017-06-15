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

public class RegionHelpExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			int page = 1;
			if(args.hasAny(Text.of("page"))) {
				page = args.<Integer>getOne("page").get();
			}
			if(page <= 1) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Help(1/5)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "/region -", TextColors.WHITE, " ", TextColors.WHITE, " Get the region selector item");
				Utils.sendMessage(player, TextColors.YELLOW, "/region save -", TextColors.WHITE, " Save the current editing region");
				Utils.sendMessage(player, TextColors.YELLOW, "/region info (region) -", TextColors.WHITE, " Display the details of the region at the current location or the specified one");
				Utils.sendMessage(player, TextColors.YELLOW, "/region delete (region) -", TextColors.WHITE, " Delete the region at the current location or the specified one");
				Utils.sendMessage(player, TextColors.YELLOW, "/region name [name] -", TextColors.WHITE, " Rename the current editing region with the specified name");
			}
			if(page == 2) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Help(2/5)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "/region list -", TextColors.WHITE, " Show the names of all the saved regions");
				Utils.sendMessage(player, TextColors.YELLOW, "/region gamemode [gamemode] -", TextColors.WHITE, " Set the gamemode of the current editing region");
				Utils.sendMessage(player, TextColors.YELLOW, "/region edit (region) -", TextColors.WHITE, " Allows to edit the region at the current location or the specified one");
				Utils.sendMessage(player, TextColors.YELLOW, "/region flag [flag] [value] -", TextColors.WHITE, " Set the flag value for the current editing region");
				Utils.sendMessage(player, TextColors.YELLOW, "/region add [player] (region) -", TextColors.WHITE, " Add a owner to the region at the current location or the specified one");
			}
			if(page == 3) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Help(3/5)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "/region remove [player] (region) -", TextColors.WHITE, " Remove a owner to the region at the current location or the specified one");
				Utils.sendMessage(player, TextColors.YELLOW, "/region global [flag] [value] -", TextColors.WHITE, " Set the value of the global flag (used outside of regions)");
				Utils.sendMessage(player, TextColors.YELLOW, "/region globallist -", TextColors.WHITE, " Displays all global flag values");
				Utils.sendMessage(player, TextColors.YELLOW, "/region setteleport [x] [y] [z] -", TextColors.WHITE, " Set the teleport location of the current editing regiont at the specified coordinates");
				Utils.sendMessage(player, TextColors.YELLOW, "/region setspawn [x] [y] [z] -", TextColors.WHITE, " Set the spawn location of the current editing regiont at the specified coordinates");
				
			}
			if(page == 4) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Help(4/5)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "/region teleport [region] -", TextColors.WHITE, " Set the player at the teleport location of the specified region");
				Utils.sendMessage(player, TextColors.YELLOW, "/region spawn [region] -", TextColors.WHITE, " Set the player at the spawn location of the specified region");
				Utils.sendMessage(player, TextColors.YELLOW, "/region priority [value] -", TextColors.WHITE, " Set the priority of the current editing region");
				Utils.sendMessage(player, TextColors.YELLOW, "/region command [command] (region) -", TextColors.WHITE, " Allow or disallow a command in the current editing region or the specified one");
				Utils.sendMessage(player, TextColors.YELLOW, "/region help -", TextColors.WHITE, " Displays this list");
			}
			if(page >= 5) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Help(5/5)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "/region flaghelp -", TextColors.WHITE, " Displays the flags help");
				Utils.sendMessage(player, TextColors.GOLD, "--------------------------------------------");
			}
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
