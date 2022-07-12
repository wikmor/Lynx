package me.wikmor.lynx.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister // always registers
public final class SpawnEntityCommand extends SimpleCommand {

	public SpawnEntityCommand() {
		super("spawnentity|spawnmob|spawne");

		setDescription("Spawn a mob at the given location.");
		// this is set automatically
		// setPermission("lynx.command.spawnentity");
		// setUsage("One line usage");
		setMinArguments(2);
	}

	@Override
	protected String[] getMultilineUsageMessage() {
		List<String> usage = new ArrayList<>();

		if (hasPerm("lynx.command.spawnentity.player"))
			usage.add("/" + getLabel() + " <mobType> <playerName> - Spawn a mob at the player's location.");

		if (hasPerm("lynx.command.spawnentity.location"))
			usage.add("/" + getLabel() + " <mobType> <world> <x> <y> <z> - Spawns a mob at the given location.");

		return /*usage.toArray(new String[usage.size()])*/ Common.toArray(usage);
		/*return new String[] {
				"/<command> <mobType> <playerName> - Spawn a mob at the player's location.",
				"/<command> <mobType> <world> <x> <y> <z> - Spawns a mob at the given location."
		};*/
	}

	@Override
	protected void onCommand() {
		EntityType entityType = findEnum(EntityType.class, args[0], "No such entity by name '{enum}'.");
		checkBoolean(entityType.isAlive() && entityType.isSpawnable(), "Entity " + entityType.getName() + " is not alive or spawnable!");

		if (args.length == 5) {
			String worldName = args[1];
			World world = Bukkit.getWorld(worldName);
			int x = findNumber(2, "The X position must be a whole number! You typed: '{2}'.");
			int y = findNumber(3, "The Y position must be a whole number! You typed: '{3}'.");
			int z = findNumber(4, "The Z position must be a whole number! You typed: '{4}'.");

			checkNotNull(world, "Invalid world! You typed: " + worldName);

			world.spawnEntity(new Location(world, x, y, z), entityType);

			//returnTell? sender.sendMessage(ChatColor.GREEN + "Spawned " + entityType.toString().toLowerCase() + " at " + worldName + " " + x + " " + y + " " + z);
		}

		else if (args.length == 2) {
			checkConsole();

			Player player = getPlayer();
			Player targetPlayer = findPlayer(args[1]);

			targetPlayer.getWorld().spawnEntity(targetPlayer.getLocation(), entityType);

			//player.sendMessage(ChatColor.GREEN + "Spawned " + entityType.toString().toLowerCase() + " at " + targetPlayer.getName() + "'s location.");
		}
	}

	@Override
	protected List<String> tabComplete() {
		switch (args.length) {
			case 1:
				return completeLastWord(Arrays
						.stream(EntityType.values())
						.filter(type -> type.isAlive() && type.isSpawnable())
						.collect(Collectors.toList()));

			case 2:
				/*if (isPlayer()) {
					for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						String name = onlinePlayer.getName();

						if (name.startsWith(args[1]))
							completions.add(name);
					}
				}

				for (World world : Bukkit.getWorlds()) {
					String worldName = world.getName();

					if (worldName.startsWith(args[1]))
						completions.add(worldName);
				}

				break;*/
				return completeLastWord(isPlayer() ? Common.getPlayerNames() : NO_COMPLETE, Common.getWorldNames());

			case 3:
				/*checkConsole();
				
				completions.add(String.valueOf(getPlayer().getLocation().getBlockX()));
				break;*/
				return completeLastWord(isPlayer() ? getPlayer().getLocation().getBlockX() : NO_COMPLETE);

			case 4:
				/*checkConsole();
				
				completions.add(String.valueOf(getPlayer().getLocation().getBlockY()));
				break;*/
				return completeLastWord(isPlayer() ? getPlayer().getLocation().getBlockY() : NO_COMPLETE);

			case 5:
				/*checkConsole();
				
				completions.add(String.valueOf(getPlayer().getLocation().getBlockZ()));
				break;*/
				return completeLastWord(isPlayer() ? getPlayer().getLocation().getBlockZ() : NO_COMPLETE);
		}

		return NO_COMPLETE;
	}
}
