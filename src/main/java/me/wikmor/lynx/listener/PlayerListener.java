package me.wikmor.lynx.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mineacademy.fo.annotation.AutoRegister;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerListener implements Listener {

	@Getter
	private static final PlayerListener instance = new PlayerListener();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		System.out.println("Joined player: " + event.getPlayer().getName());
	}
}
