package com.universeguard.events.flags;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventSpawnEnderPearl {

	@Listener
	public void onEnderPearl(SpawnEntityEvent event, @First EntitySpawnCause e) {
		Region r = RegionUtils.load(e.getEntity().getLocation());
		if (e.getType() == SpawnTypes.PLACEMENT) {
			if (e.getEntity().getType() == EntityTypes.PLAYER
					&& event.getEntities().get(0).getType() == EntityTypes.ENDER_PEARL) {
				Player p = (Player) e.getEntity();
				if (r != null) {
					if (!RegionUtils.hasPermission(p, r)) {
						boolean b = !r.getFlag("enderpearl");
						if(b) {
							if (p.gameMode().get().equals(GameModes.SURVIVAL)) {
								ItemStack ep = ItemStack.builder().itemType(ItemTypes.ENDER_PEARL).quantity(1).build();
								p.getInventory().offer(ep);
							}
							event.setCancelled(true);
						}
						
					}

				} else {
					if (!RegionUtils.hasGlobalPermission(p)) {
						GlobalRegion gr = RegionUtils.loadGlobal(event.getTargetWorld().getName());
						if(gr != null) {
							boolean b = !gr.getFlag("enderpearl");
							if(b) {
								if (p.gameMode().get().equals(GameModes.SURVIVAL)) {
									ItemStack ep = ItemStack.builder().itemType(ItemTypes.ENDER_PEARL).quantity(1).build();
									p.getInventory().offer(ep);
								}
								event.setCancelled(true);
							}
						}
						
						
					}
				}
			}
		}

	}
}
