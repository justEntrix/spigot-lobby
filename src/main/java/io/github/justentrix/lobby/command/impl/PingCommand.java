package io.github.justentrix.lobby.command.impl;

import io.github.justentrix.lobby.LobbyPlugin;
import io.github.justentrix.lobby.command.CommandCallback;
import io.github.justentrix.lobby.utils.NmsHelper;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

/**
 * @author justEntrix
 */
public class PingCommand implements CommandCallback {

  private final LobbyPlugin plugin;

  public PingCommand(LobbyPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void execute(Player executor, String[] arguments) {
    executor.sendMessage(String.format(this.plugin.getMessage("ping"), this.detectPing(executor)));
  }

  public int detectPing(Player player) {
    try {
      var craftPlayer = NmsHelper.toCraftPlayer(player);
      var getHandle = craftPlayer.getClass().getMethod("getHandle");

      var entityPlayer = getHandle.invoke(craftPlayer);
      var ping = entityPlayer.getClass().getDeclaredField("ping");

      return ping.getInt(entityPlayer);
    } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return -1;
    }
  }
}
