package team.devblook.shrimp.module;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.service.CommandService;
import team.devblook.shrimp.service.ConfigurationService;
import team.devblook.shrimp.service.Service;
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
        multibind(Service.class).asSet()
                .to(CommandService.class)
                .to(ConfigurationService.class);
        bind(BukkitConfiguration.class).toInstance(new BukkitConfiguration(plugin,"settings"));
        bind(BukkitConfiguration.class).named("players").toInstance(new BukkitConfiguration(plugin,"players"));
        install(new CommandModule());
    }
}
