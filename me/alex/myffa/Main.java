package me.alex.myffa;

import me.alex.myffa.commands.CommandKit;
import me.alex.myffa.commands.CommandMyFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import me.alex.myffa.EventListener;

public class Main extends JavaPlugin {

    private ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();
    private EventListener evlist;
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        reloadConfig();

        consoleCommandSender.sendMessage(ChatColor.GREEN + "[MyFFA] Enabling...");
        getCommand("myffa").setExecutor(new CommandMyFFA());
        getCommand("mkit").setExecutor(new CommandKit());
        this.evlist = new EventListener();
        getServer().getPluginManager().registerEvents(this.evlist, this);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
