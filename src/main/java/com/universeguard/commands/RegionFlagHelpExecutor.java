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
				Utils.sendMessage(player, TextColors.YELLOW, "build -", TextColors.RESET, "Sets if non-members can place/break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "pvp -", TextColors.RESET, "Sets if players can pvp in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "mobdamage -", TextColors.RESET, "Sets if mobs can deal damage to players in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "expdrop -", TextColors.RESET, "Sets if mobs can drop experience in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "creeperexplosions -", TextColors.RESET, "Sets if creepers will break blocks in the region when they explode");	
			}
			if(page == 2) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(2/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "otherexplosions -", TextColors.RESET, "Sets if explosions will break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "endermangrief -", TextColors.RESET, "Sets if enderman are allowed to break/place blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "enderpearl -", TextColors.RESET, "Sets if non-members are allowed to use enderpearls in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "enderdragonblockdamage -", TextColors.RESET, "Sets if the enderdragon will break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "sleep -", TextColors.RESET, "Sets if non-members are allowed to sleep in the region");
			}
			if(page == 3) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(3/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "tnt -", TextColors.RESET, "Sets if tnt explosion will break blocks in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "lighter -", TextColors.RESET, "Sets if non-members are allowed to use lighter in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "chests -", TextColors.RESET, "Sets if non-members are allowed to open chests/trapped chests in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "waterflow -", TextColors.RESET, "Sets if water can flow in the region. This also enable/disable water buckets at all");
				Utils.sendMessage(player, TextColors.YELLOW, "lavaflow -", TextColors.RESET, "Sets if lava can flow in the region. This also enable/disable lava buckets at all");
			}
			if(page == 4) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(4/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "use -", TextColors.RESET, "Sets if non-members are allowed to use containers in the region. This will not consider crafting tables, anvils and enchanting tables");
				Utils.sendMessage(player, TextColors.YELLOW, "vehicleplace -", TextColors.RESET, "Sets if non-members are allowed to place vehicles in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "vehicledestroy -", TextColors.RESET, "Sets if non-members are allowed to destroy vehicles in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "leafdecay -", TextColors.RESET, "Sets if leaves will decay in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "sendchat -", TextColors.RESET, "Sets if non-members are allowed to send chat messages while in the region");
			}
			if(page == 5) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(5/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "potionsplash -", TextColors.RESET, "Sets if non-members are allowed to thrown potions in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "falldamage -", TextColors.RESET, "Sets if players can take fall damage in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "walldamage -", TextColors.RESET, "Sets if players can take suffocate in walls in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "drown -", TextColors.RESET, "Sets if players can drown in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "cantp -", TextColors.RESET, "Sets if players can use the /region teleport command while in the region");
			}
			if(page == 6) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(6/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "canspawn -", TextColors.RESET, "Sets if players can use the /region spawn command while in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "hunger -", TextColors.RESET, "Sets if players will loose hunger points while in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "enderchests -", TextColors.RESET, "Sets if non-members can open enderchests in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "animals -", TextColors.RESET, "Sets if non-hostile mobs can spawn in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "mobs -", TextColors.RESET, "Sets if hostile mobs can spawn in the region");
			}
			if(page == 7) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(7/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "tntdamage -", TextColors.RESET, "Sets if players will get damage from tnt explosions");
				Utils.sendMessage(player, TextColors.YELLOW, "otherexplosionsdamage -", TextColors.RESET, "Sets if players will get damage from other explosions");
				Utils.sendMessage(player, TextColors.YELLOW, "invincible -", TextColors.RESET, "Sets if players are invincible in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "cactusdamage -", TextColors.RESET, "Sets if player will receive damage from cactus in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "firedamage -", TextColors.RESET, "Sets if player will receive damage from fire/lava in the region");
			}
			if(page >= 8) {
				Utils.sendMessage(player, TextColors.GOLD, "------------------Flags Help(8/8)------------------");
				Utils.sendMessage(player, TextColors.YELLOW, "mobpvp -", TextColors.RESET, "Sets if players can damage mobs in the region");
				Utils.sendMessage(player, TextColors.YELLOW, "animalspvp -", TextColors.RESET, "Sets if players can damage animals in the region");
				Utils.sendMessage(player, TextColors.GOLD, "--------------------------------------------");
			}

			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
