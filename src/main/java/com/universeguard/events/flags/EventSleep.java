package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventSleep {

	@Listener
	public void onSleeping(InteractBlockEvent.Secondary event, @First Player player) {
		BlockSnapshot block = event.getTargetBlock();
		Region r;
		if (block.getState().getType() == BlockTypes.BED) {
			r = RegionUtils.load(block.getLocation().get());
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("sleep"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("sleep"));
				}
			}

		}
	}
}
