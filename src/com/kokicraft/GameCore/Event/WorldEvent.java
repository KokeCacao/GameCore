package com.kokicraft.GameCore.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

import com.kokicraft.GameCore.Main;

public class WorldEvent implements Listener {

	public Main plugin;

	public WorldEvent(Main instance) {
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onStructureGrow(StructureGrowEvent event) {
		if (plugin.getConfig().getBoolean(event.getLocation().getWorld().getName() + ".World.NoTreeGrow")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPortalCreate(PortalCreateEvent event) {
		if (plugin.getConfig().getBoolean(event.getWorld().getName() + ".World.NoPortalCreate")) {
			event.setCancelled(true);
			return;
		}
	}
}
