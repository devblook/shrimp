package team.devblook.shrimp.service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        plugin.getComponentLogger()
                .info(Component.
                        text("Starting Storage with " + storage.getClass().getSimpleName() + "...").color(NamedTextColor.GREEN));
        storage.connect();
    }

    @Override
    public void stop() {
        plugin.getComponentLogger().info(Component.
                text("Stopping Storage with " + storage.getClass().getSimpleName() + "...").color(NamedTextColor.RED));
    }
}
