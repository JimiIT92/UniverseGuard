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

import com.universeguard.region.GlobalRegion;
import com.universeguard.utils.RegionUtils;
import com.universeguard.utils.Utils;

public class RegionGlobalListExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			GlobalRegion r = RegionUtils.loadGlobal(player.getWorld().getName());
			ArrayList<Text> temp = new ArrayList<Text>();
			for (String s : GlobalRegion.getFlagNames()) {
				if(r.getFlag(s))
					temp.add(Text.of(TextColors.GREEN, s, ", "));
				else
					temp.add(Text.of(TextColors.RED, s, ", "));
			}
			
			
			Utils.sendMessage(player, temp.toArray());
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
