package me.wikmor.lynx.command;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import me.wikmor.lynx.settings.PlayerData;

@AutoRegister
public final class SetKitCommand extends SimpleCommand {

	public SetKitCommand() {
		super("setkit");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		PlayerData.from(getPlayer()).setKit("Archer", Arrays.asList(new ItemStack(Material.ARROW), new ItemStack(Material.BOW)));

		tellSuccess("Kit given!");
	}
}
