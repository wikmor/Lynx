package me.wikmor.lynx.command;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompColor;
import org.mineacademy.fo.remain.CompMaterial;

@AutoRegister
public final class CustomItemsCommand extends SimpleCommand {

	public static final ItemStack POTION = ItemCreator.ofPotion(PotionEffectType.JUMP, 5 * 60 * 20, 30,
			"Kangaroo",
			"",
			"Drink this to",
			"jump into infinite",
			"height (careful!)").make();

	public CustomItemsCommand() {
		super("customitems");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		/*ItemStack sunFlower = new ItemStack(CompMaterial.SUNFLOWER.getMaterial());
		ItemMeta meta = sunFlower.getItemMeta();
		
		meta.setDisplayName("Deadly Sunflower");
		meta.setLore(Arrays.asList("", "Click an entity", "to deal massive damage."));
		
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		sunFlower.setItemMeta(meta);
		sunFlower.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
		
		getPlayer().getInventory().addItem(sunFlower);*/

		ItemCreator.of(CompMaterial.SUNFLOWER,
				"&cDeadly Sunflower",
				"",
				"Click an entity to",
				"deal massive damage.")
				.hideTags(true)
				.enchant(Enchantment.DAMAGE_ALL, 10)
				.give(getPlayer());

		ItemCreator.of(CompMaterial.LEATHER_CHESTPLATE,
				"Lynxplate",
				"",
				"Wear this to be a",
				"part of the blue team.")
				.color(CompColor.BLUE)
				.give(getPlayer());

		getPlayer().getInventory().addItem(POTION);

		ItemCreator.of(CompMaterial.PLAYER_HEAD,
				"Creator of Minecraft")
				.skullOwner("Notch")
				.give(getPlayer());

		ItemCreator.of(CompMaterial.PLAYER_HEAD,
				"&bDiamond Ore")
				.skullUrl("https://textures.minecraft.net/texture/733b6c907f1c2a1ae54f90aafbc9e561f2f4dd4ec4b73e56d54955bc1dfcc2a0")
				.give(getPlayer());

		ItemCreator.ofEgg(EntityType.ENDERMAN,
				"Spawn Dragon",
				"",
				"Click any ground block",
				"to summon a dragon!")
				.tag("CustomBoss", EntityType.ENDER_DRAGON.toString())
				.give(getPlayer());
	}
}
