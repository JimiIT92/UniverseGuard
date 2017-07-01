package com.universeguard.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.universeguard.region.GlobalRegion;
import com.universeguard.region.Region;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class RegionUtils {

	public static void loadGlobalRegions() {
		for(World w : Sponge.getServer().getWorlds()) {
			if(RegionUtils.loadGlobal(w.getName()) == null)
				RegionUtils.saveGlobal(new GlobalRegion(w.getName()));
		}
	}
	
	public static ArrayList<Region> getAllRegions() {
		ArrayList<Region> regions = new ArrayList<Region>();
		File dir = new File("config/universeguard/regions");
		if (dir.exists()) {
			File[] files = dir.listFiles();

			for (File file : files) {
				if (file.exists()) {
					Region r = getByName(file.getName().substring(0, file.getName().indexOf(".json")));
					if (r != null)
						regions.add(r);
				}
			}

		}

		return regions;
	}

	public static boolean isInRegion(Region r, Location<World> l) {
		Location<World> pos1 = r.getPos1();
		Location<World> pos2 = r.getPos2();
		
		int x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
		int y1 = Math.min(pos1.getBlockY(), pos2.getBlockY());
		int z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
		int x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
		int y2 = Math.max(pos1.getBlockY(), pos2.getBlockY());
		int z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
		
		return r.getWorld().equalsIgnoreCase(l.getExtent().getName())
				&& r.getDimension().equals(l.getExtent().getDimension().getType().getId())
				&& ((l.getBlockX() >= x1 && l.getBlockX() <= x2)
				&& (l.getBlockY() >= y1 && l.getBlockY() <= y2)
				&& (l.getBlockZ() >= z1 && l.getBlockZ() <= z2));
	}

	public static Region load(Location<World> location) {
		ArrayList<Region> regions = new ArrayList<Region>();
		if(getAllRegions() != null) {
			for (Region r : getAllRegions()) {
				if (isInRegion(r, location))
					regions.add(r);
			}
		}
		
		if(!regions.isEmpty()) {
			Region r = regions.get(0);
			for(Region reg : regions) {
				if(reg.getPriority() > r.getPriority())
					r = reg;
			}
			return r;
		}
		
		return null;
	}
	
	public static void save(Region r) {
		String name = r.getName();
		if(name.isEmpty()) {
			name = "Region";
			int d = 0;
			for(Region region : getAllRegions()) {
				String n = region.getName();
				if(n.startsWith("Region"))
					d++;
			}
			name += String.valueOf(d);
		}
		File file = new File("config/universeguard/regions/" + name + ".json");
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder().setFile(file).build();

		ConfigurationNode root;

		try {
			root = loader.load();
		} catch (IOException e) {
			root = loader.createEmptyNode(ConfigurationOptions.defaults());
		}

		ConfigurationNode region = root.getNode(name);

		HashMap<String, Integer> pos1 = new HashMap<String, Integer>();
		pos1.put("x", r.getPos1().getBlockX());
		pos1.put("y", r.getPos1().getBlockY());
		pos1.put("z", r.getPos1().getBlockZ());
		region.getNode("pos1").setValue(pos1);

		HashMap<String, Integer> pos2 = new HashMap<String, Integer>();
		pos2.put("x", r.getPos2().getBlockX());
		pos2.put("y", r.getPos2().getBlockY());
		pos2.put("z", r.getPos2().getBlockZ());
		region.getNode("pos2").setValue(pos2);

		region.getNode("dimension").setValue(r.getDimension());
		region.getNode("world").setValue(r.getWorld());
		region.getNode("priority").setValue(r.getPriority());
		region.getNode("gamemode").setValue(r.getGameMode().getId());

		HashMap<String, Integer> teleport = new HashMap<String, Integer>();
		teleport.put("x", r.getTeleport().getBlockX());
		teleport.put("y", r.getTeleport().getBlockY());
		teleport.put("z", r.getTeleport().getBlockZ());
		region.getNode("teleport").setValue(teleport);

		HashMap<String, Integer> spawn = new HashMap<String, Integer>();
		spawn.put("x", r.getSpawn().getBlockX());
		spawn.put("y", r.getSpawn().getBlockY());
		spawn.put("z", r.getSpawn().getBlockZ());
		region.getNode("spawn").setValue(spawn);

		HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
		for (int i = 0; i < r.getAllFlags().size(); i++) {
			flags.put(Region.getFlagNames().get(i), r.getAllFlags().get(i));
		}

		ArrayList<String> owners = new ArrayList<String>();
		for (int i = 0; i < r.getOwners().size(); i++) {
			owners.add(r.getOwners().get(i).toString());
		}

		region.getNode("flags").setValue(flags);
		region.getNode("owners").setValue(owners);
		
		ArrayList<String> commands = new ArrayList<String>();
		for (int i = 0; i < r.getCommands().size(); i++) {
			commands.add(r.getCommands().get(i).toString());
		}
		
		region.getNode("commands").setValue(commands);

		try {
			loader.save(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Region getByName(String name) {
		File file = new File("config/universeguard/regions/" + name + ".json");
		if (!file.exists()) {
			return null;
		}

		ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder().setFile(file).build();

		ConfigurationNode root;

		try {
			root = loader.load();
		} catch (IOException e) {
			root = loader.createEmptyNode(ConfigurationOptions.defaults());
		}

		ConfigurationNode region = root.getNode(name);
		if(Sponge.getServer().getWorld(region.getNode("world").getString()).isPresent()) {
			World world = Sponge.getServer().getWorld(region.getNode("world").getString()).get();
			Location<World> pos1 = new Location<World>(world, region.getNode("pos1").getNode("x").getInt(),
					region.getNode("pos1").getNode("y").getInt(), region.getNode("pos1").getNode("z").getInt());

			Location<World> pos2 = new Location<World>(world, region.getNode("pos2").getNode("x").getInt(),
					region.getNode("pos2").getNode("y").getInt(), region.getNode("pos2").getNode("z").getInt());

			Location<World> teleport = new Location<World>(world, region.getNode("teleport").getNode("x").getInt(),
					region.getNode("teleport").getNode("y").getInt(), region.getNode("teleport").getNode("z").getInt());

			Location<World> spawn = new Location<World>(world, region.getNode("spawn").getNode("x").getInt(),
					region.getNode("spawn").getNode("y").getInt(), region.getNode("spawn").getNode("z").getInt());

			String d = region.getNode("dimension").getString();

			String w = region.getNode("world").getString();
			


			Region r = new Region(pos1, pos2, d, w);

			r.setName(name);
			r.setPriority(region.getNode("priority").getInt());
			r.setGameMode(region.getNode("gamemode").getString());
			r.setTeleport(teleport);
			r.setSpawn(spawn);

			for (int i = 0; i < r.getAllFlags().size(); i++) {
				r.setFlag(Region.getFlagNames().get(i), region.getNode("flags").getNode(Region.getFlagNames().get(i)).getBoolean());
			}

			ArrayList<UUID> owners = new ArrayList<UUID>();
			for (int i = 0; i < region.getNode("owners").getChildrenList().size(); i++) {
				String s = region.getNode("owners").getChildrenList().get(i).getString();
				UUID id = UUID.fromString(s);
				owners.add(id);
			}

			r.setOwners(owners);
			
			ArrayList<String> commands = new ArrayList<String>();
			for (int i = 0; i < region.getNode("commands").getChildrenList().size(); i++) {
				String s = region.getNode("commands").getChildrenList().get(i).getString();
				commands.add(s);
			}
			
			r.setCommands(commands);

			return r;
		}
		return null;
	}
	
	public static void delete(Player p, String name) {
		File file = new File("config/universeguard/regions/" + name + ".json");
		if (file.exists()) {
			if(file.delete())
				Utils.sendMessage(p, TextColors.GREEN, "Region ", name, " successfully deleted!");
			else
				Utils.sendMessage(p, TextColors.RED, "Can't delete the region ", name, "!");
		}
		else
			Utils.sendMessage(p, TextColors.RED, "Can't find the region ", name, "!");
	}
	
	public static void delete(String name) {
		File file = new File("config/universeguard/regions/" + name + ".json");
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static void delete(Player p, Location<World> l) {
		Region r = load(l);
		if(r != null)
			delete(p, r.getName());
		else
			Utils.sendMessage(p, TextColors.RED, "There's no region here!");
		
	}
	
	public static boolean hasPermission(Player p, Region r) {
		return r.getOwners().contains(p.getUniqueId()) || p.hasPermission(Utils.getPermission("permission")) || p.hasPermission(Utils.getPermission("bypass"));
	}
	
	public static boolean hasGlobalPermission(Player p) {
		return p.hasPermission(Utils.getPermission("permission")) || p.hasPermission(Utils.getPermission("bypass"));
	}
	
	
	public static GlobalRegion loadGlobal(String name) {
		File file = new File("config/universeguard/globals/" + name + ".json");
		if (!file.exists()) {
			return null;
		}

		ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder().setFile(file).build();

		ConfigurationNode root;

		try {
			root = loader.load();
		} catch (IOException e) {
			root = loader.createEmptyNode(ConfigurationOptions.defaults());
		}

		ConfigurationNode region = root.getNode(name);
		String w = region.getNode("world").getString();

		GlobalRegion r = new GlobalRegion(w);

		r.setName(name);

		for (int i = 0; i < r.getAllFlags().size(); i++) {
			r.setFlag(Region.getFlagNames().get(i), region.getNode("flags").getNode(Region.getFlagNames().get(i)).getBoolean());
		}
		
		ArrayList<String> commands = new ArrayList<String>();
		for (int i = 0; i < region.getNode("commands").getChildrenList().size(); i++) {
			String s = region.getNode("commands").getChildrenList().get(i).getString();
			commands.add(s);
		}
		
		r.setCommands(commands);

		return r;
	}
	
	public static void saveGlobal(GlobalRegion r) {
		String name = r.getName();
		File file = new File("config/universeguard/globals/" + name + ".json");
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder().setFile(file).build();

		ConfigurationNode root;

		try {
			root = loader.load();
		} catch (IOException e) {
			root = loader.createEmptyNode(ConfigurationOptions.defaults());
		}

		ConfigurationNode region = root.getNode(name);

		region.getNode("world").setValue(r.getWorld());
		

		HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
		for (int i = 0; i < r.getAllFlags().size(); i++) {
			flags.put(Region.getFlagNames().get(i), r.getAllFlags().get(i));
		}

		region.getNode("flags").setValue(flags);
		
		ArrayList<String> commands = new ArrayList<String>();
		for (int i = 0; i < r.getCommands().size(); i++) {
			commands.add(r.getCommands().get(i).toString());
		}
		
		region.getNode("commands").setValue(commands);

		try {
			loader.save(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
