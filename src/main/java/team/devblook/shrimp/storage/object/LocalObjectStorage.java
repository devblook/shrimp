package team.devblook.shrimp.storage.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import team.devblook.shrimp.model.Model;
import team.devblook.shrimp.storage.ObjectStorage;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.storage.adapt.InstantAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalObjectStorage<T extends Model> implements ObjectStorage<T> {

  private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
  private static final Gson GSON = new GsonBuilder()
                                     .registerTypeAdapter(Instant.class, new InstantAdapter())
                                     .setPrettyPrinting()
                                     .create();
  private final Class<T> clazz;
  private final Storage storage;
  private final ComponentLogger logger;

  public LocalObjectStorage(ComponentLogger logger, Storage storage, Class<T> clazz) {
    this.clazz = clazz;
    this.storage = storage;
    this.logger = logger;
  }

  @Override
  public T findSync(final String uuid) {
    final File file = new File(this.storage.getFolder(), uuid + ".json");
    if (!file.exists()) {
      return null;
    }

    try (final FileReader reader = new FileReader(file)) {
      return GSON.fromJson(reader, clazz);
    } catch (IOException e) {
      this.logger.error("Failed to read file", e);
    }

    return null;
  }

  @Override
  public T findAsync(final String uuid) {
    return CompletableFuture.supplyAsync(() -> this.findSync(uuid), EXECUTOR)
             .exceptionally(e -> {
               this.logger.error("Failed to find object", e);
               return null;
             })
             .whenComplete((result, __) -> {
               if (result == null) {
                 this.logger.error("Failed to find object");
               }
             })
             .join();
  }

  @Override
  public Optional<T> findOptionalSync(final String uuid) {
    return Optional.ofNullable(this.findSync(uuid));
  }

  @Override
  public Optional<T> findOptionalAsync(final String uuid) {
    return Optional.ofNullable(this.findAsync(uuid));
  }

  @Override
  public void saveSync(final T object) {
    final File file = new File(this.storage.getFolder(), object.getId() + ".json");

    try (FileWriter writer = new FileWriter(file)) {
      GSON.toJson(object, writer);
    } catch (Exception e) {
      this.logger.error("Failed to save object", e);
    }
  }

  @Override
  public void saveAsync(final T object) {
    CompletableFuture.runAsync(() -> this.saveSync(object), EXECUTOR)
      .exceptionally(e -> {
        this.logger.error("Failed to save object", e);
        return null;
      })
      .whenComplete((__, e) -> {
        if (e != null) {
          this.logger.error("Failed to save object");
        }
      });
  }

  @Override
  public void deleteSync(final String uuid) {
    final File file = new File(this.storage.getFolder(), uuid + ".json");
    if (!file.exists()) {
      this.logger.error("File does not exist");
      return;
    }

    if (!file.delete()) {
      this.logger.error("Failed to delete file");
    }
  }
}
