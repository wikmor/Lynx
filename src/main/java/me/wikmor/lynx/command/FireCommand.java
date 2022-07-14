package me.wikmor.lynx.command;

import org.bukkit.entity.Player;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class FireCommand extends SimpleCommand {

	public FireCommand() {
		super("fire|setfire");

		setDescription("Set a player on fire");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		Player target = findPlayer(args[0]);
		checkBoolean(!hasPerm(target, "lynx.bypass.fire"), "You cannot set that player on fire!");

		target.setFireTicks(20 * 2);
		tellSuccess("{0} has been set on fire");
	}
}
