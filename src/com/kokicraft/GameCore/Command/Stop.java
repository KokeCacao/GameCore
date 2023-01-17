package com.kokicraft.GameCore.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kokicraft.GameCore.Main;

public class Stop implements CommandExecutor {

  private Main plugin;

  public Stop(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, final String[] args) {
    if (args.length == 0) {
      plugin.getServer().getConsoleSender().sendMessage(
          "Server stop in " + String.valueOf(Main.plugin.getConfig().getInt("Server.StopDelay")) + " seconds.");
      Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
        Integer i = Main.plugin.getConfig().getInt("Server.StopDelay");

        @Override
        public void run() {
          if (i > 0) {
            i--;
            if (((i <= 5) && (i > 0)) || (i == 10) || (i == 20)
                || (i == Main.plugin.getConfig().getInt("Server.StopDelay"))) {
              plugin.getServer().getConsoleSender().sendMessage("Server stop in " + i + " seconds.");
            }
          } else {
            plugin.getServer().getConsoleSender().sendMessage("Server stop.");
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
              p.kickPlayer("Server stop.");
            }
            plugin.getServer().shutdown();
          }
        }
      }, 0L, 20L);
    } else if (args.length == 1) {
      plugin.getServer().getConsoleSender().sendMessage("Server stop in " + args[0] + " seconds");
      Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
        Integer i = Integer.valueOf(args[0]);

        @Override
        public void run() {
          if (i > 0) {
            i--;
            if (((i <= 5) && (i > 0)) || (i == 10) || (i == 20)
                || (i == Main.plugin.getConfig().getInt("Server.StopDelay"))) {
              plugin.getServer().getConsoleSender().sendMessage("Server stop in " + i + " seconds.");
            }
          } else {
            plugin.getServer().getConsoleSender().sendMessage("Server stop.");
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
              p.kickPlayer("Server stop.");
            }
            plugin.getServer().shutdown();
          }
        }
      }, 0L, 20L);
    }
    return true;
  }
}
