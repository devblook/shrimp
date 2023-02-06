package team.devblook.shrimp.module;

import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.storage.cloud.MongoStorage;
import team.devblook.shrimp.storage.cloud.MySQLStorage;
import team.devblook.shrimp.storage.local.JsonStorage;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.AbstractModule;

import java.util.Locale;

public class StorageModule extends AbstractModule {

    private final BukkitConfiguration settings;

    public StorageModule(BukkitConfiguration settings) {
        this.settings = settings;
    }

    @Override
    public void configure() {
        String typeStorage = settings.get().getString("storage.type", "JSON").toUpperCase(Locale.ROOT);

        switch (typeStorage) {
            case "JSON" -> bind(Storage.class).to(JsonStorage.class);
            case "MYSQL" -> bind(Storage.class).to(MySQLStorage.class);
            case "MONGODB" -> bind(Storage.class).to(MongoStorage.class);
            default -> throw new IllegalArgumentException("Storage type not found!");
        }
    }
}
