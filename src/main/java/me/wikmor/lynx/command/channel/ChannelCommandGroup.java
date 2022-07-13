package me.wikmor.lynx.command.channel;

import java.util.ArrayList;
import java.util.List;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.SimpleComponent;

@AutoRegister
public final class ChannelCommandGroup extends SimpleCommandGroup {

	public ChannelCommandGroup() {
		super("channel|ch");
	}

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new ChannelJoinCommand(this));
		registerSubcommand(new ChannelLeaveCommand(this));
	}

	@Override
	protected String getCredits() {
		return "Test information";
	}

	@Override
	protected List<SimpleComponent> getNoParamsHeader() {
		List<SimpleComponent> infos = new ArrayList<>();

		infos.add(SimpleComponent.of("Welcome to the channel command. Type /channel ? for more info."));
		infos.add(SimpleComponent.of("Plugin made by wikmor."));

		return infos;
		// or return Arrays.asList(
		// SimpleComponent.of("Welcome to the channel command. Type /channel ? for more info."),
		// SimpleComponent.of("Plugin made by wikmor."))
	}

	@Override
	protected String[] getHelpHeader() {
		return new String[] {
				"&6The help for your channel command:"
		};
	}
}
