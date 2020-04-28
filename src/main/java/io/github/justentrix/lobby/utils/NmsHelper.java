package io.github.justentrix.lobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author justEntrix
 */
public class NmsHelper {

  public static String getServerVersion() {
    var serverName  = Bukkit.getServer().getClass().getPackage().getName();
    var split = serverName.split("\\.");

    return split[split.length - 1];
  }

  public static Object toCraftPlayer(Player player) {
    try {
      var clazz = Class.forName(String.format("org.bukkit.craftbukkit.%s.entity.CraftPlayer", getServerVersion()));
      return clazz.cast(player);
    } catch (ClassNotFoundException failure) {
      failure.printStackTrace();
      return null;
    }
  }


}
