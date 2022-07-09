package me.wikmor.lynx;

import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.Remain;

import me.wikmor.lynx.api.CowExplodeEvent;

public final class Lynx extends SimplePlugin {

	@Override
	protected void onPluginStart() {
		// registerEvents(PlayerListener.getInstance()); handled using @AutoRegister
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChatEarly(AsyncPlayerChatEvent event) {
		System.out.println("1 Message: " + event.getMessage());
		System.out.println("Is this event run on the primary thread (sync)? " + Bukkit.isPrimaryThread());
		// don't use event.isAsynchronous() - plugins can 'lie'

		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true) /*(priority = EventPriority.NORMAL) by default*/
	public void onChat(AsyncPlayerChatEvent event) {
		System.out.println("2 Message: " + event.getMessage() + " is cancelled? " + event.isCancelled());
	}

	@EventHandler(priority = EventPriority.MONITOR/*, ignoreCancelled = true*/)
	public void onChatLate(AsyncPlayerChatEvent event) {
		if (!event.isCancelled())
			System.out.println("3 Message: " + event.getMessage() + " is cancelled? " + event.isCancelled());
	}

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent event) {

		if (!Remain.isInteractEventPrimaryHand(event))
			return;

		Entity entity = event.getRightClicked();

		System.out.println("Entity clicked!");

		if (entity instanceof Cow) {
			CowExplodeEvent cowEvent = new CowExplodeEvent(entity);

			if (Common.callEvent(cowEvent))
				entity.getWorld().createExplosion(entity.getLocation(), cowEvent.getPower());
		}
	}
}
