package com.universeguard.events.flags;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventPvp {

	@Listener
	public void onPvp(InteractEntityEvent.Primary event, @Root Player player) {
		if (event.getTargetEntity() instanceof Player) {
			Player p = (Player) event.getTargetEntity();
			Region r1 = RegionUtils.load(player.getLocation());
			Region r2 = RegionUtils.load(p.getLocation());
			if (r1 == null && r2 == null) {
				GlobalRegion gr = RegionUtils.loadGlobal(player.getWorld().getName());
				if(gr != null)
					event.setCancelled(!gr.getFlag("pvp"));
			}
			if (r1 != null && r2 == null) {
				event.setCancelled(!r1.getFlag("pvp"));
			} else if (r1 == null && r2 != null)
				event.setCancelled(!r2.getFlag("pvp"));
			else if (r1 != null && r2 != null)
				event.setCancelled(!r1.getFlag("pvp") || !r2.getFlag("pvp"));
		}
	}

	@Listener
	public void onDamageByEntity(DamageEntityEvent event, @Root DamageSource src, @First EntityDamageSource entity) {
		if (event.getTargetEntity() instanceof Player) {
			Entity e = entity.getSource();
			Player p = (Player) event.getTargetEntity();
			Region r = RegionUtils.load(p.getLocation());
			if (src.getType() == DamageTypes.FIRE || src.getType() == DamageTypes.MAGMA) {
				if (r == null) {
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("pvp"));
				}
					
				else
					event.setCancelled(!r.getFlag("pvp"));
			} else if (src.getType() == DamageTypes.EXPLOSIVE) {
				if (e.getType() == EntityTypes.DRAGON_FIREBALL || e.getType() == EntityTypes.ENDER_CRYSTAL
						|| e.getType() == EntityTypes.SMALL_FIREBALL || e.getType() == EntityTypes.FIREBALL) {
					if (r == null) {
						GlobalRegion gr = RegionUtils.loadGlobal(e.getWorld().getName());
						if(gr != null)
							event.setCancelled(!gr.getFlag("otherexplosionsdamage"));
					}
					else
						event.setCancelled(!r.getFlag("otherexplosionsdamage"));
				} else if (e.getType() == EntityTypes.TNT_MINECART || e.getType() == EntityTypes.PRIMED_TNT) {
					if (r == null) {
						GlobalRegion gr = RegionUtils.loadGlobal(e.getWorld().getName());
						if(gr != null)
							event.setCancelled(!gr.getFlag("tntdamage"));
					}
					else
						event.setCancelled(!r.getFlag("tntdamage"));
				} else {
					if (r == null) {
						GlobalRegion gr = RegionUtils.loadGlobal(e.getWorld().getName());
						if(gr != null)
							event.setCancelled(!gr.getFlag("mobdamage"));
					}
					else
						event.setCancelled(!r.getFlag("mobdamage"));
				}

			} else if (src.getType() == DamageTypes.PROJECTILE || src.getType() == DamageTypes.ATTACK) {
				if (e.getType() == EntityTypes.SPECTRAL_ARROW || e.getType() == EntityTypes.TIPPED_ARROW) {
					Arrow projectile = (Arrow) e;
					if (projectile != null) {
						if (projectile.getShooter() instanceof Monster) {
							if (r == null) {
								GlobalRegion gr = RegionUtils.loadGlobal(e.getWorld().getName());
								if(gr != null)
									event.setCancelled(!gr.getFlag("mobdamage"));
							}
							else
								event.setCancelled(!r.getFlag("mobdamage"));
						} else {
							if (r == null) {
								GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
								if(gr != null)
									event.setCancelled(!gr.getFlag("pvp"));
							}
							else
								event.setCancelled(!r.getFlag("pvp"));
						}

					}
				}

			} 
			
			else {
				if (r == null) {
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("mobdamage"));
				}
				else
					event.setCancelled(!r.getFlag("mobdamage"));
			}

		}
	}
	
	@Listener
	public void onDamage(DamageEntityEvent event, @Root DamageSource src) {
		if (event.getTargetEntity() instanceof Player) {
			Player p = (Player) event.getTargetEntity();
			Region r = RegionUtils.load(p.getLocation());
			if(r != null) {
				if(r.getFlag("invincible")) {
					event.setCancelled(true);
					return;
				}
				
			} else {
				GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
				if(gr != null) {
					if(gr.getFlag("invincible")) {
						event.setCancelled(true);
						return;
					}
					
				}
			}
			if (src.getType() == DamageTypes.FALL) {
				if (r == null) {
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("falldamage"));
				}
				else
					event.setCancelled(!r.getFlag("falldamage"));
			}
			else if (src.getType() == DamageTypes.DROWN) {
				if (r == null)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("drown"));
				}
				else
					event.setCancelled(!r.getFlag("drown"));
			}
			else if (src.getType() == DamageTypes.HUNGER) {
				if (r == null)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("hunger"));
				}
				else
					event.setCancelled(!r.getFlag("hunger"));
			}
			else if (src.getType() == DamageTypes.SUFFOCATE) {
				if (r == null)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("walldamage"));
				}
				else
					event.setCancelled(!r.getFlag("walldamage"));
			}
			else if(src.getType() == DamageTypes.CONTACT) {
				if (r == null)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("cactusdamage"));
				}
				else
					event.setCancelled(!r.getFlag("cactusdamage"));
			}
			else if(src.getType() == DamageTypes.FIRE) {
				if (r == null)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("firedamage"));
				}
				else
					event.setCancelled(!r.getFlag("firedamage"));
			}
			else {
				if (r == null)
				{
					GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
					if(gr != null)
						event.setCancelled(!gr.getFlag("mobdamage"));
				}
				else
					event.setCancelled(!r.getFlag("mobdamage"));
			}

		}
	}
}
