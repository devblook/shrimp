package team.devblook.shrimp.inject;

import org.bukkit.plugin.Plugin;
import team.unnamed.inject.AbstractModule;

public class PluginModule extends AbstractModule {

  private final Plugin plugin;

  public PluginModule(Plugin plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    bind(Plugin.class).toInstance(plugin);
  }
}
