package me.wikmor.lynx.command;

import java.util.List;

import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.model.Replacer;
import org.mineacademy.fo.model.SimpleScoreboard;
import org.mineacademy.fo.remain.CompBarColor;
import org.mineacademy.fo.remain.CompBarStyle;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompToastStyle;
import org.mineacademy.fo.remain.Remain;
import org.mineacademy.fo.slider.ColoredTextSlider;

@AutoRegister
public final class AnimateCommand extends SimpleCommand {

	public AnimateCommand() {
		super("animated");

		setUsage("<action/title/tab/boss/toast/scoreboard>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		checkConsole();

		Player player = getPlayer();
		String type = args[0].toLowerCase();

		if ("action".equals(type)) {
			Remain.sendActionBar(player, "&7[ &dWelcome to the server! &7]");

			Common.runLater(2 * 20, () -> Remain.sendActionBar(player, "&7[ &fType /help for more info! &7]"));
		}

		else if ("title".equals(type))
			Remain.sendTitle(player, 10, 2 * 20, 2 * 20, "&6Welcome", "&7Visit our website");

		else if ("tab".equals(type))
			Remain.sendTablist(player, "[MineAcademy]\nOnline players &c" + Remain.getOnlinePlayers().size(), "\nReceive reward at...");

		else if ("boss".equals(type))
			Remain.sendBossbarTimed(player, Common.colorize("&cThis is a special announcement!"), 6, CompBarColor.RED, CompBarStyle.SEGMENTED_6);

		else if ("toast".equals(type))
			Remain.sendToast(player, "Hey " + player.getName() + "\nYou've got a new mail!", CompMaterial.SUNFLOWER, CompToastStyle.TASK);

		else if ("scoreboard".equals(type)) {

			ColoredTextSlider slider = ColoredTextSlider
					.from("Information Board")
					.primaryColor("&6")
					.secondaryColor("&c")
					.width(4);

			SimpleScoreboard board = new SimpleScoreboard() {

				@Override
				protected String replaceVariables(Player player, String message) {
					return Replacer.replaceArray(message,
							"online", Remain.getOnlinePlayers().size(),
							"health", MathUtil.formatTwoDigits(player.getHealth()),
							"ping", PlayerUtil.getPing(player));
				}

				@Override
				protected void onUpdate() {
					this.setTitle(slider.next());
				}
			};

			board.setUpdateDelayTicks(1);
			board.setTitle("&6Info&7Board");

			board.addRows("",
					"&cOnline players:",
					"&7{online}",
					"",
					"&cHealth:",
					"&7{health}",
					"",
					"&cPing:",
					"&7{ping}");

			board.show(player);
		}
	}

	@Override
	protected List<String> tabComplete() {
		return completeLastWord("action", "title", "tab", "boss", "toast", "scoreboard");
	}
}
