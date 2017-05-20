package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;
import com.universeguard.utils.Utils;

public class RegionGlobalExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			
			if(args.hasAny("flag") && args.hasAny("value")) {
				GlobalRegion r = RegionUtils.loadGlobal(player.getWorld().getName());
				String name = args.<String>getOne("flag").get();
				if(!Region.getFlagNames().contains(name)) {
					Utils.sendMessage(player, TextColors.RED, "Please specify a valid flag name!");
					return CommandResult.success();
				}
				boolean value = args.<Boolean>getOne("value").get();
				r.setFlag(name, value);
				RegionUtils.saveGlobal(r);
				Utils.sendMessage(player, TextColors.GREEN, "Global Region updated!");
			}
			else {
				if(!args.hasAny("flag")) {
					Utils.sendMessage(player, TextColors.RED, "Please specify a flag!");
				}
				else
					Utils.sendMessage(player, TextColors.RED, "Please specify a valid value!");
			}
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
