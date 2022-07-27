package me.wikmor.lynx.settings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.mineacademy.fo.settings.YamlStaticConfig;

import lombok.SneakyThrows;

public final class Settings extends YamlStaticConfig {

	@Override
	protected void onLoad() {
		this.loadConfiguration("settings.yml");

		printFields(Settings.class);
	}

	/*@Override
	protected boolean saveComments() {
		return false;
	}*/

	@Override
	protected List<String> getUncommentedSections() {
		return Arrays.asList("Parts");
	}

	public static Integer PLAYER_HEALTH;
	public static String PLAYER_NAME;
	public static List<String> PLAYER_ACHIEVEMENTS;
	public static String[] MULTILINE_MESSAGE;

	public static final class Boss {
		public static Double DAMAGE_MULTIPLIER;
		public static String ALIAS;

		private static void init() {
			setPathPrefix("Boss");

			DAMAGE_MULTIPLIER = getDouble("Damage_Multiplier");
			ALIAS = getString("Alias");
		}
	}

	private static void init() {
		setPathPrefix(null);

		PLAYER_HEALTH = getInteger("Player_Health", 50);
		PLAYER_NAME = getString("Player_Name");
		PLAYER_ACHIEVEMENTS = getStringList("Player_Achievements");
		MULTILINE_MESSAGE = getString("Multiline_Message").split("\n");
	}

	@SneakyThrows
	private static void printFields(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields())
			if (Modifier.isStatic(field.getModifiers()))
				System.out.println(clazz.getSimpleName() + "." + field.getName() + " --> " + field.get(null));

		for (Class<?> superClazz : clazz.getDeclaredClasses())
			printFields(superClazz);
	}
}
