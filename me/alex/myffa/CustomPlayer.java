package me.alex.myffa;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.alex.myffa.commands.CommandKit;

public class CustomPlayer {

    private Main main = Main.getInstance();
    private Player player;
    CommandKit strings[];
    CommandKit kitsList;

    public CustomPlayer(String name) {
        player = Bukkit.getPlayerExact(name);
    }

    public CustomPlayer(CommandSender commandSender) {
        player = (Player) commandSender;
    }

    public void sendMessage(String message, boolean prefix) {
        if (prefix) {
            player.sendMessage(main.format(main.getConfig().getString("prefix-chat") + " " + message));
        } else {
            player.sendMessage(main.format(message));
        }
    }

    public boolean isPlayer(CommandSender commandSender) {
        return commandSender instanceof Player;
    }

    public void sendMessage(String message) {
        player.sendMessage(main.format(message));
    }

    @SuppressWarnings("deprecation")
	public void setArmor(String nameKit, int id, String armorType) {
        ItemStack itemStack = new ItemStack(Material.getMaterial(id), 1);

        for (String enchants : main.getConfig().getStringList("kits."+nameKit+"."+armorType+".enchant")) {
            if (enchants != null) {
                itemStack.addUnsafeEnchantment(Enchantment.getByName(enchants.split(":")[0]), Integer.parseInt(enchants.split(":")[1]));
            }
        }

        switch (armorType) {
            case "helmet":
                player.getInventory().setHelmet(itemStack);
                break;
            case "chestplate":
                player.getInventory().setChestplate(itemStack);
                break;
            case "leggings":
                player.getInventory().setLeggings(itemStack);
                break;
            case "boots":
                player.getInventory().setBoots(itemStack);
                break;
        }
    }

    public Inventory getInventory() {
        return player.getInventory();
    }

	public boolean hasKitPermission(String permission) {
		return player.hasPermission(permission);
	}
}
