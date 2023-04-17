package team.devblook.shrimp.service;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;

import javax.inject.Inject;

public class StorageService implements Service {
    @Inject
    private Shrimp plugin;
    @Inject
    private Storage storage;

    @Override
    public void start() {
        plugin.getLogger()
                .info("Starting Storage with " + storage.getClass().getSimpleName() + "...");
        storage.connect();
    }

    @Override
    public void stop() {
        plugin.getLogger().info("Stopping Storage with " + storage.getClass().getSimpleName() + "...");
    }
}
