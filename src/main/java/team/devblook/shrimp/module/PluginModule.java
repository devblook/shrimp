package team.devblook.shrimp.module;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.listener.ListenerModule;
import team.devblook.shrimp.listener.ListenerService;
import team.devblook.shrimp.service.CommandService;
import team.devblook.shrimp.service.ConfigurationService;
import team.devblook.shrimp.service.Service;
import team.devblook.shrimp.storage.StorageModule;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.AbstractModule;

public class PluginModule extends AbstractModule {
    private Shrimp plugin;

    public PluginModule(Shrimp plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(Shrimp.class).toInstance(plugin);
        multibind(Service.class)
                .asSet()
                .to(ListenerService.class)
                .to(ConfigurationService.class)
                .to(CommandService.class);

        BukkitConfiguration settings = new BukkitConfiguration(plugin, "settings");
        bind(BukkitConfiguration.class)
                .toInstance(settings);
        bind(BukkitConfiguration.class)
                .named("players")
                .toInstance(new BukkitConfiguration(plugin, "players"));
        bind(BukkitConfiguration.class)
                .named("messages")
                .toInstance(new BukkitConfiguration(plugin, "messages"));

        install(new CommandModule());
        install(new StorageModule(settings));
        install(new ReferenceModule());
        install(new ListenerModule());
    }
}
