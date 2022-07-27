package me.wikmor.lynx.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.mineacademy.fo.settings.YamlConfig;

import lombok.Getter;

@Getter
public final class PlayerData extends YamlConfig {

	private static Map<UUID, PlayerData> playerData = new HashMap<>();

	private final String playerName;
	private final UUID uuid;

	private double health;
	private String tabListName;

	private PlayerData(String playerName, UUID uuid) {
		this.playerName = playerName;
		this.uuid = uuid;

		this.setHeader("My\nHeader");
		this.loadConfiguration(NO_DEFAULT, "players/" + uuid + ".yml");
		// this.save(); if you want to save the default values for each player on the plugin load
	}

	@Override
	protected void onLoad() {
		this.health = this.getDouble("health", 20D);
		this.tabListName = this.getString("tablist-name", this.playerName);
	}

	@Override
	protected void onSave() {
		this.set("health", this.health);
		this.set("tablist-name", this.tabListName);
	}

	public void setHealth(double health) {
		this.health = health;

		this.save();
	}

	public void setTabListName(String tabListName) {
		this.tabListName = tabListName;

		this.save();
	}

	public static PlayerData from(Player player) {
		UUID uuid = player.getUniqueId();
		PlayerData data = playerData.get(uuid);

		if (data == null) {
			data = new PlayerData(player.getName(), uuid);

			playerData.put(uuid, data);
		}

		return data;
	}

	public static void remove(Player player) {
		playerData.remove(player.getUniqueId());
	}
}