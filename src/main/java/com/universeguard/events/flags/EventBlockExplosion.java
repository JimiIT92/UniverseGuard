package com.universeguard.events.flags;

import java.util.ArrayList;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventBlockExplosion {

	@Listener
	public void onBlockExplode(ExplosionEvent.Detonate event) {
		Entity src = null;
		if (event.getCause().root() instanceof Entity) {
			src = (Entity) event.getCause().root();
		}
		Region region = RegionUtils.load(event.getExplosion().getLocation());
		if (region != null) {
			if (src != null && src.getType() == EntityTypes.CREEPER) {
				event.setCancelled(!region.getFlag("creeperexplosions"));
			} else if (src != null
					&& (src.getType() == EntityTypes.TNT_MINECART || src.getType() == EntityTypes.PRIMED_TNT)) {
				event.setCancelled(!region.getFlag("tnt"));
			} else {
				event.setCancelled(!region.getFlag("otherexplosions"));
			}
		} else {
			ArrayList<Location<World>> locations = new ArrayList<Location<World>>();
			for (Location<World> l : event.getAffectedLocations()) {
				if (event.getTargetWorld().getBlock(l.getBlockPosition()).getType() != BlockTypes.AIR)
					locations.add(l);
			}

			for (Location<World> l : locations) {
				Region r = RegionUtils.load(l);
				if (src != null && src.getType() == EntityTypes.CREEPER) {
					if (r == null) {
						GlobalRegion gr = RegionUtils.loadGlobal(event.getTargetWorld().getName());
						if (gr != null) {
							if (!gr.getFlag("creeperexplosions"))
								event.getAffectedLocations().remove(l);
						}
					} else if (!r.getFlag("creeperexplosions"))
						event.getAffectedLocations().remove(l);
				} else if (src != null
						&& (src.getType() == EntityTypes.TNT_MINECART || src.getType() == EntityTypes.PRIMED_TNT)) {
					if (r == null) {
						GlobalRegion gr = RegionUtils.loadGlobal(event.getTargetWorld().getName());
						if (gr != null)
							if (!gr.getFlag("tnt"))
								event.getAffectedLocations().remove(l);
					} else if (!r.getFlag("tnt"))
						event.getAffectedLocations().remove(l);
				} else {
					if (r == null) {
						GlobalRegion gr = RegionUtils.loadGlobal(event.getTargetWorld().getName());
						if (gr != null)
							if (!gr.getFlag("otherexplosions"))
								event.getAffectedLocations().remove(l);
					} else if (!r.getFlag("otherexplosions"))
						event.getAffectedLocations().remove(l);
				}

			}
		}

	}
}
