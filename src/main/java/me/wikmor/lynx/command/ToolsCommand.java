package me.wikmor.lynx.command;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.menu.MenuTools;

import me.wikmor.lynx.item.KittyCannon;

@AutoRegister
public final class ToolsCommand extends SimpleCommand {

	public ToolsCommand() {
		super("tools");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		new MenuTools() {
			@Override
			protected Object[] compileTools() {
				return new Object[] {
						KittyCannon.class
				};
			}
		}.displayTo(getPlayer());
	}
}
