package com.kokicraft.GameCore.Event;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.kokicraft.GameCore.Main;

public class BlockEvent implements Listener {

	public Main plugin;

	public BlockEvent(Main instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoLeavesDecay")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onNotePlay(NotePlayEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoNotePlay")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoBlockIgnite")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockGrow(BlockGrowEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoBlockGrow")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoBlockMelt")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockExp(BlockExpEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoBlockExp")) {
			event.setExpToDrop(0);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBurn(BlockBurnEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoBlockBreakByFire")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockDispense(BlockDispenseEvent event) {
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoDispense")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();

		if (event.getBlock().getType() == Material.FIRE) {
			if (plugin.getConfig()
					.getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoPlayerSetFire")) {
				if ((p.isOp()) && (p.getGameMode() == GameMode.CREATIVE)) {
					event.setCancelled(false);
					return;
				} else {
					event.setCancelled(true);
					return;
				}
			}
		}

		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoPlace")) {
			if ((p.isOp()) && (p.getGameMode() == GameMode.CREATIVE)) {
				event.setCancelled(false);
				return;
			} else {
				event.setCancelled(true);
				return;
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoBreak")) {
			if ((p.isOp()) && (p.getGameMode() == GameMode.CREATIVE)) {
				event.setCancelled(false);
				return;
			} else {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockFromTo(BlockFromToEvent event) {
		Block b = event.getBlock();
		if (b.getType() == Material.DRAGON_EGG) {
			if (plugin.getConfig()
					.getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoDragonEggTeleport")) {
				event.setCancelled(true);
				return;
			}
		}
		if (b.getType() == Material.CAKE) {
			if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoCakeEat")) {
				event.setCancelled(true);
				return;
			}
		}
		if (b.getType() == Material.FLOWER_POT) {
			if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoAddFlowerInPot")) {
				event.setCancelled(true);
				return;
			}
		}
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		Player p = event.getPlayer();
		if ((p.isOp()) || (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".PlayerProtect.ColorSign"))) {
			String[] lines = event.getLines();
			for (int i = 0; i < lines.length; i++) {
				event.setLine(i, ChatColor.translateAlternateColorCodes('&', lines[i]));
			}
			return;
		}
	}
}
