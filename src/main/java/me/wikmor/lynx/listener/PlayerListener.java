package me.wikmor.lynx.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.debug.LagCatcher;
import org.mineacademy.fo.remain.CompMetadata;
import org.mineacademy.fo.remain.Remain;
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

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (!Remain.isInteractEventPrimaryHand(event))
			return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		ItemStack hand = event.getItem();

		String bossName = hand != null ? CompMetadata.getMetadata(hand, "CustomBoss") : null;

		if (bossName != null && block != null) {
			EntityType entity = EntityType.valueOf(bossName);
			Location location = block.getLocation().add(0.5, 5, 0.5);

			player.getWorld().spawnEntity(location, entity);

			if (player.getGameMode() != GameMode.CREATIVE)
				PlayerUtil.takeOnePiece(player, hand);

			event.setCancelled(true); // to not spawn an Enderman
		}
	}

	@EventHandler
	public void onFallDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (!(entity instanceof Player))
			return;

		Player player = (Player) entity;

		if (event.getCause() == DamageCause.FALL && player.hasPotionEffect(PotionEffectType.JUMP))
			event.setCancelled(true);
	}
}
