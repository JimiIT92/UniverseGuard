package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.monster.Enderman;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventBlockPlace {

	@Listener
	public void onBlockPlaceByEntity(ChangeBlockEvent.Place event) {
		if (event.getCause().root() instanceof Enderman) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("endermangrief"));
			} else {
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTransactions().get(0).getOriginal().getLocation().get().getExtent().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("endermangrief"));
				}
			}
		} 
		else if (!(event.getCause().root() instanceof Player) && (event.getTransactions().get(0).getFinal().getState().getType() == BlockTypes.FIRE)) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("firespread"));
			} else {
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTransactions().get(0).getOriginal().getLocation().get().getExtent().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("firespread"));
				}
			}
		}
		else if (!(event.getCause().root() instanceof Player) && !(event.getCause().root() instanceof Piston)) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("build"));
			} else {
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTransactions().get(0).getOriginal().getLocation().get().getExtent().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("build"));
				}
			}
		}

	}
	
	@Listener
	public void onBlockPlace(ChangeBlockEvent.Place event, @Root Player player) {
		Region r = RegionUtils.load(player.getLocation());
		if(r != null) {
			if(!RegionUtils.hasPermission(player, r))
				event.setCancelled(!r.getFlag("build"));
		} else {
			if(!RegionUtils.hasGlobalPermission(player))
			{
				GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
				if(gr != null)
					event.setCancelled(!gr.getFlag("build"));
			}
		}
	}
	
	@Listener
	public void onEntityPlace(InteractBlockEvent.Secondary event, @First Player player) {
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
			if(item == ItemTypes.PAINTING || item == ItemTypes.ITEM_FRAME
					|| item == ItemTypes.ARMOR_STAND || item == ItemTypes.END_CRYSTAL) {
				if(r != null) {
					if(!RegionUtils.hasPermission(player, r))
						event.setCancelled(!r.getFlag("build"));
				} else {
					if(!RegionUtils.hasGlobalPermission(player))
					{
						GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
						if(gr != null)
							event.setCancelled(!gr.getFlag("build"));
					}
				}
			}
		}
	}

}
