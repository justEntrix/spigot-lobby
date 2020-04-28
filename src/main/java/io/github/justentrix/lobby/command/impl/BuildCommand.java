package io.github.justentrix.lobby.command.impl;

import io.github.justentrix.lobby.LobbyPlugin;
import io.github.justentrix.lobby.command.CommandCallback;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author justEntrix
 */
public class BuildCommand implements CommandCallback {

  private final LobbyPlugin plugin;
  private final String permission;

  private boolean canBuild = true;

  public BuildCommand(LobbyPlugin plugin) {
    this.plugin = plugin;
    this.permission = "lobby.build";

    this.plugin.registerEvent(BlockPlaceEvent.class, this::blockPlaced);
    this.plugin.registerEvent(BlockBreakEvent.class, this::blockBroke);
  }

  private void blockPlaced(BlockPlaceEvent event) {
    if (!this.canBuild && !event.getPlayer().hasPermission(this.permission)) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(this.plugin.getMessage("cannotBuild"));
    }
  }

  private void blockBroke(BlockBreakEvent event) {
    if (!this.canBuild && !event.getPlayer().hasPermission(this.permission)) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(this.plugin.getMessage("cannotBuild"));
    }
  }

  @Override
  public void execute(Player executor, String[] arguments) {
    this.canBuild = !this.canBuild;
    executor.sendMessage(this.plugin.getMessage((this.canBuild) ? "buildModeEnabled" : "buildModeDisabled"));
  }

}
