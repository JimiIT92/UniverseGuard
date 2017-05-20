package com.universeguard.events.flags;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventHunger implements Runnable {
   
	@Override
    public void run() {
        for(Player p : Sponge.getServer().getOnlinePlayers()) {
        	if(p.gameMode().get() == GameModes.SURVIVAL) {
        		Region r = RegionUtils.load(p.getLocation());
            	if(r != null) {
            		if(!r.getFlag("hunger"))
            			p.offer(Keys.FOOD_LEVEL, 20);
            	} else {
            		GlobalRegion gr = RegionUtils.loadGlobal(p.getWorld().getName());
            		if(gr != null) {
            			if(!gr.getFlag("hunger"))
                			p.offer(Keys.FOOD_LEVEL, 20);
            		}
            		
            	}
        	}
        }
    }
	
}
