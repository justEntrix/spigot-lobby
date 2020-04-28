package io.github.justentrix.lobby.inventory;

import io.github.justentrix.lobby.LobbyPlugin;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

/**
 * @author justEntrix
 */
public class InventoryGuiContainer implements Consumer<InventoryClickEvent> {

  private final LobbyPlugin plugin;

  private Collection<InventoryGui> guis;

  public InventoryGuiContainer(LobbyPlugin plugin) {
    this.plugin = plugin;
    this.guis = new HashSet<>();
    this.plugin.registerEvent(InventoryClickEvent.class, this);
  }

  public void registerGui(InventoryGui gui) {
    this.guis.add(gui);
  }

  public void deregisterGui(InventoryGui gui) {
    this.guis.remove(gui);
  }

  // TODO: Must be tested.
  @Override
  public void accept(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player))
      return;
    if (event.getCurrentItem() == null)
      return;

    var inventoryStream = this.guis.stream().map(InventoryGui::getBukkitInventory);

    if (inventoryStream.noneMatch(event.getInventory()::equals))
      return;

    var optionalListeners = this.guis.stream()
        .filter((gui) -> event.getInventory().equals(gui.getBukkitInventory()))
        .findFirst()
        .map(InventoryGui::getListeners);
    if (optionalListeners.isEmpty())
      return;

    optionalListeners.get().forEach((item, listener) -> {
      if (item.equals(event.getCurrentItem()))
        listener.accept(event);
    });
  }

}
