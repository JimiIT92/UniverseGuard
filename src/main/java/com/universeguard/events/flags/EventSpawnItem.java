package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.spawn.BlockSpawnCause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventSpawnItem {

	@Listener
	public void onItemSpawn(SpawnEntityEvent event) {
		if(event.getCause().first(BlockSpawnCause.class).isPresent()) {
			BlockSnapshot b = ((BlockSpawnCause)event.getCause().first(BlockSpawnCause.class).get()).getBlockSnapshot();
			BlockType t = ((BlockSpawnCause)event.getCause().first(BlockSpawnCause.class).get()).getBlockSnapshot().getState().getType();
			if(t == BlockTypes.LEAVES || t == BlockTypes.LEAVES2) {
				Region r = RegionUtils.load(b.getLocation().get());
				if(r != null) {
					event.setCancelled(!r.getFlag("leafdecay"));
				}
				else {
					GlobalRegion gr = RegionUtils.loadGlobal(b.getLocation().get().getExtent().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("leafdecay"));
				}
			}
		}
	}
}
