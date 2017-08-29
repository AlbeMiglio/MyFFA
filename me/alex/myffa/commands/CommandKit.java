package me.alex.myffa.commands;

import me.alex.myffa.CustomPlayer;
import me.alex.myffa.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CommandKit implements CommandExecutor {

    private Main main = Main.getInstance();

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> kitsList = new ArrayList<>();
        CustomPlayer player = new CustomPlayer(commandSender);
        if ((!player.hasKitPermission("myffa.kit" + strings[0]) || (!player.hasKitPermission("myffa.kit.*")))) {
        	player.sendMessage(getConfig().getString("not-enough-permissions"), true);
        	return false;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("&cOnly players can execute this command");
            return false;
        }
        
        int i = 0;
        for (String kits : main.getConfig().getConfigurationSection("kits").getKeys(false)) {
            i++;
            kitsList.add(kits);
        }

        if (!(strings.length > 0)) {
            commandSender.sendMessage(main.getConfig().getString("kit-message").replaceAll("%number%", String.valueOf(i)));
            commandSender.sendMessage(kitsList.toString());
            return false;
        }

        if (kitsList.contains(strings[0])) {
            FileConfiguration fileConfiguration = main.getConfig();

            player.setArmor(strings[0], fileConfiguration.getInt("kits."+strings[0]+".helmet.id"), "helmet");
            player.setArmor(strings[0], fileConfiguration.getInt("kits."+strings[0]+".chestplate.id"), "chestplate");
            player.setArmor(strings[0], fileConfiguration.getInt("kits."+strings[0]+".leggings.id"), "leggings");
            player.setArmor(strings[0], fileConfiguration.getInt("kits."+strings[0]+".boots.id"), "boots");


            for (int x=1; x<main.getConfig().getConfigurationSection("kits."+strings[0]+".items").getKeys(false).size()+1; x++) {
            	  
                  ItemStack itemStack = new ItemStack(Material.getMaterial(fileConfiguration.getInt("kits."+strings[0]+".items."+x+".id")),
                          fileConfiguration.getInt("kits."+strings[0]+".items."+x+".amount"), (byte) fileConfiguration.getInt("kits."+strings[0]+".items."+x+".data"));

                  for (String enchants : main.getConfig().getStringList("kits."+strings[0]+".items."+x+".enchant")) {
                      if (enchants != null) {
                          itemStack.addUnsafeEnchantment(Enchantment.getByName(enchants.split(":")[0]), Integer.parseInt(enchants.split(":")[1]));
                      }
                    }
                

                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(main.format(fileConfiguration.getString("kits."+strings[0]+".items."+x+".name")));
                itemMeta.setLore(fileConfiguration.getStringList("kits."+strings[0]+".items."+x+".lore"));

                itemStack.setItemMeta(itemMeta);
                player.getInventory().clear();
                player.getInventory().setItem(x-1, itemStack);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("kit-selected").replaceAll("%kit%", strings[0])));
            	}
            }
            return true;
    }

	private FileConfiguration getConfig() {
		return main.getConfig();
	}
}
