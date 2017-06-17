package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.Explosive;
import org.spongepowered.api.entity.living.complex.EnderDragon;
import org.spongepowered.api.entity.living.complex.EnderDragonPart;
import org.spongepowered.api.entity.living.monster.Enderman;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.explosion.Explosion;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventBlockBreak {
	
	@Listener
	public void onBlockBreakByEntity(ChangeBlockEvent.Break event) {
		if(event.getCause().root() instanceof LocatableBlock) {
			LocatableBlock b = (LocatableBlock)event.getCause().root();
			if(b.getBlockState().getType() == BlockTypes.FIRE) {
				Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
				if (r != null) {
					event.setCancelled(!r.getFlag("firespread"));
				} else {
					{
						GlobalRegion gr = RegionUtils.loadGlobal(b.getWorld().getName());
						if (gr != null)
							event.setCancelled(!gr.getFlag("firespread"));
					}
				}
			}
		}
		else if (event.getCause().root() instanceof Enderman) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("endermangrief"));
			} else {
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTransactions().get(0).getOriginal().getLocation().get().getExtent().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("endermangrief"));
				}
			}
		} else if (event.getCause().root() instanceof EnderDragon
				|| event.getCause().root() instanceof EnderDragonPart) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("enderdragonblockdamage"));
			} else {
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTransactions().get(0).getOriginal().getLocation().get().getExtent().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("enderdragonblockdamage"));
				}
			}
		} else if (!(event.getCause().root() instanceof Player) && !(event.getCause().root() instanceof Explosion) &&
				!(event.getCause().root() instanceof Explosive)) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("build"));
			} else {
				{
					GlobalRegion gr = RegionUtils.loadGlobal(event.getTransactions().get(0).getOriginal().getLocation().get().getExtent().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("build"));
				}
			}
		}

	}

	@Listener
	public void onBlockBreak(ChangeBlockEvent.Break event, @Root Player player) {
		Region r = RegionUtils.load(player.getLocation());
		if (r != null) {
			if (!RegionUtils.hasPermission(player, r))
				event.setCancelled(!r.getFlag("build"));
		} else {
			if (!RegionUtils.hasGlobalPermission(player)) {
				GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
				if (gr != null)
					event.setCancelled(!gr.getFlag("enderdragonblockdamage"));
			}
		}
	}

	@Listener
	public void onInteract(InteractEntityEvent.Primary event, @Root Player player) {
		Region r = RegionUtils.load(event.getTargetEntity().getLocation());
		if (event.getTargetEntity().getType() == EntityTypes.BOAT
				|| event.getTargetEntity().getType() == EntityTypes.RIDEABLE_MINECART) {
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("vehicledestroy"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("vehicledestroy"));
				}
			}
		} else if (event.getTargetEntity().getType() == EntityTypes.ITEM_FRAME
				|| event.getTargetEntity().getType() == EntityTypes.ARMOR_STAND || event.getTargetEntity().getType() == EntityTypes.PAINTING) {
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("build"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("build"));
				}
			}
		} else {
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("build"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player)) {
					GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
					if (gr != null)
						event.setCancelled(!gr.getFlag("build"));
				}
			}
		}

	}

	@Listener
	public void onCollide(CollideEntityEvent event) {
		for(Entity e : event.getEntities()) {
			if(e.getType() == EntityTypes.ITEM_FRAME || e.getType() == EntityTypes.PAINTING || e.getType() == EntityTypes.ARMOR_STAND) {
				Region r = RegionUtils.load(e.getLocation());
				if(r != null)
					event.setCancelled(!r.getFlag("build"));
				else {
					GlobalRegion gr = RegionUtils.loadGlobal(e.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("build"));
				}
			}
		}
		
	}

}
