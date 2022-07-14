package me.wikmor.lynx.command;

import org.bukkit.permissions.PermissionAttachment;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import me.wikmor.lynx.Lynx;
import me.wikmor.lynx.PermissionManager;

@AutoRegister
public final class PermsCommand extends SimpleCommand {

	public PermsCommand() {
		super("permtest");

		setDescription("Test your own permission system");
		setUsage("<add/remove/check>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		checkConsole(); // console has all permissions :)

		String param = args[0].toLowerCase();

		if ("add".equals(param)) {
			checkBoolean(!PermissionManager.hasPermissionSet(getPlayer()), "You have already set your permission");
			PermissionAttachment attachment = getPlayer().addAttachment(Lynx.getInstance(), "lynx.command.fire", true);

			PermissionManager.store(getPlayer(), attachment);
			tellSuccess("Your permission was granted!");
		}

		else if ("remove".equals(param)) {
			//checkBoolean(PermissionManager.hasPermissionSet(getPlayer()), "You do not have set your permission");
			PermissionAttachment attachment = PermissionManager.getPermission(getPlayer());
			//checkBoolean(attachment != null, "You do not have set your permission");
			checkNotNull(attachment, "You do not have set your permission");

			getPlayer().removeAttachment(attachment);
			PermissionManager.remove(getPlayer());

			tellSuccess("Your permission was removed!");
		}

		else if ("check".equals(param))
			tellInfo(PermissionManager.hasPermissionSet(getPlayer()) ? " You have a permission set" : "You do not have permission set");

		else
			returnInvalidArgs();
	}
}
