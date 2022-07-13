package me.wikmor.lynx;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public final class ChannelManager {

	private static final Map<UUID, String> playerChannels = new HashMap<>();

	public static void join(Player player, String channel) {
		playerChannels.put(player.getUniqueId(), channel);
	}

	public static void leave(Player player, String channel) {
		playerChannels.remove(player.getUniqueId(), channel);
	}

	public static boolean isJoined(Player player, String channelName) {
		String channel = getChannel(player);

		return channel != null && channel.equals(channelName);
	}

	public static String getChannel(Player player) {
		return playerChannels.get(player.getUniqueId());
	}
}
