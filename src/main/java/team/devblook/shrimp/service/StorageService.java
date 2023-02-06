package team.devblook.shrimp.service;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.storage.cloud.MongoStorage;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;

public class StorageService implements Service{
    @Inject
    private Shrimp plugin;
    @Inject
    private BukkitConfiguration settings;
    @Override
    public void start() {
        final Storage storage = new MongoStorage(settings, plugin);
        storage.connect();
    }
    @Override
    public void stop() {
        plugin.getLogger().info("Stopping Storage Service...");
    }
}
