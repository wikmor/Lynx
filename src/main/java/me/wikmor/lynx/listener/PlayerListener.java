package me.wikmor.lynx.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.debug.LagCatcher;
import org.mineacademy.fo.settings.Lang;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.wikmor.lynx.settings.PlayerData;

@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerListener implements Listener {

	@Getter
	private static final PlayerListener instance = new PlayerListener();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		LagCatcher.start("PlayerJoinEvent full event");

		Player player = event.getPlayer();
		PlayerData data = PlayerData.from(player);

		String tabListName = data.getTabListName();

		LagCatcher.start("Saving tab name");
		LagCatcher.performanceTest(1000, "saving player data", () -> {
			player.setPlayerListName(tabListName);
		});
		LagCatcher.end("Saving tab name");

		System.out.println("Player " + player.getName() + " has kit: " + data.getKit());
		System.out.println("Custom locale key: " + Lang.of("Custom_Section.Boss"));

		LagCatcher.end("PlayerJoinEvent full event", true);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {

		// Save memory by removing the player data -- this won't remove data
		// from your disk, only removes the loaded instance in your plugin!~
		// (Next time you call PlayerData#from, it will get loaded back again)
		PlayerData.remove(event.getPlayer());
	}
}
