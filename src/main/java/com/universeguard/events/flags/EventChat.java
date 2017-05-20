package com.universeguard.events.flags;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventChat {

	@Listener
	public void onChat(MessageChannelEvent.Chat e, @First Player p) {
		Region r = RegionUtils.load(p.getLocation());
		if(r != null) {
			if(!RegionUtils.hasPermission(p, r))
				e.setCancelled(!r.getFlag("sendchat"));
		} else {
			if(!RegionUtils.hasGlobalPermission(p))
			{
				GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
				if(gr != null)
					e.setCancelled(!gr.getFlag("sendchat"));
			}
		}
	}
}
