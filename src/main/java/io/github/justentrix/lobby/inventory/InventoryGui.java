package io.github.justentrix.lobby.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author justEntrix
 */
public abstract class InventoryGui {

  private final String title;
  private Inventory bukkitInventory;
  private Map<ItemStack, Consumer<InventoryInteractEvent>> listeners;

  private InventoryGui(String title) {
    this.title = title;
    this.listeners = new HashMap<>();
  }

  protected InventoryGui(String title, InventoryType type) {
    this(title);
    this.bukkitInventory = Bukkit.createInventory(null, type);
  }

  protected InventoryGui(String title, int size) {
    this(title);
    this.bukkitInventory = Bukkit.createInventory(null, size);
  }

  public abstract void placeItems();

  protected void setItem(int slot, ItemStack itemStack, Consumer<InventoryInteractEvent> listener) {
    this.setItem(slot, itemStack);
    this.listeners.put(itemStack, listener);
  }

  protected void setItem(int slot, ItemStack itemStack) {
    this.bukkitInventory.setItem(slot, itemStack);
  }

  public Inventory getBukkitInventory() {
    return bukkitInventory;
  }

  public Map<ItemStack, Consumer<InventoryInteractEvent>> getListeners() {
    return listeners;
  }

  public String getTitle() {
    return title;
  }

}
