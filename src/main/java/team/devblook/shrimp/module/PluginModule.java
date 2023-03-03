package team.devblook.shrimp.module;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.service.*;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.AbstractModule;

public class PluginModule extends AbstractModule {
    private final Shrimp plugin;

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
                .to(CommandService.class)
                .to(StorageService.class);

        BukkitConfiguration settings = new BukkitConfiguration(plugin, "settings");
        bind(BukkitConfiguration.class)
                .toInstance(settings);
        bind(BukkitConfiguration.class)
                .named("messages")
                .toInstance(new BukkitConfiguration(plugin, "messages"));

        install(new CommandModule());
        install(new StorageModule(settings,plugin));
        install(new ReferenceModule());
        install(new ListenerModule());
    }
}
