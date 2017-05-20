package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.UniverseGuard;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;
import com.universeguard.utils.Utils;

public class RegionCommandExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if (src instanceof Player) {
			Player player = (Player) src;

			Region r;
			String name = null;
			if (UniverseGuard.instance.pendings.containsKey(player))
				r = UniverseGuard.instance.pendings.get(player);
			else {
				if (args.hasAny(Text.of("region"))) {
					name = args.<String>getOne("region").get();
					r = RegionUtils.load(name);
				} else {
					r = RegionUtils.load(player.getLocation());
				}
			}

			if (r != null) {
				if (args.hasAny("name")) {
					String p = args.<String>getOne("name").get();
					if (r.getCommands().contains(p)) {
						Utils.sendMessage(player, TextColors.GREEN, args.<String>getOne("name").get(),
								" command enabled!");
						r.removeCommand(p);
						RegionUtils.save(r);
					}

					else {
						Utils.sendMessage(player, TextColors.GREEN, args.<String>getOne("name").get(),
								" command disabled!");
						r.addCommand(p);
						RegionUtils.save(r);
					}

				} else {
					Utils.sendMessage(player, TextColors.RED, "Please specify a valid command!");
				}
			} else {
				if (args.hasAny(Text.of("region"))) {
					Utils.sendMessage(player, TextColors.RED, "Can't find the region ",
							args.<String>getOne("region").get());
				} else
					Utils.sendMessage(player, TextColors.RED, "You don't have any pending region to set its commands!");
			}

			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
