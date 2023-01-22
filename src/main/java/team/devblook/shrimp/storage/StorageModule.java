package team.devblook.shrimp.storage;

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
            case "JSON":
                bind(Storage.class).to(JsonStorage.class);
                break;
            case "MYSQL":
                // TODO: Implement MySQL please :3
                break;
            case "MONGODB":
                // TODO: Implement MongoDB please :3
                break;
            default:
                throw new IllegalArgumentException("Storage type not found!");
        }
    }
}
