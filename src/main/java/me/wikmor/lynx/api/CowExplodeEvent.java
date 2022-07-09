package me.wikmor.lynx.api;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.mineacademy.fo.event.SimpleEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CowExplodeEvent extends SimpleEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final Entity cow;
	private float power = 5;
	private boolean cancelled;

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
