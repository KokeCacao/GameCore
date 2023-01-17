package com.kokicraft.GameCore.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import com.kokicraft.GameCore.Main;

public class EnchantmentEvent implements Listener {

	public Main plugin;

	public EnchantmentEvent(Main instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEnchantItem(EnchantItemEvent event) {
		if ((plugin.getConfig().getBoolean(event.getEnchanter().getWorld().getName() + ".Enchant.Disable")) && (event.getEnchanter().isOp() == false)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
		if ((plugin.getConfig().getBoolean(event.getEnchanter().getWorld().getName() + ".Enchant.NoPrep")) && (event.getEnchanter().isOp() == false)) {
			event.setCancelled(true);
			return;
		}
	}
}