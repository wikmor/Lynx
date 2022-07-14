package me.wikmor.lynx;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public final class PermissionManager {

	// stores only one permission per player for demo purposes, you can make List<PermissionAttachment> to attach more than one permission to the player
	private static final Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

	public static void store(Player player, PermissionAttachment attachment) {
		playerPermissions.put(player.getUniqueId(), attachment);
	}

	public static void remove(Player player) {
		playerPermissions.remove(player.getUniqueId());
	}

	public static boolean hasPermissionSet(Player player) {
		return getPermission(player) != null;
	}

	public static PermissionAttachment getPermission(Player player) {
		return playerPermissions.get(player.getUniqueId());
	}
}
