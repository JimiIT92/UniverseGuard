package com.universeguard.events.flags;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventExperienceDrop {

	@Listener
	public void onExperienceDrop(SpawnEntityEvent event) {
		if(!event.getEntities().isEmpty()) {
			Entity e = event.getEntities().get(0);
			if (e.getType() == EntityTypes.EXPERIENCE_ORB) {
				Region r = RegionUtils.load(e.getLocation());
				if (r != null) {
					event.setCancelled(!r.getFlag("expdrop"));
				} else {
					{
						GlobalRegion gr = RegionUtils.loadGlobal(e.getWorld().getName());
						if(gr != null) {
							event.setCancelled(!gr.getFlag("expdrop"));
						}
					}
				}
			}
		}
	}
}
