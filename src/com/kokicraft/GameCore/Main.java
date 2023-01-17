package com.kokicraft.GameCore;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.kokicraft.GameCore.Command.Core;
import com.kokicraft.GameCore.Command.Reload;
import com.kokicraft.GameCore.Command.Stop;
import com.kokicraft.GameCore.Command.Teleport;
import com.kokicraft.GameCore.Event.BlockEvent;
import com.kokicraft.GameCore.Event.EnchantmentEvent;
import com.kokicraft.GameCore.Event.EntityEvent;
import com.kokicraft.GameCore.Event.HangingEvent;
import com.kokicraft.GameCore.Event.InventoryEvent;
import com.kokicraft.GameCore.Event.PlayerEvent;
import com.kokicraft.GameCore.Event.VehicleEvent;
import com.kokicraft.GameCore.Event.WeatherEvent;
import com.kokicraft.GameCore.Event.WorldEvent;

public class Main extends JavaPlugin {

	//to-do: interact, inventoryClick
	//inv
	//player
	
	
	
	
	
	// BlockFromEvent(BlockGrowEvent)
	// BlockMultiPlaceEvent(BlockPlaceEvent)
	/* BlockPhysicsEvent */
	// BlockPistonEvent
	// BlockPistonExtendEvent
	// BlockPistonRetractEvent
	// BlockRedstoneEvent
	// BlockSpreadEvent(BlockFormEvent(BlockGrowEvent))
	// EntityBlockFormEvent(BlockFormEvent(BlockGrowEvent))

	/* CreeperPowerEvent */
	/* EntityCombustByBlockEvent */
	/* EntityCombustByEntityEvent */
	/* EntityCombustEvent */
	// EntityCreatePortalEvent
	// EntityDamageByBlockEvent
	/* EntityDeathEvent */
	// EntityEvent
	/* EntityInteractEvent */
	// EntityPortalEnterEvent
	// EntityPortalExitEvent
	/* EntityRegainHealthEvent */
	// EntityShootBowEvent
	/* EntityTameEvent */
	// EntityTargetEvent
	// EntityTargetLivingEntityEvent
	// EntityTeleportEvent
	/* EntityUnleashEvent */
	/* ExpBottleEvent */
	/* ExplosionPrimeEvent */
	/* FireworkExplodeEvent */
	// HorseJumpEvent
	// ItemDespawnEvent
	// ItemMergeEvent
	// ItemSpawnEvent
	/* PigZapEvent */
	/* PlayerLeashEntityEvent */
	/* PotionSplashEvent */
	/* ProjectileLaunchEvent */
	// SlimeSplitEvent

	// InventoryCloseEvent
	// InventoryDragEvent
	// InventoryEvent
	// InventoryInteractEvent

	// AsyncPlayerChatEvent
	// AsyncPlayerPreLoginEvent
	// PlayerAnimationEvent
	/*
	 * PlayerArmorStandManipulateEvent
	 * 
	 */
	// PlayerBedLeaveEvent
	// ****PlayerBucketEvent
	// PlayerChangedWorldEvent
	// PlayerChannelEvent
	/* PlayerChatEvent */
	// PlayerChatTabCompleteEvent
	// PlayerCommandPreprocessEvent
	/* PlayerEggThrowEvent */
	// PlayerEvent
	/* PlayerExpChangeEvent */
	// PlayerGameModeChangeEvent
	// ****PlayerInteractAtEntityEvent
	// PlayerInventoryEvent
	/* PlayerItemBreakEvent */
	/* PlayerItemConsumeEvent */
	// PlayerItemHeldEvent
	/* PlayerLevelChangeEvent */
	/* PlayerLoginEvent */
	// PlayerMoveEvent
	/* PlayerPortalEvent */
	// PlayerPreLoginEvent
	// PlayerRegisterChannelEvent
	// PlayerResourcePackStatusEvent
	/* PlayerRespawnEvent */
	/* PlayerTeleportEvent */
	// PlayerToggleFlightEvent
	// PlayerToggleSneakEvent
	// PlayerToggleSprintEvent
	/* PlayerUnleashEntityEvent */
	// PlayerUnregisterChannelEvent
	// PlayerVelocityEvent

	// MapInitializeEvent
	// PluginDisableEvent
	// PluginEnableEvent
	// PluginEvent
	// RemoteServerCommandEvent
	// ServerCommandEvent
	// ServerEvent
	// ServerListPingEvent
	// ServiceEvent
	// ServiceRegisterEvent
	// ServiceUnregisterEvent

	/* VehicleBlockCollisionEvent */
	/* VehicleCollisionEvent */
	/* VehicleCreateEvent */
	/* VehicleDamageEvent */
	/* VehicleDestroyEvent */
	/* VehicleEnterEvent */
	/* VehicleEntityCollisionEvent */
	// VehicleEvent
	/* VehicleExitEvent */
	/* VehicleMoveEvent */
	/* VehicleUpdateEvent */

	// ChunkEvent
	// ChunkLoadEvent
	// ChunkPopulateEvent
	// ChunkUnloadEvent
	// SpawnChangeEvent
	// WorldEvent
	// WorldInitEvent
	// WorldLoadEvent
	// WorldSaveEvent
	// WorldUnloadEvent

	public static Main plugin;
	public Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		plugin = this;
		registerEvent();
		this.getConfig();
		this.saveDefaultConfig();
		registerCommand();
		log.info("[GameCore] Version " + this.getDescription().getVersion() + " has been enabled.");
	}

	public void registerEvent() {
		getServer().getPluginManager().registerEvents(new BlockEvent(this), this);
		getServer().getPluginManager().registerEvents(new EnchantmentEvent(this), this);
		getServer().getPluginManager().registerEvents(new EntityEvent(this), this);
		getServer().getPluginManager().registerEvents(new HangingEvent(this), this);
		getServer().getPluginManager().registerEvents(new InventoryEvent(this), this);
		getServer().getPluginManager().registerEvents(new PlayerEvent(this), this);
		getServer().getPluginManager().registerEvents(new VehicleEvent(this), this);
		getServer().getPluginManager().registerEvents(new WeatherEvent(this), this);
		getServer().getPluginManager().registerEvents(new WorldEvent(this), this);
	}

	public void registerCommand() {
		getCommand("core").setExecutor(new Core(plugin));
		getCommand("tp").setExecutor(new Teleport(plugin));
		getCommand("tphere").setExecutor(new Teleport(plugin));
		getCommand("tpa").setExecutor(new Teleport(plugin));
		getCommand("tpahere").setExecutor(new Teleport(plugin));
		getCommand("tpaccept").setExecutor(new Teleport(plugin));
		getCommand("tpdeny").setExecutor(new Teleport(plugin));
		getCommand("reload").setExecutor(new Reload(plugin));
		getCommand("stop").setExecutor(new Stop(plugin));
	}

	public void onDisable() {

		List<String> deletes = plugin.getConfig().getStringList("Server.DeletePlayerData");
		for (String delete : deletes) {
			File path = new File(delete + "/playerdata");
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
		log.info("[GameCore] GameCore has been disabled.");
	}
}
