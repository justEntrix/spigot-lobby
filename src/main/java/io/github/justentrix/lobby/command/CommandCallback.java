package io.github.justentrix.lobby.command;

import org.bukkit.entity.Player;

/**
 * @author justEntrix
 */
public interface CommandCallback {

  void execute(Player executor, String[] arguments);
}
