package team.devblook.shrimp.storage.local;

import com.google.gson.Gson;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.user.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class JsonStorage implements Storage {

  private static final Gson GSON = new Gson();
  private static final Executor EXECUTOR = Executors.newCachedThreadPool();
  private final File folder;
  private final Shrimp plugin;

  @Inject
  public JsonStorage(Shrimp plugin) {
    this.plugin = plugin;
    this.folder = new File(plugin.getDataFolder(), "users");
    if (!this.folder.exists() && !this.folder.mkdirs()) {
      throw new RuntimeException("Could not create folder " + this.folder.getAbsolutePath());
    }
  }

  @Override
  public void save(User user) {
    CompletableFuture.runAsync(() -> {
      File file = new File(this.folder, user.id() + ".json");

      try (Writer writer = new BufferedWriter(new FileWriter(file))) {
        if (!file.exists() && !file.createNewFile()) {
          throw new IOException("Could not create file " + file.getAbsolutePath());
        }
        GSON.toJson(user, writer);
      } catch (IOException e) {
        this.plugin.getSLF4JLogger().error("Could not save user " + user.id(), e);
      }
    }, EXECUTOR);
  }

  @Override
  public User find(String id) {
    return CompletableFuture.supplyAsync(() -> {
      File file = new File(this.folder, id + ".json");

      try (Reader reader = new BufferedReader(new FileReader(file))) {
        return GSON.fromJson(reader, User.class);
      } catch (IOException e) {
        this.plugin.getSLF4JLogger().info("Create new user " + id);
      }
      return null;
    }, EXECUTOR).join();
  }
}
