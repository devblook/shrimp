package team.devblook.shrimp.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.service.Service;

import javax.inject.Inject;
import java.util.Set;

public class ListenerService implements Service {

    @Inject
    private Set<Listener> listeners;
    @Inject
    private Shrimp plugin;

    @Override
    public void start() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }
}
