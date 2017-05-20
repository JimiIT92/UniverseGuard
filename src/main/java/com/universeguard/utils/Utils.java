package com.universeguard.utils;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.universeguard.config.ConfigurationManager;

public class Utils {

	public static void sendMessage(Player player, Object... args) {
		if (args.length != 0) {
			player.sendMessage(Text.of(args));
		}
	}

	public static ItemStack getSelector() {
		ItemStack selector = ItemStack.builder().itemType(ItemTypes.STICK).build();
		selector.offer(Keys.DISPLAY_NAME, Text.of(TextColors.LIGHT_PURPLE, "Region selector"));

		return selector.createSnapshot().createStack();
	}

	public static ConfigurationManager getConfig() {
		return ConfigurationManager.getInstance();
	}

	public static boolean isSelector(ItemStack stack) {
		ItemStack selector = getSelector();
		return stack.getItem() == selector.getItem() && 
				stack.get(Keys.DISPLAY_NAME).get().compareTo(selector.get(Keys.DISPLAY_NAME).get()) == 0;
	}
	
	public static String locationToString(Location<World> l) {
		return l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ();
	}
	
	public static Text getUser(UUID uuid) {
	    Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(uuid);

	    if (onlinePlayer.isPresent()) {
	        return Text.of(TextColors.GREEN, onlinePlayer.get().getName(), ", ");
	    }

	    Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);

	    return Text.of(TextColors.RED, userStorage.get().get(uuid).get().getName(), ", ");
	}
	
	public static String getPermission(String p) {
		return getConfig().getConfig().getNode(p).getString();
	}
	
}
