package com.universeguard.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.universeguard.UniverseGuard;
import com.universeguard.region.Region;
import com.universeguard.utils.Utils;

public class RegionSetSpawnExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			Region r = UniverseGuard.instance.pendings.get(player);
			
			if(r != null) {
				if(args.hasAny("locX") && args.hasAny("locY") && args.hasAny("locZ")) {
					int x = args.<Integer>getOne("locX").get();
					int y = args.<Integer>getOne("locY").get();
					int z = args.<Integer>getOne("locZ").get();
					Location<World> l = new Location<World>(Sponge.getServer().getWorld(r.getWorld()).get(), x, y, z);
					r.setSpawn(l);
					Utils.sendMessage(player, TextColors.GREEN, "Region updated!");
				}
				else
					Utils.sendMessage(player, TextColors.RED, "Please specify a valid location!");
			}
				
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
