package com.universeguard.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;
import com.universeguard.utils.Utils;

public class RegionInfoExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			Region r;
			String name = null;
			if(args.hasAny(Text.of("name"))) {
				name = args.<String>getOne("name").get();
				r = RegionUtils.load(name);
			}
			else {
				r = RegionUtils.load(player.getLocation());
			}
				
			
			if(r != null) {
				Utils.sendMessage(player, TextColors.YELLOW,	"[--------Displaying informations for region ", r.getName(), "--------]");
				Utils.sendMessage(player, TextColors.GREEN, 	"|From: ", Utils.locationToString(r.getPos1()));
				Utils.sendMessage(player, TextColors.GREEN, 	"|To: ", Utils.locationToString(r.getPos2()));
				if(r.getGameMode() == GameModes.NOT_SET)
					Utils.sendMessage(player, TextColors.GREEN, 	"|Gamemode: Not Set");
				else
					Utils.sendMessage(player, TextColors.GREEN, 	"|Gamemode: ", r.getGameMode().getName());
				Utils.sendMessage(player, TextColors.GREEN, 	"|Spawn location: ", Utils.locationToString(r.getSpawn()));
				Utils.sendMessage(player, TextColors.GREEN, 	"|Teleport location: ", Utils.locationToString(r.getTeleport()));
				Utils.sendMessage(player, TextColors.GREEN, 	"|Priority: ", r.getPriority());
				Utils.sendMessage(player, TextColors.AQUA, 		"|--Flags:");
				
				ArrayList<Text> temp = new ArrayList<Text>();
				for (String s : Region.getFlagNames()) {
					if(r.getFlag(s))
						temp.add(Text.of(TextColors.GREEN, s, ", "));
					else
						temp.add(Text.of(TextColors.RED, s, ", "));
				}
				
				
				Utils.sendMessage(player, temp.toArray());
				
				temp.clear();
				Utils.sendMessage(player, TextColors.AQUA, 		"|--Owners:");
				for(UUID owner : r.getOwners()) {
					Text p = Utils.getUser(owner);
					temp.add(p);
				}

				Utils.sendMessage(player, temp.toArray());
				
				temp.clear();
				Utils.sendMessage(player, TextColors.AQUA, 		"|--Disabled commands:");
				for(String cmd : r.getCommands()) {
					temp.add(Text.of(TextColors.GREEN, cmd, ", "));
				}
				
				Utils.sendMessage(player, temp.toArray());
			}
			else {
				if(name != null) 
					Utils.sendMessage(player, TextColors.RED, "Can't find the region ", name, "!");
				else
					Utils.sendMessage(player, TextColors.RED, "There's no region here!");
			}
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
