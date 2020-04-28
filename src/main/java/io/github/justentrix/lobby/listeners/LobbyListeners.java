package io.github.justentrix.lobby.listeners;

import com.destroystokyo.paper.Title;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.justentrix.lobby.LobbyPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.command.UnknownCommandEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.util.Consumer;

/**
 * @author justEntrix
 */
public class LobbyListeners {

  private final LobbyPlugin plugin;

  private final JsonObject joinSettings;

  public LobbyListeners(LobbyPlugin plugin) {
    this.plugin = plugin;
    this.joinSettings = this.plugin.getMessagesDocument().getJson().get("joinSettings").getAsJsonObject();
  }

  private <T extends Event> void registerEvent(Class<T> clazz, Consumer<T> consumer) {
    EventExecutor executor = (listener, event) -> consumer.accept((T) event);

    this.plugin.getServer().getPluginManager()
        .registerEvent(clazz, new Listener() {}, EventPriority.NORMAL, executor, this.plugin);
  }

  public void initialize() {
    this.registerEvent(PlayerQuitEvent.class, (event) -> {
      var quitMessage = this.joinSettings.get("quitMessage").getAsString();
      event.setQuitMessage(String.format(quitMessage, event.getPlayer().getDisplayName()));
    });

    this.registerEvent(PlayerJoinEvent.class, (event) -> {
      var joinMessage = this.joinSettings.get("joinMessage").getAsString();
      event.setJoinMessage(String.format(joinMessage, event.getPlayer().getDisplayName()));

      if (!this.joinSettings.get("joinTitle").getAsBoolean())
        return;

      var title = this.joinSettings.get("title").getAsString();
      var subTitle = this.joinSettings.get("subTitle").getAsString();

      var titleComponent = new TextComponent(title);
      var subTitleComponent = new TextComponent(subTitle);

      event.getPlayer().sendTitle(new Title(titleComponent, subTitleComponent));
    });
    this.registerEvent(UnknownCommandEvent.class, (event) -> {
      event.setMessage(String.format(this.plugin.getMessage("unknownCommand"), event.getCommandLine()));
    });
  }

}
