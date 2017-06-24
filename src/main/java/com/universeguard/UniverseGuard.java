package com.universeguard;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.universeguard.commands.RegionAddOwnerExecutor;
import com.universeguard.commands.RegionCommandExecutor;
import com.universeguard.commands.RegionDeleteExecutor;
import com.universeguard.commands.RegionEditExecutor;
import com.universeguard.commands.RegionExecutor;
import com.universeguard.commands.RegionFlagExecutor;
import com.universeguard.commands.RegionFlagHelpExecutor;
import com.universeguard.commands.RegionGamemodeExecutor;
import com.universeguard.commands.RegionGlobalExecutor;
import com.universeguard.commands.RegionGlobalListExecutor;
import com.universeguard.commands.RegionHelpExecutor;
import com.universeguard.commands.RegionInfoExecutor;
import com.universeguard.commands.RegionListExecutor;
import com.universeguard.commands.RegionNameExecutor;
import com.universeguard.commands.RegionPriorityExecutor;
import com.universeguard.commands.RegionRemoveOwnerExecutor;
import com.universeguard.commands.RegionSaveExecutor;
import com.universeguard.commands.RegionSetSpawnExecutor;
import com.universeguard.commands.RegionSetTeleportExecutor;
import com.universeguard.commands.RegionSpawnExecutor;
import com.universeguard.commands.RegionTeleportExecutor;
import com.universeguard.config.ConfigurationManager;
import com.universeguard.events.EventLeftClick;
import com.universeguard.events.EventRightClick;
import com.universeguard.events.flags.EventBlockBreak;
import com.universeguard.events.flags.EventBlockExplosion;
import com.universeguard.events.flags.EventBlockPlace;
import com.universeguard.events.flags.EventBlockUse;
import com.universeguard.events.flags.EventChat;
import com.universeguard.events.flags.EventCommandSend;
import com.universeguard.events.flags.EventDecay;
import com.universeguard.events.flags.EventExperienceDrop;
import com.universeguard.events.flags.EventFlow;
import com.universeguard.events.flags.EventHunger;
import com.universeguard.events.flags.EventItemDrop;
import com.universeguard.events.flags.EventItemUse;
import com.universeguard.events.flags.EventPvp;
import com.universeguard.events.flags.EventSetGamemode;
import com.universeguard.events.flags.EventSleep;
import com.universeguard.events.flags.EventSpawnEnderPearl;
import com.universeguard.events.flags.EventSpawnEntity;
import com.universeguard.events.flags.EventSpawnItem;
import com.universeguard.events.flags.EventSpawnPotion;
import com.universeguard.events.flags.EventSpawnVehicle;
import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;
import com.universeguard.utils.Utils;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "universeguard", name = "UniverseGuard", version = "1.0.4", description="A World Guard solution for Sponge", authors = "Minehendrix")
public class UniverseGuard {

	public HashMap<Player, Region> pendings = new HashMap<Player, Region>();
	
	public static UniverseGuard instance;
	
	@Inject
	Game game;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private File configFile;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	@Inject
	private Logger logger;


	public File getConfigFile() {
		return configFile;
	}

	public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() {
		return configManager;
	}

	public Logger getLogger() {
		return logger;
	}

	@Listener
	public void onPreInit(GamePreInitializationEvent event) {
	    instance = this;
	    logger.info("Loading Universe Guard...");
	}
	
	@Listener
	public void onInit(GameInitializationEvent event) {
		ConfigurationManager.getInstance().setup(configFile, configManager);

		CommandSpec regionInfoSpec = CommandSpec.builder().description(Text.of("Get the informations of a region"))
				.executor(new RegionInfoExecutor()).
				arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))))
				.build();
		
		CommandSpec regionEditSpec = CommandSpec.builder().description(Text.of("Edit a region"))
				.executor(new RegionEditExecutor()).
				arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionDeleteSpec = CommandSpec.builder().description(Text.of("Delete a region"))
				.executor(new RegionDeleteExecutor()).
				arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionNameSpec = CommandSpec.builder().description(Text.of("Rename a region"))
				.executor(new RegionNameExecutor()).
				arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionPrioritySpec = CommandSpec.builder().description(Text.of("Set the priority of a region"))
				.executor(new RegionPriorityExecutor()).
				arguments(GenericArguments.integer(Text.of("value")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionFlagSpec = CommandSpec.builder().description(Text.of("Set a region flag"))
				.executor(new RegionFlagExecutor()).
				arguments(GenericArguments.string(Text.of("flag")), GenericArguments.bool(Text.of("value")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionGlobalSpec = CommandSpec.builder().description(Text.of("Set a global region flag"))
				.executor(new RegionGlobalExecutor()).
				arguments(GenericArguments.string(Text.of("flag")), GenericArguments.bool(Text.of("value")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionSetTeleportSpec = CommandSpec.builder().description(Text.of("Set the teleport location of a region"))
				.executor(new RegionSetTeleportExecutor()).
				arguments(GenericArguments.integer(Text.of("locX")),
						GenericArguments.integer(Text.of("locY")),
						GenericArguments.integer(Text.of("locZ")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionSetSpawnSpec = CommandSpec.builder().description(Text.of("Set the spawn location of a region"))
				.executor(new RegionSetSpawnExecutor()).
				arguments(GenericArguments.integer(Text.of("locX")),
						GenericArguments.integer(Text.of("locY")),
						GenericArguments.integer(Text.of("locZ")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionGamemodeSpec = CommandSpec.builder().description(Text.of("Set the gamemode of a region"))
				.executor(new RegionGamemodeExecutor()).
				arguments(GenericArguments.string(Text.of("gamemode")))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionSaveSpec = CommandSpec.builder().description(Text.of("Save a region"))
				.permission(Utils.getPermission("permission"))
				.executor(new RegionSaveExecutor()).build();
		
		CommandSpec regionGlobalListSpec = CommandSpec.builder().description(Text.of("Display the value of global region flags"))
				.permission(Utils.getPermission("permission"))
				.executor(new RegionGlobalListExecutor()).build();
		
		CommandSpec regionListSpec = CommandSpec.builder().description(Text.of("List the names of all regions"))
				.executor(new RegionListExecutor()).build();
		
		CommandSpec regionAddOwnerSpec = CommandSpec.builder().description(Text.of("Add a owner to the region"))
				.permission(Utils.getPermission("permission"))
				.executor(new RegionAddOwnerExecutor()).
				arguments(GenericArguments.player(Text.of("name")),GenericArguments.optional(GenericArguments.string(Text.of("region"))))
				.build();
		
		CommandSpec regionRemoveOwnerSpec = CommandSpec.builder().description(Text.of("Remove a owner from the region"))
				.permission(Utils.getPermission("permission"))
				.executor(new RegionRemoveOwnerExecutor()).
				arguments(GenericArguments.player(Text.of("name")), GenericArguments.optional(GenericArguments.string(Text.of("region"))))
				.build();
		
		CommandSpec regionTeleportSpec = CommandSpec.builder().description(Text.of("Teleport the player to the teleport location of the specified region"))
				.executor(new RegionTeleportExecutor()).
				arguments(GenericArguments.string(Text.of("name")))
				.build();
		
		CommandSpec regionSpawnSpec = CommandSpec.builder().description(Text.of("Teleport the player to the spawn location of the specified region"))
				.executor(new RegionSpawnExecutor()).
				arguments(GenericArguments.string(Text.of("name")))
				.build();
		
		CommandSpec regionCmdSpec = CommandSpec.builder().description(Text.of("Enable or disable a command in the region"))
				.executor(new RegionCommandExecutor()).
				arguments(GenericArguments.string(Text.of("name")),GenericArguments.optional(GenericArguments.string(Text.of("region"))))
				.permission(Utils.getPermission("permission"))
				.build();
		
		CommandSpec regionHelpSpec = CommandSpec.builder().description(Text.of("Help"))
				.executor(new RegionHelpExecutor())
				.arguments(GenericArguments.optional(GenericArguments.integer(Text.of("page"))))
				.build();
		
		CommandSpec regionFlagHelpSpec = CommandSpec.builder().description(Text.of("Flags Help"))
				.executor(new RegionFlagHelpExecutor())
				.arguments(GenericArguments.optional(GenericArguments.integer(Text.of("page"))))
				.build();
		
		CommandSpec regionCommandSpec = CommandSpec.builder().description(Text.of("Region command"))
				.executor(new RegionExecutor())
				.child(regionSaveSpec, "save")
				.child(regionInfoSpec, "info")
				.child(regionDeleteSpec, "delete")
				.child(regionNameSpec, "name")
				.child(regionListSpec, "list")
				.child(regionGamemodeSpec, "gamemode")
				.child(regionEditSpec, "edit")
				.child(regionFlagSpec, "flag")
				.child(regionAddOwnerSpec, "add")
				.child(regionRemoveOwnerSpec, "remove")
				.child(regionGlobalSpec, "global")
				.child(regionGlobalListSpec, "globallist")
				.child(regionSetTeleportSpec, "setteleport")
				.child(regionSetSpawnSpec, "setspawn")
				.child(regionTeleportSpec, "teleport")
				.child(regionSpawnSpec, "spawn")
				.child(regionPrioritySpec, "priority")
				.child(regionCmdSpec, "command")
				.child(regionHelpSpec, "help")
				.child(regionFlagHelpSpec, "flaghelp")
				.build();

		Sponge.getCommandManager().register(this, regionCommandSpec, Lists.newArrayList("region", "rg"));
		
		game.getEventManager().registerListeners(this, new EventLeftClick());
		game.getEventManager().registerListeners(this, new EventRightClick());
		
		game.getEventManager().registerListeners(this, new EventBlockPlace());
		game.getEventManager().registerListeners(this, new EventBlockBreak());
		game.getEventManager().registerListeners(this, new EventPvp());
		game.getEventManager().registerListeners(this, new EventBlockExplosion());
		game.getEventManager().registerListeners(this, new EventItemDrop());
		game.getEventManager().registerListeners(this, new EventExperienceDrop());
		game.getEventManager().registerListeners(this, new EventItemUse());
		game.getEventManager().registerListeners(this, new EventSpawnEnderPearl());
		game.getEventManager().registerListeners(this, new EventSleep());
		game.getEventManager().registerListeners(this, new EventBlockUse());
		game.getEventManager().registerListeners(this, new EventSpawnVehicle());
		game.getEventManager().registerListeners(this, new EventChat());
		game.getEventManager().registerListeners(this, new EventFlow());
		game.getEventManager().registerListeners(this, new EventDecay());
		game.getEventManager().registerListeners(this, new EventSpawnItem());
		game.getEventManager().registerListeners(this, new EventCommandSend());
		game.getEventManager().registerListeners(this, new EventSpawnPotion());
		game.getEventManager().registerListeners(this, new EventSpawnEntity());
		
		
		Task.builder().execute(new EventSetGamemode())
		        .interval(1, TimeUnit.SECONDS)
		        .name("Set Gamemode Timer Task").submit(instance);
		Task.builder().execute(new EventHunger())
        .interval(1, TimeUnit.SECONDS)
        .name("Hunger Timer Task").submit(instance);
		
		
		logger.info("Universe Guard loaded!");
	}
	
	@Listener
	public void onStart(GameStartedServerEvent event) {
		RegionUtils.loadGlobalRegions();
	}
}
