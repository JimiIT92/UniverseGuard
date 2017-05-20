package com.universeguard.events.flags;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.property.block.MatterProperty;
import org.spongepowered.api.data.property.block.MatterProperty.Matter;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventFlow {
	@Listener
	public void onFlow(ChangeBlockEvent.Pre event) {
		BlockSnapshot snapshot = event.getTargetWorld().createSnapshot(event.getLocations().get(0).getBlockX(), event.getLocations().get(0).getBlockY(), event.getLocations().get(0).getBlockZ());
		Location<World> loc = event.getLocations().get(event.getLocations().size()-1);
		Region r = RegionUtils.load(loc);
		Optional<MatterProperty> matter = snapshot.getState().getProperty(MatterProperty.class);
		if (r != null) {
			if (matter.isPresent() && matter.get().getValue() == Matter.LIQUID) {
				if (snapshot.getState().getType() == BlockTypes.LAVA
						|| snapshot.getState().getType() == BlockTypes.FLOWING_LAVA)
					event.setCancelled(!r.getFlag("lavaflow"));
				else
					event.setCancelled(!r.getFlag("waterflow"));
			}
		} else {
			if (matter.isPresent() && matter.get().getValue() == Matter.LIQUID) {
				if (snapshot.getState().getType() == BlockTypes.LAVA
						|| snapshot.getState().getType() == BlockTypes.FLOWING_LAVA)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTargetWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("lavaflow"));
				}
				else
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTargetWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("waterflow"));
				}
			}
		}

	}
}
