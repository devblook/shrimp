package team.devblook.shrimp.service;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class ConfigurationService implements Service {

    private Shrimp plugin;
    @Named("messages")
    private BukkitConfiguration messages;

    @Named("players")
    private BukkitConfiguration players;

    private BukkitConfiguration settings;

    @Override
    public void start() {
        plugin.getLogger().info("Configuration load!");
    }

    @Override
    public void stop() {
        players.save();
        settings.save();
        messages.save();
        plugin.getLogger().info("Configuration saved!");
    }
}
