package me.alex.myffa;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import me.alex.myffa.Main;

public class EventListener implements Listener {
	
   private Main main = Main.getInstance();
   private FileConfiguration getConfig() {
	   return main.getConfig();
   }
   private String firstsignline = getConfig().getString("first-sign-line");
   
   @EventHandler
   public void onSignChange(SignChangeEvent event) {
       if (event.getPlayer().hasPermission("myffa.admin") || event.getPlayer().hasPermission("myffa.sign")) {
           if (event.getLine(0).contains("[MyFFA]"))
               event.setLine(0, ChatColor.translateAlternateColorCodes('&', firstsignline));
       }
       else event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("not-enough-permissions")));
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
       if((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
           Block block = event.getClickedBlock();
           if(block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
               Sign sign = (Sign) event.getClickedBlock().getState();
               if (sign.getLine(0).contains(ChatColor.translateAlternateColorCodes('&', firstsignline))) {
                       event.getPlayer().chat("/mkit" + " " + (sign.getLine(1).toString()));
                       if (getConfig().getBoolean("broadcast-kit-signs-clicks")) {
                       System.out.println(ChatColor.translateAlternateColorCodes('&', "&c%player% used a kit '%kit%' sign!").replaceAll("%player%", event.getPlayer().getName()).replaceAll("%kit%", sign.getLine(1).toString()));
                       }
               }
           }
       }
   }

}
