package team.devblook.shrimp.module;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.service.CommandService;
import team.devblook.shrimp.service.Service;
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
                .to(CommandService.class);
        install(new CommandModule());
    }
}
