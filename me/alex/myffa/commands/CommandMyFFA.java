package me.alex.myffa.commands;

import me.alex.myffa.CustomPlayer;
import me.alex.myffa.FileManager;
import me.alex.myffa.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandMyFFA implements CommandExecutor {

    private CustomPlayer player;
    private Main main = Main.getInstance();
    private FileConfiguration getConfig() {
    	return main.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        player = new CustomPlayer(commandSender);
        FileManager location = new FileManager("location");
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("&cOnly players can execute this command");
            return false;
        }
        if (!(strings.length > 0)) {
            usageCommand();
            return false;
        }

        switch (strings[0]) {
            case "spawn":
             if (!getConfig().getBoolean("setspawn-enabled")) {
            	 usageCommand();
            	 return false;
             }
             if (location.getConfigurationSection("Spawn") == null) {
            	player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("spawn-not-set")));
             }
             if (player.hasKitPermission("myffa.admin") || (player.hasKitPermission("myffa.spawn"))) {
            	 Location Spawn = new Location(Bukkit.getWorld(location.getString("spawn.world")), location.getDouble("spawn.x"), location.getDouble("spawn.y"), location.getDouble("spawn.z"));
            	((Player) commandSender).teleport(Spawn);
             }
            	 break;
            case "setspawn":
              if (!getConfig().getBoolean("setspawn-enabled")) {
            	player.sendMessage("&cYou have to enable &4setspawn-enabled &cin your config.yml!");
            	return false;
              }
              if (player.hasKitPermission("myffa.admin") || (player.hasKitPermission("myffa.setspawn"))) {                	
                location.set("spawn.x", ((Player) commandSender).getLocation().getX());
                location.set("spawn.y", ((Player) commandSender).getLocation().getY());
                location.set("spawn.z", ((Player) commandSender).getLocation().getZ());
                location.set("spawn.world", ((Player) commandSender).getLocation().getWorld().getName());
                   
                player.sendMessage("&cSpawn set.");
              }
              else player.sendMessage(getConfig().getString("not-enough-permissions"));
                break;
            case "reload":
              if (player.hasKitPermission("myffa.admin") || (player.hasKitPermission("myffa.reload"))) {
                for (int i=0; i<1; i++)
                    main.reloadConfig();
                player.sendMessage("&aConfig reloaded.");
              }
              else player.sendMessage(getConfig().getString("not-enough-permissions"));
                break;
            case "help":
                usageCommand();
                break;
        }

        return true;
    }

    private void usageCommand() {
        for (String message : main.getConfig().getStringList("usage-command"))
            player.sendMessage(message);
    }
}
