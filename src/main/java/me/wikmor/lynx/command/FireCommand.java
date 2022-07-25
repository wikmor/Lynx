package me.wikmor.lynx.command;

import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.TimeUtil;
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

		Common.runLater((int) TimeUtil.toTicks("5 seconds"), () -> {
			target.setFireTicks(20 * 2);
			tellSuccess("{0} has been set on fire");
		});

		/*Common.runTimerAsync(10, new BukkitRunnable() {

			@Override
			public void run() {
				if (target.isDead()) {
					cancel();

					return;
				}

				target.setFireTicks(20 * 2);
			}
		});*/
	}
}
