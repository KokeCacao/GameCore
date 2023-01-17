package com.kokicraft.GameCore.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kokicraft.GameCore.Main;

public class Reload implements CommandExecutor {

  private Main plugin;

  public Reload(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, final String[] args) {
    if (args.length == 0) {
      plugin.getServer().getConsoleSender()
          .sendMessage("Server reload in " + String.valueOf(Main.plugin.getConfig().getInt("Server.ReloadDelay")) + " seconds.");
      Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
        Integer i = Main.plugin.getConfig().getInt("Server.ReloadDelay");

        @Override
        public void run() {
          if (i > 0) {
            i--;
            if (((i <= 5) && (i > 0)) || (i == 10) || (i == 20)
                || (i == Main.plugin.getConfig().getInt("Server.ReloadDelay"))) {
              plugin.getServer().getConsoleSender().sendMessage("Server reload in " + i + " seconds.");
            }
          } else {
            plugin.getServer().getConsoleSender().sendMessage("Server Shutting down...");
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
              p.kickPlayer("Server reloading. Will be back soon.");
            }
            plugin.getServer().shutdown();
          }
        }
      }, 0L, 20L);
    } else if (args.length == 1) {
      Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
        Integer i = Integer.valueOf(args[0]);

        @Override
        public void run() {
          if (i > 0) {
            i--;
            if (((i <= 5) && (i > 0)) || (i == 10) || (i == 20)
                || (i == Main.plugin.getConfig().getInt("Server.ReloadDelay"))) {
              plugin.getServer().getConsoleSender().sendMessage("Server reload in " + i + " seconds.");
            }
          } else {
            plugin.getServer().getConsoleSender().sendMessage("Server Shutting down...");
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
              p.kickPlayer("Server reloading. Will be back soon.");
            }
            plugin.getServer().shutdown();
          }
        }
      }, 0L, 20L);
    }
    return true;
  }
}
