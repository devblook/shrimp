package team.devblook.shrimp.inject.database;

import org.bukkit.plugin.Plugin;
import team.devblook.shrimp.BukkitConfiguration;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.storage.impl.LocalStorage;
import team.devblook.shrimp.storage.impl.MongoStorage;
import team.devblook.shrimp.storage.impl.MySqlStorage;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;
import team.unnamed.inject.Singleton;

import java.util.Locale;

public class DatabaseModule extends AbstractModule {

  @Singleton
  @Provides
  public Storage storageProvider(BukkitConfiguration config, Plugin plugin) {
    final String type = config.get()
                          .getString("storage-type", "JSON");
    return switch (type.toUpperCase(Locale.ROOT)) {
      case "MYSQL" -> new MySqlStorage(plugin.getComponentLogger());
      case "MONGO" -> new MongoStorage(plugin.getComponentLogger(), "mongodb://localhost:27017", "shrimp");
      default -> new LocalStorage(plugin);
    };
  }
}
