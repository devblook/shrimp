package team.devblook.shrimp.service;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

public class ConfigurationService implements Service{
    @Inject
    private Shrimp plugin;
    @Inject
    @Named("players")
    private BukkitConfiguration players;
    @Inject
    private BukkitConfiguration settings;
    @Override
    public void start() {
        //empty
    }

    @Override
    public void stop() {
        players.save();
        settings.save();
        plugin.getLogger().info("Configuration saved!");
    }
}
