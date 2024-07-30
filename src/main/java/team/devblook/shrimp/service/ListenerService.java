package team.devblook.shrimp.service;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.Inject;

import java.util.Set;

public class ListenerService implements Service {

  @Inject
  private Set<Listener> listeners;

  @Inject
  private Plugin plugin;

  public void start() {
    listeners.forEach(listener -> plugin.getServer()
                                    .getPluginManager()
                                    .registerEvents(listener, plugin));
  }

  public void stop() {
    HandlerList.unregisterAll();
  }
}
