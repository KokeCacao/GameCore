package com.kokicraft.GameCore;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements CommandExecutor {

	public static Main plugin;
	Logger log = Logger.getLogger("Minecraft");
	Integer boradcase;

	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new PluginListener(this), this);
		this.getConfig();
		this.saveDefaultConfig();
		log.info("[GameCore] Version " + this.getDescription().getVersion() + " has been enabled.");
		boradcase();
		getCommand("core").setExecutor(new Main());
	}

	public void onDisable() {
		if (getConfig().getBoolean("KillEntityOnDisable")) {
			for (World w : Bukkit.getWorlds()) {
				for (Entity e : w.getLivingEntities()) {
					e.remove();
				}
			}
		}
		if (getConfig().getBoolean("BoardCase.Enable")) {
			Bukkit.getScheduler().cancelTask(boradcase);
		}
		log.info("[GameCore] GameCore has been disabled.");
	}

	@SuppressWarnings("deprecation")
	public void boradcase() {
		if (getConfig().getBoolean("BoardCase.Enable")) {
			boradcase = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					Bukkit.broadcastMessage(getConfig().getString("BoardCase.Message"));
				}
			}, 0L, getConfig().getInt("BoardCase.Tick"));
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender.isOp()) {
			if ((args[0] != null) && (args[0].equalsIgnoreCase("reload"))) {
				this.reloadConfig();
				if (boradcase != null) {
					Bukkit.getScheduler().cancelTask(boradcase);
					boradcase();
				}
				sender.sendMessage("[GameCore] Configuration reloaded.");
				log.info("[GameCore] Configuration reloaded.");
				return true;
			} else if ((args[0] != null) && (args[0].equalsIgnoreCase("stopserver"))) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					p.kickPlayer("服务器关闭");
				}
				plugin.getServer().shutdown();
			} else if ((args[0] != null) && (args[0].equalsIgnoreCase("reloadserver"))) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					p.kickPlayer("服务器重启");
				}
				plugin.getServer().shutdown();
			} else {
				sender.sendMessage("[GameCore] Type '/core help' for help.");
			}
		}
		return false;
	}
}
