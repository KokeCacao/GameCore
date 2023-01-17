package com.kokicraft.GameCore.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.kokicraft.GameCore.Main;

public class Core implements CommandExecutor {

	private static Main plugin;

	public Core(Main plugin) {
		Core.plugin = plugin;
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if ((args[0] != null) && (args[0].equalsIgnoreCase("reload"))) {
			plugin.reloadConfig();
			plugin.log.info("[GameCore] Configuration reloaded.");
			return true;
		}
		return false;
	}
}
