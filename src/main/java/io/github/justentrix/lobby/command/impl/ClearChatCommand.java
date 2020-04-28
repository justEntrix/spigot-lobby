package io.github.justentrix.lobby.command.impl;

import io.github.justentrix.lobby.LobbyPlugin;
import io.github.justentrix.lobby.command.CommandCallback;
import org.bukkit.entity.Player;

/**
 * @author justEntrix
 */
public class ClearChatCommand implements CommandCallback {

  private final LobbyPlugin plugin;

  public ClearChatCommand(LobbyPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void execute(Player executor, String[] arguments) {
    for (int i = 0; i < 100; i++)
      this.plugin.getServer().broadcastMessage("");

    var message = String.format(this.plugin.getMessage("chatCleared"), executor.getDisplayName());
    this.plugin.getServer().broadcastMessage(message);
  }
}
