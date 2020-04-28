package io.github.justentrix.lobby.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author justEntrix
 */
public class JsonDocument {

  private final Path file;
  private final Gson gson;

  private JsonObject json;

  private JsonDocument(Path file) throws IOException {
    this.file = file;
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.load();
  }

  public void save() throws IOException {
    Files.write(this.file, this.gson.toJson(this.json).getBytes());
  }

  private void load() throws IOException {
    String content = Files.readString(this.file);
    this.json = new JsonParser().parse(content).getAsJsonObject();
  }

  public JsonObject getJson() {
    return this.json;
  }

  public static JsonDocument of(Path file) throws IOException {
    return new JsonDocument(file);
  }

}
