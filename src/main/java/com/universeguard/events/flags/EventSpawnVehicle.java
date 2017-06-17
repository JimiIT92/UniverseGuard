package com.universeguard.events.flags;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventSpawnVehicle {

	@Listener
	public void onVehicleSpawn(SpawnEntityEvent event, @First EntitySpawnCause e) {
		Region r = RegionUtils.load(e.getEntity().getLocation());
		if (e.getType() == SpawnTypes.PLACEMENT) {
			if (e.getEntity().getType() == EntityTypes.PLAYER && 
					(event.getEntities().get(0).getType() == EntityTypes.BOAT || event.getEntities().get(0).getType() == EntityTypes.RIDEABLE_MINECART)) {
				Player p = (Player)e.getEntity();
				if (r != null) {
					if(!RegionUtils.hasPermission(p, r))
						event.setCancelled(!r.getFlag("vehicleplace"));
				} else {
					if(!RegionUtils.hasGlobalPermission(p)) {
						GlobalRegion gr = RegionUtils.loadGlobal(e.getEntity().getWorld().getName());
						if(gr != null)
							event.setCancelled(!gr.getFlag("vehicleplace"));
					}
						
				}
			}
		}

	}
}
