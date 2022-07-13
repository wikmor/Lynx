package me.wikmor.lynx.command.channel;

import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import me.wikmor.lynx.ChannelManager;

public class ChannelLeaveCommand extends SimpleSubCommand {

	protected ChannelLeaveCommand(SimpleCommandGroup parent) {
		super(parent, "leave|l");

		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		String name = args[0];
		checkBoolean(ChannelManager.isJoined(getPlayer(), name), "You are not joined in {0} channel.");

		ChannelManager.leave(getPlayer(), name);
		tellWarn("You have left {0} channel.");
	}
}
