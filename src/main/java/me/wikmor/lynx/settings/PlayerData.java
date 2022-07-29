package me.wikmor.lynx.settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.settings.YamlConfig;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
public final class PlayerData extends YamlConfig {

	private static Map<UUID, PlayerData> playerData = new HashMap<>();

	private final String playerName;
	private final UUID uuid;

	private double health;
	private String tabListName;

	private Kit kit;

	@Getter
	@ToString
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Kit implements ConfigSerializable {

		private String name;
		private List<ItemStack> items;

		@Override
		public SerializedMap serialize() {
			SerializedMap map = new SerializedMap();

			map.put("Name", this.name);
			map.put("Items", this.items);

			return map;
		}

		public static Kit deserialize(SerializedMap map/*, Player player*/) {
			Kit kit = new Kit();

			kit.name = map.getString("Name");
			kit.items = map.getList("Items", ItemStack.class);

			return kit;
		}
	}

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
		this.kit = this.get("Kit", Kit.class/*, player*/);
	}

	@Override
	protected void onSave() {
		this.set("health", this.health);
		this.set("tablist-name", this.tabListName);
		this.set("Kit", this.kit);
	}

	public void setHealth(double health) {
		this.health = health;

		this.save();
	}

	public void setTabListName(String tabListName) {
		this.tabListName = tabListName;

		this.save();
	}

	public void setKit(String kitName, List<ItemStack> items) {
		this.kit = new Kit(kitName, items);

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