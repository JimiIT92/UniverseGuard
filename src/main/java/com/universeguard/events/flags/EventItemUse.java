package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventItemUse {
	
	@Listener
	public void onItemUse(InteractBlockEvent.Secondary event, @First Player player) {
		BlockSnapshot block = event.getTargetBlock();
		Region r;
		if (!block.getState().getType().equals(BlockTypes.AIR))
			r = RegionUtils.load(block.getLocation().get());
		else
			r = RegionUtils.load(player.getLocation());
		
		ItemType item = null;
		if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
        	item = player.getItemInHand(HandTypes.MAIN_HAND).get().getItem();
        } else if (player.getItemInHand(HandTypes.OFF_HAND).isPresent()){
        	item = player.getItemInHand(HandTypes.OFF_HAND).get().getItem();
        }
		
		if(item != null) {
			if(item == ItemTypes.FLINT_AND_STEEL) {
				if(r != null) {
					if(!RegionUtils.hasPermission(player, r))
						event.setCancelled(!r.getFlag("lighter"));
				} else {
					if(!RegionUtils.hasGlobalPermission(player))
					{
						GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
						if(gr != null)
							event.setCancelled(!gr.getFlag("lighter"));
					}
				}
			}
		}
	}

}
