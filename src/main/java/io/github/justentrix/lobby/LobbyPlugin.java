package io.github.justentrix.lobby;

import io.github.justentrix.lobby.command.CommandHandler;
import io.github.justentrix.lobby.listeners.LobbyListeners;
import io.github.justentrix.lobby.utils.JsonDocument;
import java.io.IOException;
import java.nio.file.Paths;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

/**
 * @author justEntrix
 */
public class LobbyPlugin extends JavaPlugin {

  private JsonDocument messagesDocument;

  private LobbyListeners listeners;

  private CommandHandler commandHandler;

  @Override
  public void onLoad() {
    super.saveResource("messages.json", false);

    try {
      this.messagesDocument = JsonDocument.of(Paths.get(this.getDataFolder().getPath(), "messages.json"));
    } catch (IOException failure) {
      failure.printStackTrace();
    }
  }

  @Override
  public void onEnable() {
    this.getLogger().info("Plugin enabled successfully.");
    this.listeners = new LobbyListeners(this);
    this.listeners.initialize();

    this.commandHandler = new CommandHandler(this);
    this.commandHandler.initialize();
  }

  public JsonDocument getMessagesDocument() {
    return messagesDocument;
  }

  public String getPrefix() {
    return this.messagesDocument.getJson().get("prefix").getAsString();
  }

  public String getMessage(String messageKey) {
    return this.getPrefix() + " " + this.messagesDocument.getJson().get(messageKey).getAsString();
  }

  public <T extends Event> void registerEvent(Class<T> clazz, Consumer<T> consumer) {
    EventExecutor executor = (listener, event) -> consumer.accept((T) event);

    this.getServer().getPluginManager()
        .registerEvent(clazz, new Listener() {}, EventPriority.NORMAL, executor, this);
  }
}
