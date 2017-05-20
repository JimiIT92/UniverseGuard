package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventBlockUse {

	@Listener
	public void onChestUse(InteractBlockEvent.Secondary event, @First Player player) {
		BlockSnapshot block = event.getTargetBlock();
		Region r;
		if (block.getState().getType().equals(BlockTypes.CHEST)
				|| block.getState().getType().equals(BlockTypes.TRAPPED_CHEST)) {
			r = RegionUtils.load(block.getLocation().get());
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("chests"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("chests"));
				}
			}
		} else if (block.getState().getType().equals(BlockTypes.ENDER_CHEST)) {
			r = RegionUtils.load(block.getLocation().get());
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("enderchests"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("enderchests"));
				}
			}
		} else if (!block.getState().getType().equals(BlockTypes.AIR)
				&& !block.getState().getType().equals(BlockTypes.CRAFTING_TABLE)
				&& !block.getState().getType().equals(BlockTypes.ANVIL)
				&& !block.getState().getType().equals(BlockTypes.ENCHANTING_TABLE)) {
			r = RegionUtils.load(block.getLocation().get());
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("use"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("use"));
				}
			}

		}

	}

	@Listener
	public void interact(InteractEntityEvent.Secondary event, @First Player player) {
		EntityType e = event.getTargetEntity().getType();
		if (e == EntityTypes.ITEM_FRAME || e == EntityTypes.ARMOR_STAND || e == EntityTypes.PAINTING) {
			Region r = RegionUtils.load(event.getTargetEntity().getLocation());
			if (r != null) {
				//if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("use"));
			} else {
				GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
				if (gr != null) {
					if (!RegionUtils.hasGlobalPermission(player))
						event.setCancelled(!gr.getFlag("use"));
				}
			}

		}
	}

}
