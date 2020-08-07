package io.github.justentrix.lobby.command;

import io.github.justentrix.lobby.LobbyPlugin;
import io.github.justentrix.lobby.command.impl.BuildCommand;
import io.github.justentrix.lobby.command.impl.ClearChatCommand;
import io.github.justentrix.lobby.command.impl.PingCommand;

import java.util.Map;
import java.util.Objects;

import org.bukkit.entity.Player;

/**
 * @author justEntrix
 */
public class CommandHandler {

  private final LobbyPlugin plugin;
  private final Map<String, CommandCallback> commandsByName;

  public CommandHandler(LobbyPlugin plugin) {
    this.plugin = plugin;

    this.commandsByName = Map.of(
      "build", new BuildCommand(plugin),
      "ping", new PingCommand(plugin),
      "clearchat", new ClearChatCommand(plugin)
    );
  }

  public void initialize() {
    this.commandsByName.forEach((name, callback) -> {
      var command = Objects.requireNonNull(this.plugin.getCommand(name), String.format("Command \"%s\" is not contained in plugin.yml!", name));

      command.setExecutor((executor, cmd, label, arguments) -> {
        if (executor instanceof Player) {
          callback.execute((Player) executor, arguments);
        }
        else {
          executor.sendMessage(plugin.getMessage("noPlayer"));
        }
        return true;
      });
    });
  }

}
