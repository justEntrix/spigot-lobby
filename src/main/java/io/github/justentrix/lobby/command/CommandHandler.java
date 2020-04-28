package io.github.justentrix.lobby.command;

import io.github.justentrix.lobby.LobbyPlugin;
import io.github.justentrix.lobby.command.impl.BuildCommand;
import io.github.justentrix.lobby.command.impl.ClearChatCommand;
import io.github.justentrix.lobby.command.impl.PingCommand;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 * @author justEntrix
 */
public class CommandHandler {

  private final LobbyPlugin plugin;
  private final Map<String, CommandCallback> commandsByName;

  public CommandHandler(LobbyPlugin plugin) {
    this.plugin = plugin;

    this.commandsByName = new HashMap<>() {{
      put("build", new BuildCommand(plugin));
      put("ping", new PingCommand(plugin));
      put("clearchat", new ClearChatCommand(plugin));
    }};
  }

  public void initialize() {
    this.commandsByName.forEach((name, callback) -> {
      this.plugin.getCommand(name).setExecutor((executor, command, label, arguments) -> {
        if (!(executor instanceof Player)) {
          executor.sendMessage(plugin.getMessage("noPlayer"));
          return true;
        }
        callback.execute((Player) executor, arguments);
        return true;
      });
    });
  }

}
