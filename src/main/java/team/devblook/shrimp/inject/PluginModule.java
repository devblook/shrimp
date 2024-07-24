package team.devblook.shrimp.inject;

import org.bukkit.plugin.Plugin;
import team.devblook.shrimp.inject.command.CommandModule;
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
    bind(Plugin.class).toInstance(plugin);

    this.install(new CommandModule());
    this.install(new RepositoryModule());
    this.install(new ServiceModule());
  }
}
