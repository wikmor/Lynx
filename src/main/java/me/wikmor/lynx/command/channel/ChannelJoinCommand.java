package me.wikmor.lynx.command.channel;

import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import me.wikmor.lynx.ChannelManager;

public class ChannelJoinCommand extends SimpleSubCommand {

	protected ChannelJoinCommand(SimpleCommandGroup parent) {
		super(parent, "join|j");

		setDescription("Join a channel");
		setUsage("<channel>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		String name = args[0];
		checkBoolean(!ChannelManager.isJoined(getPlayer(), name), "You are already joined in {0} channel.");

		ChannelManager.join(getPlayer(), name);
		tellSuccess("You have joined {0} channel.");
	}
}
