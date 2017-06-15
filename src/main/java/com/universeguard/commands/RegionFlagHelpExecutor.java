package com.universeguard.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.utils.Utils;

public class RegionFlagHelpExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(src instanceof Player) {
			Player player = (Player)src;
			int page = 1;
			if(args.hasAny(Text.of("page"))) {
				page = args.<Integer>getOne("page").get();
			}
			if(page <= 1) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(1/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "build -", TextColors.WHITE, " Sets if non-members can place/break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "pvp -", TextColors.WHITE, " Sets if players can pvp in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "mobdamage -", TextColors.WHITE, " Sets if mobs can deal damage to players in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "expdrop -", TextColors.WHITE, " Sets if mobs can drop experience in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "creeperexplosions -", TextColors.WHITE, " Sets if creepers will break blocks in the region when they explode");	
			}
			if(page == 2) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(2/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "otherexplosions -", TextColors.WHITE, " Sets if explosions will break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "endermangrief -", TextColors.WHITE, " Sets if enderman are allowed to break/place blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "enderpearl -", TextColors.WHITE, " Sets if non-members are allowed to use enderpearls in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "enderdragonblockdamage -", TextColors.WHITE, " Sets if the enderdragon will break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "sleep -", TextColors.WHITE, " Sets if non-members are allowed to sleep in the region");
			}
			if(page == 3) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(3/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "tnt -", TextColors.WHITE, " Sets if tnt explosion will break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "lighter -", TextColors.WHITE, " Sets if non-members are allowed to use lighter in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "chests -", TextColors.WHITE, " Sets if non-members are allowed to open chests/trapped chests in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "waterflow -", TextColors.WHITE, " Sets if water can flow in the region. This also enable/disable water buckets at all");
				Utils.sendMessage(player, TextColors.YELLOW, "lavaflow -", TextColors.WHITE, " Sets if lava can flow in the region. This also enable/disable lava buckets at all");
			}
			if(page == 4) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(4/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "use -", TextColors.WHITE, " Sets if non-members are allowed to use containers in the region. This will not consider crafting tables, anvils and enchanting tables");
				Utils.sendMessage(player, TextColors.YELLOW, "vehicleplace -", TextColors.WHITE, " Sets if non-members are allowed to place vehicles in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "vehicledestroy -", TextColors.WHITE, " Sets if non-members are allowed to destroy vehicles in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "leafdecay -", TextColors.WHITE, " Sets if leaves will decay in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "sendchat -", TextColors.WHITE, " Sets if non-members are allowed to send chat messages while in the region");
			}
			if(page == 5) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(5/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "potionsplash -", TextColors.WHITE, " Sets if non-members are allowed to thrown potions in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "falldamage -", TextColors.WHITE, " Sets if players can take fall damage in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "walldamage -", TextColors.WHITE, " Sets if players can take suffocate in walls in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "drown -", TextColors.WHITE, " Sets if players can drown in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "cantp -", TextColors.WHITE, " Sets if players can use the /region teleport command while in the region");
			}
			if(page == 6) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(6/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "canspawn -", TextColors.WHITE, " Sets if players can use the /region spawn command while in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "hunger -", TextColors.WHITE, " Sets if players will loose hunger points while in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "enderchests -", TextColors.WHITE, " Sets if non-members can open enderchests in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "animals -", TextColors.WHITE, " Sets if non-hostile mobs can spawn in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "mobs -", TextColors.WHITE, " Sets if hostile mobs can spawn in the region");
			}
			if(page == 7) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(7/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "tntdamage -", TextColors.WHITE, " Sets if players will get damage from tnt explosions");
				Utils.sendMessage(player, TextColors.YELLOW, "otherexplosionsdamage -", TextColors.WHITE, " Sets if players will get damage from other explosions");
				Utils.sendMessage(player, TextColors.YELLOW, "invincible -", TextColors.WHITE, " Sets if players are invincible in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "cactusdamage -", TextColors.WHITE, " Sets if player will receive damage from cactus in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "firedamage -", TextColors.WHITE, " Sets if player will receive damage from fire/lava in the region");
			}
			if(page >= 8) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(8/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "mobpvp -", TextColors.WHITE, " Sets if players can damage mobs in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "animalspvp -", TextColors.WHITE, " Sets if players can damage animals in the region");
				Utils.sendMessage(player, TextColors.GOLD, "--------------------------------------------");
			}

			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
