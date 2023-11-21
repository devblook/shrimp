package team.devblook.shrimp.module;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.service.*;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.AbstractModule;

import java.util.logging.Logger;

public class PluginModule extends AbstractModule {
  private final Shrimp plugin;

  public PluginModule(Shrimp plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    Logger logger = this.plugin.getLogger();
    this.bind(Shrimp.class).toInstance(this.plugin);
    this.bind(Logger.class).toInstance(logger);
    this.multibind(Service.class)
            .asSet()
            .to(ListenerService.class)
            .to(ConfigurationService.class)
            .to(CommandService.class)
            .to(StorageService.class);

    BukkitConfiguration settings = new BukkitConfiguration(this.plugin, "settings");
    this.bind(BukkitConfiguration.class)
            .toInstance(settings);
    this.bind(BukkitConfiguration.class)
            .named("messages")
            .toInstance(new BukkitConfiguration(this.plugin, "messages"));

    this.install(new CommandModule());
    this.install(new StorageModule(settings, this.plugin));
    this.install(new ReferenceModule());
    this.install(new ListenerModule());
  }
}
