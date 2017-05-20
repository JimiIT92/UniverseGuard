package com.universeguard.region;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.universeguard.utils.Utils;

@SuppressWarnings("unused")
public class GlobalRegion {

	private String world;
	private String name;

	private ArrayList<String> commands;
	private boolean build;
	private boolean pvp;
	private boolean mobdamage;
	private boolean expdrop;
	private boolean itemdrop;
	private boolean mobs;
	private boolean creeperexplosions;
	private boolean otherexplosions;
	private boolean endermangrief;
	private boolean enderpearl;
	private boolean enderdragonblockdamage;
	private boolean sleep;
	private boolean tnt;
	private boolean lighter;
	private boolean chests;
	private boolean waterflow;
	private boolean lavaflow;
	private boolean use;
	private boolean vehicleplace;
	private boolean vehicledestroy;
	private boolean leafdecay;
	private boolean sendchat;
	
	/*private boolean firespread;
	private boolean lavafire;
	private boolean lightning;
	private boolean pistons;
	private boolean snowfall;
	private boolean snowmelt;
	private boolean iceform;
	private boolean icemelt;
	private boolean mushroomgrowth;
	private boolean grassgrowth;
	private boolean myceliumspread;
	private boolean vinegrowth;
	private boolean receivechat;*/
	
	private boolean potionsplash;
	private boolean falldamage;
	private boolean cantp;
	private boolean canspawn;
	private boolean hunger;
	private boolean enderchests;
	private boolean walldamage;
	private boolean drown;
	private boolean animals;
	
	public GlobalRegion(String w) {
		this.world = w;
		name = w;
		commands = new ArrayList<String>();
		initFlags();
	}
	
	public void setWorld(String s) {
		this.world = s;
	}
	
	public String getWorld() {
		return this.world;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addCommand(String s) {
		if(!this.commands.contains(s))
			commands.add(s);
	}
	
	public void removeCommand(String s) {
		if(this.commands.contains(s))
			commands.remove(s);
	}
	
	public void setCommands(ArrayList<String> s) {
		this.commands = s;
	}
	
	public ArrayList<String> getCommands() {
		return this.commands;
	}
	

	public void setFlag(String flag, boolean value) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equalsIgnoreCase(flag))
				try {
					this.getClass().getDeclaredField(flag).setBoolean(this, value);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e) {
					e.printStackTrace();
				}
		}
	}
	
	public boolean getFlag(String flag) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equalsIgnoreCase(flag))
				try {
					return f.getBoolean(this);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		return false;
	}
	
	public static ArrayList<String> getFlagNames() {
		Field[] fields = GlobalRegion.class.getDeclaredFields();
		ArrayList<String> list = new ArrayList<String>();
		for (Field f : fields) {
			if (f.getType().equals(boolean.class))
				try {
					list.add(f.getName());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
	
	public ArrayList<Boolean> getAllFlags() {
		Field[] fields = this.getClass().getDeclaredFields();
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (Field f : fields) {
			if (f.getType().equals(boolean.class))
				try {
					list.add(f.getBoolean(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
	
	public void initFlags() {
		HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
		for (int i = 0; i < getAllFlags().size(); i++) {
			setFlag(getFlagNames().get(i), Utils.getConfig().getConfig().getNode("flags").getNode(getFlagNames().get(i)).getBoolean());
		}
	}
}
