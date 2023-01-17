package com.kokicraft.GameCore.Event;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

import com.kokicraft.GameCore.Main;

public class HangingEvent implements Listener {

	public Main plugin;

	public HangingEvent(Main instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onHangingBreak(HangingBreakEvent event) {
		//EntityDamageByEntityEvent
//		switch(event.getCause()) {
//		case DEFAULT:
//			break;
//		case ENTITY:
//			break;
//		case EXPLOSION:
//			break;
//		case OBSTRUCTION:
//			break;
//		case PHYSICS:
//			break;
//		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onHangingPlace(HangingPlaceEvent event) {
		Player p = event.getPlayer();
		if ((!p.isOp()) || (!(p.getGameMode() == GameMode.CREATIVE))) {
			event.setCancelled(true);
			return;
		}
	}
}