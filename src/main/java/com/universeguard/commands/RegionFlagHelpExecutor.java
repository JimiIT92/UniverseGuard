package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.utils.Utils;

public class RegionFlagHelpExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			Utils.sendMessage(player, TextColors.GOLD, "--------------------Flags Help--------------------");
			Utils.sendMessage(player, TextColors.YELLOW, "build - Sets if non-members can place/break blocks in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "pvp - Sets if players can pvp in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "mobdamage - Sets if mobs can deal damage to players in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "expdrop - Sets if mobs can drop experience in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "creeperexplosions - Sets if creepers will break blocks in the region when they explode");
			Utils.sendMessage(player, TextColors.YELLOW, "otherexplosions - Sets if explosions will break blocks in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "endermangrief - Sets if enderman are allowed to break/place blocks in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "enderpearl - Sets if non-members are allowed to use enderpearls in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "enderdragonblockdamage - Sets if the enderdragon will break blocks in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "sleep - Sets if non-members are allowed to sleep in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "tnt - Sets if tnt explosion will break blocks in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "lighter - Sets if non-members are allowed to use lighter in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "chests - Sets if non-members are allowed to open chests/trapped chests in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "waterflow - Sets if water can flow in the region. This also enable/disable water buckets at all");
			Utils.sendMessage(player, TextColors.YELLOW, "lavaflow - Sets if lava can flow in the region. This also enable/disable lava buckets at all");
			Utils.sendMessage(player, TextColors.YELLOW, "use - Sets if non-members are allowed to use containers in the region. This will not consider crafting tables, anvils and enchanting tables");
			Utils.sendMessage(player, TextColors.YELLOW, "vehicleplace - Sets if non-members are allowed to place vehicles in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "vehicledestroy - Sets if non-members are allowed to destroy vehicles in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "leafdecay - Sets if leaves will decay in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "sendchat - Sets if non-members are allowed to send chat messages while in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "potionsplash - Sets if non-members are allowed to thrown potions in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "falldamage - Sets if players can take fall damage in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "walldamage - Sets if players can take suffocate in walls in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "drown - Sets if players can drown in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "cantp - Sets if players can use the /region teleport command while in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "canspawn - Sets if players can use the /region spawn command while in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "hunger - Sets if players will loose hunger points while in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "enderchests - Sets if non-members can open enderchests in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "animals - Sets if non-hostile mobs can spawn in the region");
			Utils.sendMessage(player, TextColors.YELLOW, "mobs - Sets if hostile mobs can spawn in the region");
			Utils.sendMessage(player, TextColors.GOLD, "--------------------------------------------");
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
