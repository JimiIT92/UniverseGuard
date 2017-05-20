package com.universeguard.events.flags;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventCommandSend {

	@Listener
	public void onCommandSend(SendCommandEvent event, @First Player p) {
		Region r = RegionUtils.load(p.getLocation());
		String cmd = event.getCommand().toLowerCase();
		if(r != null) {
			if(!RegionUtils.hasPermission(p, r))
				event.setCancelled(r.getCommands().contains(cmd));
		}
	}
}
