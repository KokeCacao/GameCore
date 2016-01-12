package com.kokicraft.GameCore;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class PluginListener implements Listener {

	public Main plugin;

	public PluginListener(Main instance) {
		plugin = instance;
	}
	// BlockExplodeEvent

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		event.setCancelled(true);
		event.getLocation().getWorld().createExplosion(event.getLocation(), 0F);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		// if ((event.getEntity() instanceof Fireball || event.getEntity()
		// instanceof SmallFireball)) {
		event.setFire(false);
		// }
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN) {
			event.setCancelled(true);
		} else if (event.getEntityType() == EntityType.WITHER) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getEntity() instanceof Player) {
			return;
		}
		event.setCancelled(true);
		switch (event.getSpawnReason()) {
		case CUSTOM:
			event.setCancelled(false);
			break;
		case SPAWNER_EGG:
			event.setCancelled(false);
			break;
		case SLIME_SPLIT:
			if (plugin.getConfig().getBoolean("Spawn." + event.getLocation().getWorld().getName() + ".NoSlimeSplit")) {
				event.setCancelled(false);
				break;
			}
		case SPAWNER:
			if (plugin.getConfig().getBoolean("Spawn." + event.getLocation().getWorld().getName() + ".NoSpawner")) {
				event.setCancelled(false);
				break;
			}
		case EGG:
			if (plugin.getConfig().getBoolean("Spawn." + event.getLocation().getWorld().getName() + ".NoThrowEgg")) {
				event.setCancelled(false);
				break;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityBreakDoor(EntityBreakDoorEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			if (plugin.getConfig()
					.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoDoorBreak")) {
				event.setCancelled(true);
			}

		} else {
			if (!((Player) event.getEntity()).isOp()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoLeavesDecay")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onNotePlay(NotePlayEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoNotePlay")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoBlockIgnite")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockGrow(BlockGrowEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoBlockGrow")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoBlockMelt")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockExp(BlockBreakEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoBlockExp")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBurn(BlockBurnEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoBlockBreakByFire")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockDispense(BlockDispenseEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getBlock().getWorld().getName() + ".NoDispense")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getFrom().getWorld().getName() + ".NoPortalTeleport")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onStructureGrow(StructureGrowEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getLocation().getWorld().getName() + ".NoTreeGrow")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPortalCreateEvent(PortalCreateEvent event) {
		if (plugin.getConfig()
				.getBoolean("BlockProtect." + event.getWorld().getName() + ".NoPortalCreate")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLightningStrike(LightningStrikeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState()) {
			if (plugin.getConfig().getBoolean("BlockProtect." + event.getWorld().getName() + ".NoWeather")) {
					World world = event.getWorld();
					if (event.getWorld() == world) {
						event.setCancelled(true);
						world.setStorm(false);
						world.setThundering(false);
						world.setWeatherDuration(0);
					}
				}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
			if (plugin.getConfig()
					.getBoolean("PlayerProtect." + event.getEntity().getWorld().getName() + ".NoFood")) {
				event.setCancelled(true);
			}
		
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			switch (event.getCause()) {
			case CONTACT:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoCactus")) {
					event.setCancelled(true);
					break;
				}
			case LAVA:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoLava")) {
					event.setCancelled(true);
					break;
				}
			case BLOCK_EXPLOSION:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoExplosion")) {
					event.setCancelled(true);
					break;
				}
			case VOID:
					if (plugin.getConfig().getBoolean("PlayerProtect." + player.getWorld().getName() + ".VoidPlayerSpawn")) {
						event.setCancelled(true);
						player.teleport(player.getWorld().getSpawnLocation());
						break;
					}
			case FALL:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoFall")) {
					event.setCancelled(true);
					break;
				}
			case DROWNING:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoDrowning")) {
					event.setCancelled(true);
					break;
				}
			case LIGHTNING:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoLightning")) {
					event.setCancelled(true);
					break;
				}
			case FIRE:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoFire")) {
					event.setCancelled(true);
					break;
				}
			case FIRE_TICK:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoFire")) {
					event.setCancelled(true);
					break;
				}
			case SUFFOCATION:
				if (plugin.getConfig().getBoolean("Damage." + player.getWorld().getName() + "NoWall")) {
					event.setCancelled(true);
					break;
				}
			}
			// void animal spawn
		} else if (!(event.getEntity() instanceof LivingEntity)) {
			LivingEntity e = (LivingEntity) event.getEntity();
			if (event.getCause() == DamageCause.VOID) {
				if (plugin.getConfig()
						.getBoolean("EntityProtect." + event.getEntity().getWorld().getName() + ".EntityVoidSpawn")) {
						event.setCancelled(true);
						e.teleport(event.getEntity().getWorld().getSpawnLocation());
					}
			} else if (plugin.getConfig().getBoolean("EntityProtect." + event.getEntity().getWorld().getName() + ".NoDamage")) {
				event.setCancelled(true);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().isOp())
				&& (event.getPlayer().getEquipment().getHelmet() != null)
				&& (event.getPlayer().getEquipment().getHelmet().getType() == Material.COOKIE)) {
			event.getPlayer().sendMessage("TypeYouClicked:");
			event.getPlayer()
					.sendMessage(String.valueOf(event.getClickedBlock().getType()) + " ("
							+ String.valueOf(event.getClickedBlock().getTypeId()) + ":"
							+ String.valueOf(event.getClickedBlock().getData()) + ")");
			event.getPlayer().sendMessage("ItemInHand:");
			event.getPlayer()
					.sendMessage(String.valueOf(event.getPlayer().getItemInHand().getType()) + " ("
							+ String.valueOf(event.getPlayer().getItemInHand().getTypeId()) + ":"
							+ String.valueOf(event.getPlayer().getItemInHand().getItemMeta()) + ")");
			event.getPlayer().sendMessage("LocationBlock:");
			event.getPlayer().sendMessage(String.valueOf(event.getClickedBlock().getLocation().getWorld().getName()));
			event.getPlayer().sendMessage("X:" + String.valueOf(event.getClickedBlock().getLocation().getBlockX()));
			event.getPlayer().sendMessage("Y:" + String.valueOf(event.getClickedBlock().getLocation().getBlockY()));
			event.getPlayer().sendMessage("Z:" + String.valueOf(event.getClickedBlock().getLocation().getBlockZ()));
			event.getPlayer().sendMessage("LocationYou:");
			event.getPlayer()
					.sendMessage("World:" + String.valueOf(event.getPlayer().getLocation().getWorld().getName()));
			event.getPlayer().sendMessage("X:" + String.valueOf(event.getPlayer().getLocation().getX()));
			event.getPlayer().sendMessage("Y:" + String.valueOf(event.getPlayer().getLocation().getY()));
			event.getPlayer().sendMessage("Z:" + String.valueOf(event.getPlayer().getLocation().getZ()));
			event.getPlayer().sendMessage("Pitch:" + String.valueOf(event.getPlayer().getLocation().getPitch()));
			event.getPlayer().sendMessage("Yaw:" + String.valueOf(event.getPlayer().getLocation().getYaw()));
			event.setCancelled(true);
			event.getPlayer().updateInventory();
		}
		if ((event.getAction() != Action.LEFT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_AIR)
				&& (!event.getPlayer().isOp())) {
			for (Integer blockId : plugin.getConfig().getIntegerList("BlockNoInteract")) {
				if (event.getClickedBlock().getTypeId() == blockId) {
					event.setCancelled(true);
					event.getPlayer().updateInventory();
				}

			}
			if ((event.getClickedBlock().getType() == Material.CAULDRON)
					&& (event.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE)) {
				event.setCancelled(true);
				event.getPlayer().updateInventory();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryOpen(InventoryOpenEvent event) {
		Player player = null;
		if ((event.getPlayer() instanceof Player)) {
			player = (Player) event.getPlayer();
		}
		switch (event.getInventory().getType()) {
		case ANVIL:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".anvil"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case BEACON:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".beacon"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case BREWING:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".brewing"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case CHEST:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".chest"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case CRAFTING:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".crafting"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case DISPENSER:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".dispenser"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case DROPPER:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".dropper"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case ENCHANTING:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".enchanting"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case ENDER_CHEST:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".enderchest"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case HOPPER:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".hopper"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case FURNACE:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".furnace"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		case WORKBENCH:
			if ((!plugin.getConfig().getBoolean("Open." + player.getWorld().getName() + ".workbench"))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
			break;
		}

	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!player.isOp()) {
			event.setCancelled(true);
		}
		if ((!event.isCancelled()) && (event.getBlock().getTypeId() == 51) && (!player.isOp())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().isOp()) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		int bucketType = player.getItemInHand().getTypeId();
		if ((bucketType == 326) && (!player.isOp())) {
			event.setCancelled(true);
		} else if ((bucketType == 327) && (!player.isOp())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		Player player = event.getPlayer();
		int source = event.getBlockClicked().getTypeId();
		if (((source == 8) || (source == 9)) && (!player.isOp())) {
			event.setCancelled(true);
		} else if (((source == 10) || (source == 11)) && (!player.isOp())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFireBreak(PlayerInteractEvent event) {
		if ((event.getClickedBlock() != null) || (event.getAction() != null)) {
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			if ((event.getAction() == Action.LEFT_CLICK_BLOCK)
					&& (block.getRelative(BlockFace.UP).getType() == Material.FIRE)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockFromTo(BlockFromToEvent e) {
		Block block = e.getBlock();
		if (block.getTypeId() == 122) {
			e.setCancelled(true);
		}
		// -------------
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		String world = player.getWorld().getName();
		FileConfiguration config = this.plugin.getConfig();
		List<Integer> drops = config.getIntegerList("worlds." + world + ".item-drop");
		for (Integer drop : drops) {
			if ((event.getItemDrop().getItemStack().getTypeId() == drop.intValue()) && (!player.isOp())) {
				event.setCancelled(true);
			}
		}
		if ((config.getBoolean("worlds." + world + ".disable-all-drops")) && (!player.isOp())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		String world = player.getWorld().getName();
		FileConfiguration config = this.plugin.getConfig();
		List<Integer> pickups = config.getIntegerList("worlds." + world + ".pickup");
		for (Integer pickup : pickups) {
			if ((event.getItem().getItemStack().getTypeId() == pickup.intValue()) && (!player.isOp())) {
				event.setCancelled(true);
				event.getItem().setPickupDelay(20);
			}
		}
		if ((config.getBoolean("worlds." + world + ".disable-all-pickup")) && (!player.isOp())) {
			event.setCancelled(true);
			event.getItem().setPickupDelay(20);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHangingBreak(HangingBreakEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHangingPlace(HangingPlaceEvent event) {
		Player p = event.getPlayer();
		if (!p.isOp()) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		if (entity.getType().equals(EntityType.ITEM_FRAME)) {
			ItemFrame iFrame = (ItemFrame) entity;
			if (((iFrame.getItem().equals(null) == false) && (iFrame.getItem().getType().equals(Material.AIR) == false))
					&& (!player.isOp())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player)) {
			Player p = (Player) e.getDamager();
			if ((e.getEntity().getType() == EntityType.ITEM_FRAME) && (!p.isOp())) {
				e.setCancelled(true);
			}
		}
		if (((e.getDamager() instanceof Projectile)) && (e.getEntity().getType() == EntityType.ITEM_FRAME)) {
			Player player = (Player) ((Projectile) e.getDamager()).getShooter();
			e.setCancelled(true);
		}
		if (((e.getDamager() instanceof Projectile)) && (e.getEntity().getType() == EntityType.PAINTING)) {
			Player player = (Player) ((Projectile) e.getDamager()).getShooter();
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryClick(InventoryClickEvent e) {
		// click
		if ((plugin.getConfig().getBoolean("NoClickInventory")) && (e.getWhoClicked().isOp() == false)) {
			e.setCancelled(true);
			((Player) e.getWhoClicked()).updateInventory();
		}
		// craft
		if ((e.getSlotType() == InventoryType.SlotType.RESULT)
				&& ((e.getInventory().getType().equals(InventoryType.CRAFTING))
						|| (e.getInventory().getType().equals(InventoryType.WORKBENCH)))
				&& (e.getSlot() == 0) && (e.getInventory().getItem(0) != null)) {
			e.setCancelled(true);
			((Player) e.getWhoClicked()).updateInventory();
			return;
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerKick(PlayerKickEvent e) {
		e.setLeaveMessage("");
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSignChange(SignChangeEvent evevt) {
		Player p = evevt.getPlayer();
		if (p.isOp()) {
			String[] lines = evevt.getLines();
			for (int i = 0; i < lines.length; i++) {
				evevt.setLine(i, ChatColor.translateAlternateColorCodes('&', lines[i]));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void joinTeleport(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (plugin.getConfig().getBoolean("JoinTeleport.Enable")) {
			World world = Bukkit.getWorld(plugin.getConfig().getString("JoinTeleport.World"));
			Integer x = plugin.getConfig().getInt("JoinTeleport.X");
			Integer y = plugin.getConfig().getInt("JoinTeleport.Y");
			Integer z = plugin.getConfig().getInt("JoinTeleport.Z");
			Integer pitch = plugin.getConfig().getInt("JoinTeleport.Pitch");
			Integer yaw = plugin.getConfig().getInt("JoinTeleport.Yaw");
			Location l = new Location(world, x, y, z, pitch, yaw);
			if (world != null) {
				player.teleport(l);
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			event.getEntity().remove();
		}
	}
}