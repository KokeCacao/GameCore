package com.kokicraft.GameCore.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.kokicraft.GameCore.Main;

public class InventoryEvent implements Listener {

  public Main plugin;

  public InventoryEvent(Main instance) {
    plugin = instance;
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInventoryOpen(InventoryOpenEvent event) {
    Player player = null;
    if ((event.getPlayer() instanceof Player)) {
      player = (Player) event.getPlayer();
    } else {
      return;
    }
    if (event.getInventory().getHolder() == null || event.getInventory().getHolder() == player) {
      return;
    }
    switch (event.getInventory().getType()) {
      // 1.19
      case CHISELED_BOOKSHELF:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.chiseledbookshelf"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case COMPOSTER:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.composter"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case SMITHING:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.smithing"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      // 1.14
      case BARREL:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.barrel"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case BLAST_FURNACE:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.blastfurnace"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case CARTOGRAPHY:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.cartography"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case GRINDSTONE:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.grindstone"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case LECTERN:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.lectern"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case LOOM:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.loom"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case SHULKER_BOX:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.shulkerbox"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case SMOKER:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.smoker"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case STONECUTTER:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.stonecutter"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      // legacy
      case ANVIL:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.anvil"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case BEACON:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.beacon"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case BREWING:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.brewing"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case CHEST:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.chest"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case CRAFTING:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.crafting"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case DISPENSER:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.dispenser"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case DROPPER:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.dropper"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case ENCHANTING:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.enchanting"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case ENDER_CHEST:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.enderchest"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case HOPPER:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.hopper"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case FURNACE:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.furnace"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case WORKBENCH:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.workbench"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case CREATIVE:
        break;
      case MERCHANT:
        if ((!plugin.getConfig().getBoolean(player.getWorld().getName() + ".Open.villager"))
            && (!player.isOp())) {
          event.setCancelled(true);
        }
        break;
      case PLAYER:
        break;
    }
    return;
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    // edit|drop own?
    // edit|drop handler = null?
    // edit|drop handler != player?
    // Player p = (Player) event.getWhoClicked();
    // ArrayList<Inventory> editedInventory = new ArrayList<>();
    // boolean interactOutSide;
    // boolean interactMouse;
    // switch (event.getAction()) {
    // case CLONE_STACK:
    // break;
    // case COLLECT_TO_CURSOR:
    // event.setCancelled(true);
    // break;
    // case DROP_ALL_CURSOR:
    // break;
    // case DROP_ALL_SLOT:
    // break;
    // case DROP_ONE_CURSOR:
    // break;
    // case DROP_ONE_SLOT:
    // break;
    // case HOTBAR_MOVE_AND_READD:
    // break;
    // case HOTBAR_SWAP:
    // break;
    // case MOVE_TO_OTHER_INVENTORY:
    // break;
    // case NOTHING:
    // return;
    // case PICKUP_ALL:
    // break;
    // case PICKUP_HALF:
    // break;
    // case PICKUP_ONE:
    // break;
    // case PICKUP_SOME:
    // break;
    // case PLACE_ALL:
    // break;
    // case PLACE_ONE:
    // break;
    // case PLACE_SOME:
    // break;
    // case SWAP_WITH_CURSOR:
    // break;
    // case UNKNOWN:
    // break;
    // }
    // switch (event.getClick()) {
    // case CONTROL_DROP:
    // //Holding Ctrl while pressing the "Drop" key (defaults to Q).
    // editedInventory.add(event.getClickedInventory());
    // interactOutSide = true;
    // break;
    // case CREATIVE:
    // //Any action done with the Creative inventory open.
    // break;
    // case DOUBLE_CLICK:
    // //Pressing the left mouse button twice in quick succession.
    // if (p.getOpenInventory().getTopInventory() != null) {
    // editedInventory.add(p.getOpenInventory().getTopInventory());
    // }
    // if (p.getOpenInventory().getBottomInventory() != null) {
    // editedInventory.add(p.getOpenInventory().getBottomInventory());
    // }
    // interactMouse = true;
    // break;
    // case DROP:
    // //The "Drop" key (defaults to Q).
    // editedInventory.add(event.getClickedInventory());
    // interactOutSide = true;
    // break;
    // case LEFT:
    // //The left (or primary) mouse button.
    // editedInventory.add(event.getClickedInventory());
    // interactMouse = true;
    // break;
    // case MIDDLE:
    // //The middle mouse button, or a "scrollwheel click".
    // event.setCancelled(true);
    // break;
    // case NUMBER_KEY:
    // //One of the number keys 1-9, correspond to slots on the hotbar.
    // Integer key = event.getHotbarButton();
    // editedInventory.add(event.getClickedInventory());
    // editedInventory.add(p.getInventory());
    // break;
    // case RIGHT:
    // //The right mouse button.
    // editedInventory.add(event.getClickedInventory());
    // interactMouse = true;
    // break;
    // case SHIFT_LEFT:
    // //Holding shift while pressing the left mouse button.
    // if (p.getOpenInventory().getTopInventory() != null) {
    // editedInventory.add(p.getOpenInventory().getTopInventory());
    // }
    // if (p.getOpenInventory().getBottomInventory() != null) {
    // editedInventory.add(p.getOpenInventory().getBottomInventory());
    // }
    // break;
    // case SHIFT_RIGHT:
    // //Holding shift while pressing the right mouse button.
    // if (p.getOpenInventory().getTopInventory() != null) {
    // editedInventory.add(p.getOpenInventory().getTopInventory());
    // }
    // if (p.getOpenInventory().getBottomInventory() != null) {
    // editedInventory.add(p.getOpenInventory().getBottomInventory());
    // }
    // break;
    // case UNKNOWN:
    // //A type of inventory manipulation not yet recognized by Bukkit.
    // break;
    // case WINDOW_BORDER_LEFT:
    // //Clicking the left mouse button on the grey area around the inventory.
    // if ((p.getItemOnCursor() != null) && (p.getItemOnCursor().getType() !=
    // Material.AIR)) {
    // interactOutSide = true;
    // }
    // break;
    // case WINDOW_BORDER_RIGHT:
    // //Clicking the right mouse button on the grey area around the inventory.
    // if ((p.getItemOnCursor() != null) && (p.getItemOnCursor().getType() !=
    // Material.AIR)) {
    // interactOutSide = true;
    // }
    // break;
    // }
    //
    //
    if ((plugin.getConfig().getBoolean(event.getWhoClicked().getWorld().getName() + ".Inventory.NoClick"))
        && (!event.getWhoClicked().isOp())) {
      event.setCancelled(true);
      ((Player) event.getWhoClicked()).updateInventory();
      return;
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onCraftItem(CraftItemEvent e) {
    if ((plugin.getConfig().getBoolean(e.getWhoClicked().getWorld().getName() + ".Inventory.NoCraft"))
        && (!e.getWhoClicked().isOp())) {
      e.setCancelled(true);
      ((Player) e.getWhoClicked()).updateInventory();
      return;
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBrew(BrewEvent event) {
    if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".Inventory.Furnace.NoBrew")) {
      event.setCancelled(true);
      return;
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onFurnaceBurn(FurnaceBurnEvent event) {
    if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".Inventory.Furnace.NoBurn")) {
      event.setCancelled(true);
      return;
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onFurnaceExtract(FurnaceExtractEvent event) {
    if (plugin.getConfig().getBoolean(event.getBlock().getWorld().getName() + ".Inventory.Furnace.NoExp")) {
      event.setExpToDrop(0);
      return;
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInventoryCreative(InventoryCreativeEvent event) {
    if ((plugin.getConfig().getBoolean(event.getWhoClicked().getWorld().getName() + ".Inventory.NoCreative"))
        && (!event.getWhoClicked().isOp())) {
      event.setCancelled(true);
      return;
    }
  }

  // @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  // public void onInventoryPickupItem(InventoryPickupItemEvent event) {
  // if (plugin.getConfig().getBoolean(event.getItem().getWorld().getName() +
  // ".Inventory.NoHopper")) {
  // event.getItem().remove();
  // event.setCancelled(true);
  // return;
  // }
  // }
}
