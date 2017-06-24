package com.universeguard.region;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.universeguard.utils.Utils;

@SuppressWarnings("unused")
public class Region {

	private Location<World> pos1;
	private Location<World> pos2;
	private int dimension;
	private String world;
	
	private String name;
	private int priority;
	
	private ArrayList<UUID> owners;
	
	private GameMode gamemode;
	
	private Location<World> teleport;
	private Location<World> spawn;

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
	private boolean firespread;
	private boolean potionsplash;
	private boolean falldamage;
	private boolean cantp;
	private boolean canspawn;
	private boolean hunger;
	private boolean enderchests;
	private boolean walldamage;
	private boolean drown;
	private boolean animals;
	private boolean tntdamage;
	private boolean otherexplosionsdamage;
	private boolean invincible;
	private boolean cactusdamage;
	private boolean firedamage;
	private boolean mobpvp;
	private boolean animalspvp;
	
	
	/*private boolean lightning;
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

	public Region(Location<World> p1, Location<World> p2, int d, String w) {
		this.pos1 = p1;
		this.pos2 = p2;
		this.dimension = d;
		this.world = w;
		name = "";
		priority = 0;
		owners = new ArrayList<UUID>();
		commands = new ArrayList<String>();
		gamemode = GameModes.NOT_SET;
		teleport = this.pos1;
		spawn = this.pos1;
		initFlags();
	}
	
	public Region(int x1, int y1, int z1, int x2, int y2, int z2, int d, String w) {
		this(new Location<World>(Sponge.getServer().getWorld(w).get(), x1, y1,z1), new Location<World>(Sponge.getServer().getWorld(w).get(), x2, y2,z2), d, w);
	}
	
	public void setGameMode(String value) {
		if(value.equalsIgnoreCase("adventure"))
			this.gamemode = GameModes.ADVENTURE;
		else if(value.equalsIgnoreCase("creative"))
			this.gamemode = GameModes.CREATIVE;
		else if(value.equalsIgnoreCase("spectator"))
			this.gamemode = GameModes.SPECTATOR;
		else if(value.equalsIgnoreCase("survival"))
			this.gamemode = GameModes.SURVIVAL;
		else 
			this.gamemode = GameModes.NOT_SET;
	}
	
	public GameMode getGameMode() {
		return this.gamemode;
	}
	
	public void setWorld(String s) {
		this.world = s;
	}
	
	public String getWorld() {
		return this.world;
	}
	
	public void setTeleport(Location<World> l) {
		this.teleport = l;
	}
	
	public Location<World> getTeleport() {
		return this.teleport;
	}
	
	public void setSpawn(Location<World> l) {
		this.spawn = l;
	}
	
	public Location<World> getSpawn() {
		return this.spawn;
	}
	
	public void setPos1(Location<World> l) {
		this.pos1 = l;
	}
	
	public Location<World> getPos1() {
		return this.pos1;
	}
	
	public void setPos2(Location<World> l) {
		this.pos2 = l;
	}
	
	public Location<World> getPos2() {
		return this.pos2;
	}
	
	public void setDimension(int d) {
		this.dimension = d;
	}
	
	public int getDimension() {
		return this.dimension;
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
	
	public void addOwner(UUID p) {
		if(!this.owners.contains(p))
			owners.add(p);
	}
	
	public void removeOwner(UUID p) {
		if(this.owners.contains(p))
			this.owners.remove(p);
	}
	
	public void setOwners(ArrayList<UUID> p) {
		this.owners = p;
	}
	
	public ArrayList<UUID> getOwners() {
		return this.owners;
	}
	
	public boolean isOwner(UUID p) {
		return this.owners.contains(p);
	}
	
	public void setPriority(int p) {
		this.priority = p;
	}
	
	public int getPriority() {
		return this.priority;
	}
	public boolean setFlag(String flag, boolean value) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equalsIgnoreCase(flag))
				try {
					this.getClass().getDeclaredField(flag).setBoolean(this, value);
					return true;
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e) {
					e.printStackTrace();
				}
		}
		return false;
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
		Field[] fields = Region.class.getDeclaredFields();
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
