package com.kokicraft.GameCore.Event;

import org.bukkit.GameMode;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import com.kokicraft.GameCore.Main;

public class VehicleEvent implements Listener {

	public Main plugin;

	public VehicleEvent(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
	    if (event.getVehicle() instanceof Boat) {
	    	if (event.getAttacker() == null) {
		    	if (plugin.getConfig().getBoolean(event.getVehicle().getWorld().getName() + "Vehicle.NoNatureBoatDistroy")) {
		    		event.setCancelled(true);
		    		return;
		    	}
	    	} else if (event.getAttacker() instanceof Player) {
	    		Player p = (Player) event.getAttacker();
	    		if ((!p.isOp()) || (p.getGameMode() != GameMode.CREATIVE)) {
			    	if (plugin.getConfig().getBoolean(event.getVehicle().getWorld().getName() + "Vehicle.NoPlayerBoatDistroy")) {
			    		event.setCancelled(true);
			    		return;
			    	}
	    		}
	    	} else {
		    	if (plugin.getConfig().getBoolean(event.getVehicle().getWorld().getName() + "Vehicle.NoMobBoatDistroy")) {
		    		event.setCancelled(true);
		    		return;
		    	}
	    	}
	    }
	    if (event.getVehicle() instanceof Minecart) {
	    	if (event.getAttacker() == null) {
		    	if (plugin.getConfig().getBoolean(event.getVehicle().getWorld().getName() + "Vehicle.NoNatureMinecartDistroy")) {
		    		event.setCancelled(true);
		    		return;
		    	}
	    	} else if (event.getAttacker() instanceof Player) {
	    		Player p = (Player) event.getAttacker();
	    		if ((!p.isOp()) || (p.getGameMode() != GameMode.CREATIVE)) {
			    	if (plugin.getConfig().getBoolean(event.getVehicle().getWorld().getName() + "Vehicle.NoPlayerMinecartDistroy")) {
			    		event.setCancelled(true);
			    		return;
			    	}
	    		}
	    	} else {
		    	if (plugin.getConfig().getBoolean(event.getVehicle().getWorld().getName() + "Vehicle.NoMobMinecartDistroy")) {
		    		event.setCancelled(true);
		    		return;
		    	}
	    	}
	    }
	}
}
