package com.kokicraft.GameCore.Event;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import com.kokicraft.GameCore.Main;

public class PlayerEvent implements Listener {

	public Main plugin;

	public PlayerEvent(Main instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().isOp()) && (event.getPlayer().getEquipment().getItemInHand() != null) && (event.getPlayer().getEquipment().getItemInHand().getType() == Material.COOKIE)) {
			event.getPlayer().sendMessage("TypeYouClicked:");
			event.getPlayer().sendMessage(String.valueOf(event.getClickedBlock().getType()) + " (" + event.getClickedBlock().getType().toString() + ")");
			event.getPlayer().sendMessage("ItemInHand:");
			event.getPlayer().sendMessage(String.valueOf(event.getPlayer().getItemInHand().getType()) + " (" + String.valueOf(event.getPlayer().getItemInHand().getType()) + ":" + String.valueOf(event.getPlayer().getItemInHand().getItemMeta()) + ")");
			event.getPlayer().sendMessage("LocationBlock:");
			event.getPlayer().sendMessage(String.valueOf(event.getClickedBlock().getLocation().getWorld().getName()));
			event.getPlayer().sendMessage("X:" + String.valueOf(event.getClickedBlock().getLocation().getBlockX()));
			event.getPlayer().sendMessage("Y:" + String.valueOf(event.getClickedBlock().getLocation().getBlockY()));
			event.getPlayer().sendMessage("Z:" + String.valueOf(event.getClickedBlock().getLocation().getBlockZ()));
			event.getPlayer().sendMessage("LocationYou:");
			event.getPlayer().sendMessage("World:" + String.valueOf(event.getPlayer().getLocation().getWorld().getName()));
			event.getPlayer().sendMessage("X:" + String.valueOf(event.getPlayer().getLocation().getX()));
			event.getPlayer().sendMessage("Y:" + String.valueOf(event.getPlayer().getLocation().getY()));
			event.getPlayer().sendMessage("Z:" + String.valueOf(event.getPlayer().getLocation().getZ()));
			event.getPlayer().sendMessage("Pitch:" + String.valueOf(event.getPlayer().getLocation().getPitch()));
			event.getPlayer().sendMessage("Yaw:" + String.valueOf(event.getPlayer().getLocation().getYaw()));
			event.setCancelled(true);
			event.getPlayer().updateInventory();
			return;
		}
		if ((event.getAction() != Action.LEFT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_AIR) && (!event.getPlayer().isOp())) {
			List<String> list = plugin.getConfig().getStringList(event.getPlayer().getWorld().getName() + ".BlockProtect.NoInteract");
			if (list.contains(event.getClickedBlock().getType().name())) {
				event.setCancelled(true);
				event.getPlayer().updateInventory();
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Material bucketType = event.getBucket();
		if (plugin.getConfig().getBoolean(event.getBlockClicked().getWorld().getName() + ".BlockProtect.NoWaterBukkit") && ((bucketType == Material.WATER_BUCKET) && (!player.isOp()) || (player.getGameMode() != GameMode.CREATIVE))) {
			event.setCancelled(true);
			return;
		} else if (plugin.getConfig().getBoolean(event.getBlockClicked().getWorld().getName() + ".BlockProtect.NoLavaBukkit") && ((bucketType == Material.LAVA_BUCKET) && (!player.isOp()) || (player.getGameMode() != GameMode.CREATIVE))) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		Player player = event.getPlayer();
		Material source = event.getBlockClicked().getType();
		if ((plugin.getConfig().getBoolean(event.getBlockClicked().getWorld().getName() + ".BlockProtect.NoWaterBukkit")) && source == Material.WATER && (!player.isOp() || (player.getGameMode() != GameMode.CREATIVE))) {
			event.setCancelled(true);
			return;
		} else if ((plugin.getConfig().getBoolean(event.getBlockClicked().getWorld().getName() + ".BlockProtect.NoLavaBukkit")) && source == Material.LAVA && (!player.isOp() || (player.getGameMode() != GameMode.CREATIVE))) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFireBreak(PlayerInteractEvent event) {
		if ((event.getClickedBlock() != null) && (event.getAction() != null)) {
			if (plugin.getConfig().getBoolean(event.getClickedBlock().getWorld().getName() + ".BlockProtect.NoBreak")) {
				Block block = event.getClickedBlock();
				if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && (block.getRelative(BlockFace.UP).getType() == Material.FIRE)) {
					if ((!event.getPlayer().isOp()) || (event.getPlayer().getGameMode() != GameMode.CREATIVE)) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		String world = p.getWorld().getName();
		List<String> list = plugin.getConfig().getStringList(world + ".Item.Drop.List");

		if (!p.isOp() || (p.getGameMode() != GameMode.CREATIVE)) {
			if (plugin.getConfig().getBoolean(world + ".Item.Drop.Whitelist")) {
				if (!list.contains(event.getItemDrop().getItemStack().getType().toString())) {
					event.setCancelled(true);
					return;
				}
			} else {
				if (list.contains(event.getItemDrop().getItemStack().getType().toString())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerPickupItem(EntityPickupItemEvent event) {
    if (!(event.getEntity() instanceof Player)) return;
		Player p = (Player) event.getEntity();
		String world = p.getWorld().getName();
		List<String> list = plugin.getConfig().getStringList(world + ".Item.Pick.List");

		if (!p.isOp() || (p.getGameMode() != GameMode.CREATIVE)) {
			if ((plugin.getConfig().getBoolean(world + ".Item.Pick.CursorCheck"))
					&& ((p.getItemOnCursor() != null) || (p.getItemOnCursor().getType() == Material.AIR))) {
				event.setCancelled(true);
				event.getItem().setPickupDelay(20);
				return;
			}

			if (plugin.getConfig().getBoolean(world + ".Item.Pick.Whitelist")) {
				if (!list.contains(event.getItem().getItemStack().getType().toString())) {
					event.setCancelled(true);
					return;
				}
			} else {
				if (list.contains(event.getItem().getItemStack().getType().toString())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		event.setCancelled(true);
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		if (entity.getType().equals(EntityType.ITEM_FRAME)) {
			if (plugin.getConfig().getBoolean(event.getPlayer().getWorld().getName() + ".EntityProtect.NoBlockLikeEntityBreak")) {
				ItemFrame iFrame = (ItemFrame) entity;
				if (((iFrame.getItem().equals(null) == false) && (iFrame.getItem().getType().equals(Material.AIR) == false))
						&& (!player.isOp() || (player.getGameMode() != GameMode.CREATIVE))) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
		return;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
		return;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
		return;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerKick(PlayerKickEvent e) {
		e.setLeaveMessage("");
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void joinTeleport(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (plugin.getConfig().getBoolean("JoinTeleport.Enable")) {
			World world = Bukkit.getWorld(plugin.getConfig().getString("JoinTeleport.World"));
			Integer x = plugin.getConfig().getInt("JoinTeleport.X");
			Integer y = plugin.getConfig().getInt("JoinTeleport.Y");
			Integer z = plugin.getConfig().getInt("JoinTeleport.Z");
			Integer pitch = plugin.getConfig().getInt("JoinTeleport.Pitch");
			Integer yaw = plugin.getConfig().getInt("JoinTeleport.Yaw");
			Location l = new Location(world, x, y, z, pitch, yaw).add(0.5, 1, 0.5);
			player.teleport(l);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerEditBook(PlayerEditBookEvent e) {
		Player player = e.getPlayer();
		if ((plugin.getConfig().getBoolean(player.getWorld().getName() + ".PlayerProtect.NoEditBook"))
				&& (!player.isOp() || (player.getGameMode() != GameMode.CREATIVE))) {
			e.setCancelled(true);
			return;
		}
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFish(PlayerFishEvent e) {
		Player player = e.getPlayer();
		if ((plugin.getConfig().getBoolean(player.getWorld().getName() + ".PlayerProtect.NoFish"))
				&& (!player.isOp() || (player.getGameMode() != GameMode.CREATIVE))) {
			e.setCancelled(true);
			return;
		}
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerShearEntity(PlayerShearEntityEvent e) {
		Player player = e.getPlayer();
		if ((plugin.getConfig().getBoolean(player.getWorld().getName() + ".PlayerProtect.NoShearEntity"))
				&& (!player.isOp() || (player.getGameMode() != GameMode.CREATIVE))) {
			e.setCancelled(true);
			return;
		}
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent e) {
		Player player = e.getPlayer();
		if ((plugin.getConfig().getBoolean(player.getWorld().getName() + ".PlayerProtect.NoStatistic"))) {
			e.setCancelled(true);
			return;
		}
		return;
	}
}
