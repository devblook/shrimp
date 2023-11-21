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

  private BukkitConfiguration settings;

  @Override
  public void start() {
    this.plugin.getLogger().info("Configuration load!");
  }

  @Override
  public void stop() {
    this.settings.save();
    this.messages.save();
    this.plugin.getLogger().info("Configuration saved!");
  }
}
