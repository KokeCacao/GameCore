package com.kokicraft.GameCore.Event;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.kokicraft.GameCore.Main;

public class EntityEvent implements Listener {

	public Main plugin;

	public EntityEvent(Main instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (plugin.getConfig()
				.getBoolean(event.getLocation().getWorld().getName() + ".EntityProtect.NoEntityExplode")) {
			event.setCancelled(true);
			event.getLocation().getWorld().createExplosion(event.getLocation(), 0F);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		if (plugin.getConfig().getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoEntityExplode")) {
			event.setCancelled(true);
			return;
		} else if (plugin.getConfig()
				.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoExplodeFire")) {
			event.setFire(false);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN) {
			if (plugin.getConfig()
					.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoEndermanChangeBlock")) {
				event.setCancelled(true);
				return;
			}
		} else if (event.getEntityType() == EntityType.WITHER) {
			if (plugin.getConfig()
					.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoWitherChangeBlock")) {
				event.setCancelled(true);
				return;
			}
		} else if (event.getEntityType() == EntityType.BOAT) {
			if (plugin.getConfig()
					.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoBoatChangeBlock")) {
				event.setCancelled(true);
				return;
			}
		} else if (event.getEntityType() == EntityType.FALLING_BLOCK) {
			if (plugin.getConfig()
					.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoFallingBlockChangeBlock")) {
				event.setCancelled(true);
				return;
			}
		} else if (event.getEntityType() == EntityType.ENDER_DRAGON) {
			if (plugin.getConfig()
					.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoEnderDragonChangeBlock")) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getEntity() instanceof Player) {
			return;
		}
		for (String reason : plugin.getConfig()
				.getStringList(event.getLocation().getWorld().getName() + ".Spawn.NoSpawnReason")) {
			SpawnReason type = SpawnReason.valueOf(reason);
			if (event.getSpawnReason() == type) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityBreakDoor(EntityBreakDoorEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".BlockProtect.NoDoorBreak")) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent event) {
		if (event.getEntity() instanceof Player) {
			if (plugin.getConfig()
					.getBoolean(event.getFrom().getWorld().getName() + ".EntityProtect.PlayerNoPortalTeleport")) {
				event.setCancelled(true);
				return;
			}
		} else {
			if (plugin.getConfig()
					.getBoolean(event.getFrom().getWorld().getName() + ".EntityProtect.EntityNoPortalTeleport")) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (plugin.getConfig().getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.PlayerNoFood")) {
			event.setCancelled(true);
			if (event.getEntity() instanceof Player) {
				Player p = (Player) event.getEntity();
				p.setFoodLevel(20);
			}
			return;
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (event.getCause() == DamageCause.VOID) {
				if ((player.getGameMode().equals(GameMode.SPECTATOR)) || (plugin.getConfig()
						.getBoolean(player.getWorld().getName() + ".EntityProtect.PlayerVoidSpawn"))) {
					event.setCancelled(true);
					player.teleport(player.getWorld().getSpawnLocation());
				}
			} else {
				if (plugin.getConfig().getBoolean(player.getWorld().getName() + ".EntityProtect.NoPlayerDamage")) {
					event.setCancelled(true);
				}
			}
			// void animal spawn
		} else if (event.getEntity() instanceof LivingEntity) {
			LivingEntity e = (LivingEntity) event.getEntity();
			if (event.getCause() == DamageCause.VOID) {
				if (plugin.getConfig()
						.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.EntityVoidSpawn")) {
					event.setCancelled(true);
					e.teleport(event.getEntity().getWorld().getSpawnLocation());
					return;
				}
			} else if (plugin.getConfig()
					.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoMobDamage")) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (plugin.getConfig().getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.DamagePass")) {
			Double d = event.getDamage();
			Entity damager = event.getDamager();
			Entity e = event.getEntity();

			LivingEntity up = null;
			LivingEntity down = null;

			if (e.getPassenger() instanceof LivingEntity) {
				up = (LivingEntity) e.getPassenger();
			}
			if (e.getVehicle() instanceof LivingEntity) {
				down = (LivingEntity) e.getVehicle();
			}

			while ((up != null) && (up.getPassenger() != null)) {
				up.damage(d, damager);
				if (up.getPassenger() instanceof LivingEntity) {
					up = (LivingEntity) up.getPassenger();
				} else {
					up = null;
				}
			}

			while ((down != null) && (down.getPassenger() != null)) {
				down.damage(d, damager);
				if (down.getPassenger() instanceof LivingEntity) {
					down = (LivingEntity) down.getPassenger();
				} else {
					down = null;
				}
			}
		}
		
		if ((event.getEntity().getType() == EntityType.ITEM_FRAME)
				|| (event.getEntity().getType() == EntityType.PAINTING)) {
			if (event.getDamager() instanceof Player) {
				if (plugin.getConfig().getBoolean(
						event.getEntity().getWorld().getName() + ".EntityProtect.NoBlockLikeEntityBreak")) {
					Player p = (Player) event.getDamager();
					if (!(p.isOp() || !(p.getGameMode() == GameMode.CREATIVE))) {
						event.setCancelled(true);
						return;
					}
				}
			} else {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onProjectileHit(ProjectileHitEvent event) {
		// if (event.getEntity() instanceof Arrow) {
		// event.getEntity().remove();
		// return;
		// }
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSheepDyeWool(SheepDyeWoolEvent event) {
		if (plugin.getConfig().getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoSheepDyeWool")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSheepRegrowWool(SheepRegrowWoolEvent event) {
		if (plugin.getConfig()
				.getBoolean(event.getEntity().getWorld().getName() + ".EntityProtect.NoSheepRegrowWool")) {
			event.setCancelled(true);
			return;
		}
	}
}
