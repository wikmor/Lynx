package me.wikmor.lynx.command;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.model.SimpleComponent;
import org.mineacademy.fo.remain.Remain;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@AutoRegister
public final class ComponentCommand extends SimpleCommand {

	public ComponentCommand() {
		super("component");

		setUsage("<native/fo>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		checkConsole();

		String type = args[0].toLowerCase();

		if ("native".equals(type)) {
			TextComponent first = new TextComponent(TextComponent.fromLegacyText(Common.colorize("&cHello world&7, I hold ")));

			first.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/wikmor"));
			first.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Click this text\nto visit our webiste")));

			ItemStack item = getPlayer().getItemInHand();
			boolean air = item.getType() == Material.AIR;

			TextComponent second = new TextComponent(TextComponent.fromLegacyText(Common.colorize(air ? "Air" : ItemUtil.bountifyCapitalized(item.getType()))));
			second.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[] {
					new TextComponent(Remain.toJson(item))
			}));

			TextComponent main = new TextComponent("");

			main.addExtra(first);
			main.addExtra(second);

			getPlayer().spigot().sendMessage(main);
		}

		else if ("fo".equals(type)) {

			ItemStack item = getPlayer().getItemInHand();
			boolean air = item.getType() == Material.AIR;

			SimpleComponent
					.of("&cHello world&7, I hold ")
					.onClickRunCmd("/me is the best :)")
					.onHover("Click it to run a secret command")

					.append(air ? "Air" : ItemUtil.bountifyCapitalized(item.getType()))
					.onHover(item)

					.send(getPlayer());
		}
	}
}
