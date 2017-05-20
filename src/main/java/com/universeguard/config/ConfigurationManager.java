package com.universeguard.config;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigurationManager {

	private static ConfigurationManager instance = new ConfigurationManager();
	
	public static ConfigurationManager getInstance() {
		return instance;
	}
	
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;
	private CommentedConfigurationNode config;
	
	public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
		this.configLoader = configLoader;
		
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
				loadConfig();
				config.getNode("permission").setComment("The permission to run region commands and bypass region restrictions").setValue("staff");
				config.getNode("bypass").setComment("The permission to bypass region restrictions").setValue("bypass");
				
				config.getNode("flags").setComment("Default flag values. Will be used as flags for the global regions");
				config.getNode("flags").getNode("build").setValue(true);
				config.getNode("flags").getNode("pvp").setValue(true);
				config.getNode("flags").getNode("mobdamage").setValue(true);
				config.getNode("flags").getNode("expdrop").setValue(true);
				config.getNode("flags").getNode("itemdrop").setValue(true);
				config.getNode("flags").getNode("mobs").setValue(true);
				config.getNode("flags").getNode("creeperexplosions").setValue(true);
				config.getNode("flags").getNode("otherexplosions").setValue(true);
				config.getNode("flags").getNode("endermangrief").setValue(true);
				config.getNode("flags").getNode("enderpearl").setValue(true);
				config.getNode("flags").getNode("enderdragonblockdamage").setValue(true);
				config.getNode("flags").getNode("sleep").setValue(true);
				config.getNode("flags").getNode("tnt").setValue(true);
				config.getNode("flags").getNode("lighter").setValue(true);
				config.getNode("flags").getNode("chests").setValue(false);
				config.getNode("flags").getNode("waterflow").setValue(true);
				config.getNode("flags").getNode("lavaflow").setValue(true);
				config.getNode("flags").getNode("use").setValue(true);
				config.getNode("flags").getNode("vehicleplace").setValue(false);
				config.getNode("flags").getNode("vehicledestroy").setValue(false);
				config.getNode("flags").getNode("leafdecay").setValue(true);
				config.getNode("flags").getNode("sendchat").setValue(true);
				config.getNode("flags").getNode("firespread").setValue(true);
				config.getNode("flags").getNode("potionsplash").setValue(true);
				config.getNode("flags").getNode("falldamage").setValue(true);
				config.getNode("flags").getNode("cantp").setValue(true);
				config.getNode("flags").getNode("canspawn").setValue(true);
				config.getNode("flags").getNode("hunger").setValue(true);
				config.getNode("flags").getNode("commands").setValue(true);
				config.getNode("flags").getNode("enderchests").setValue(true);
				config.getNode("flags").getNode("walldamage").setValue(true);
				config.getNode("flags").getNode("drown").setValue(true);
				config.getNode("flags").getNode("animals").setValue(true);
				config.getNode("flags").getNode("tntdamage").setValue(true);
				config.getNode("flags").getNode("otherexplosionsdamage").setValue(true);
				config.getNode("flags").getNode("invincible").setValue(false);
				config.getNode("flags").getNode("cactusdamage").setValue(true);
				config.getNode("flags").getNode("firedamage").setValue(true);
				
				/*config.getNode("flags").getNode("lightning").setValue(true);		
				config.getNode("flags").getNode("pistons").setValue(true);				
				config.getNode("flags").getNode("snowfall").setValue(true);
				config.getNode("flags").getNode("snowmelt").setValue(true);
				config.getNode("flags").getNode("iceform").setValue(true);
				config.getNode("flags").getNode("icemelt").setValue(true);
				config.getNode("flags").getNode("mushroomgrowth").setValue(true);
				config.getNode("flags").getNode("grassgrowth").setValue(true);
				config.getNode("flags").getNode("myceliumspread").setValue(true);
				config.getNode("flags").getNode("vinegrowth").setValue(true);
				config.getNode("flags").getNode("receivechat").setValue(true);*/
				
				saveConfig();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			loadConfig();
		}
	}
	
	public CommentedConfigurationNode getConfig() {
		return config;
	}
	
	public ConfigurationLoader<CommentedConfigurationNode> getConfigLoader() {
		return configLoader;
	}
	
	public void saveConfig() {
		try {
			configLoader.save(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		try {
			config = configLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
