package com.universeguard.commands;

import java.util.ArrayList;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;
import com.universeguard.utils.Utils;

public class RegionListExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			ArrayList<Text> regions = new ArrayList<Text>();
			Utils.sendMessage(player, TextColors.YELLOW, "--------------------Regions saved--------------------");
			for(Region r : RegionUtils.getAllRegions()) {
				regions.add(Text.of(r.getOwners().contains(player.getUniqueId()) ? TextColors.GOLD : TextColors.RESET, r.getName(), ","));
			}
			if(regions.isEmpty())
				Utils.sendMessage(player, TextColors.RED, "There are no regions!");
			else
				Utils.sendMessage(player, regions.toArray());
			Utils.sendMessage(player, TextColors.YELLOW, "-----------------------------------------------------");
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
