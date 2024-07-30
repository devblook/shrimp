package team.devblook.shrimp.inject.configuration;

import org.bukkit.plugin.Plugin;
import team.devblook.shrimp.BukkitConfiguration;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Named;
import team.unnamed.inject.Provides;
import team.unnamed.inject.Singleton;

public class ConfigurationModule extends AbstractModule {

  @Singleton
  @Provides
  public BukkitConfiguration configProviders(Plugin plugin) {
    return new BukkitConfiguration(plugin, "settings.yml");
  }

  @Singleton
  @Provides
  @Named("messages")
  public BukkitConfiguration messagesProviders(Plugin plugin) {
    return new BukkitConfiguration(plugin, "messages.yml");
  }
}
