package team.devblook.shrimp.inject;

import org.bukkit.plugin.Plugin;
import team.devblook.shrimp.inject.command.CommandModule;
import team.devblook.shrimp.inject.configuration.ConfigurationModule;
import team.devblook.shrimp.inject.database.DatabaseModule;
import team.devblook.shrimp.inject.listener.ListenerModule;
import team.devblook.shrimp.inject.repository.RepositoryModule;
import team.devblook.shrimp.inject.service.ServiceModule;
import team.unnamed.inject.AbstractModule;

public class PluginModule extends AbstractModule {

  private final Plugin plugin;

  public PluginModule(Plugin plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    bind(Plugin.class).toInstance(this.plugin);

    this.install(new ConfigurationModule());
    this.install(new CommandModule());
    this.install(new RepositoryModule());
    this.install(new ServiceModule());
    this.install(new DatabaseModule());
    this.install(new ListenerModule());
  }
}
