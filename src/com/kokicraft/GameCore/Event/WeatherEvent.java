package com.kokicraft.GameCore.Event;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.kokicraft.GameCore.Main;

public class WeatherEvent implements Listener {

	public Main plugin;

	public WeatherEvent(Main instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLightningStrike(LightningStrikeEvent event) {
		event.setCancelled(true);
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState()) {
			if (plugin.getConfig().getBoolean(event.getWorld().getName() + ".Weather.NoRain")) {
				World world = event.getWorld();
				event.setCancelled(true);
				world.setStorm(false);
				world.setThundering(false);
				world.setWeatherDuration(0);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onThunderChange(ThunderChangeEvent event) {
		if (event.toThunderState()) {
			if (plugin.getConfig().getBoolean(event.getWorld().getName() + ".Weather.NoTunder")) {
				World world = event.getWorld();
				event.setCancelled(true);
				world.setStorm(false);
				world.setThundering(false);
				world.setWeatherDuration(0);
				return;
			}
		}
	}
}