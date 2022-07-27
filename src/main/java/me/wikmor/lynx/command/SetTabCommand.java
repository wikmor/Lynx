package me.wikmor.lynx.command;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import me.wikmor.lynx.settings.PlayerData;

@AutoRegister
public final class SetTabCommand extends SimpleCommand {

	public SetTabCommand() {
		super("settab");

		setUsage("<alias>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		String tabName = args[0];

		getPlayer().setPlayerListName(tabName);
		PlayerData.from(getPlayer()).setTabListName(tabName);

		tellSuccess("Your name has been updated to '" + tabName + "'.");
	}
}
