package com.universeguard.events.flags;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventSetGamemode implements Runnable {
   
	@Override
    public void run() {
        for(Player p : Sponge.getServer().getOnlinePlayers()) {
        	Region r = RegionUtils.load(p.getLocation());
        	if(r != null && r.getGameMode() != GameModes.NOT_SET) {
        		if(!RegionUtils.hasPermission(p, r)) {
        			GameMode g = r.getGameMode();
        			if(p.gameMode().get() != g)
        				p.offer(p.getGameModeData().set(Keys.GAME_MODE, r.getGameMode()));
        		}
        	}
        }
    }
	
}
