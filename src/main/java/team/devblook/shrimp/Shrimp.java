package team.devblook.shrimp;

import org.bukkit.plugin.java.JavaPlugin;
import team.devblook.shrimp.module.PluginModule;
import team.devblook.shrimp.service.Service;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

public class Shrimp extends JavaPlugin {
    @Inject
    private Set<Service> services;

    @Override
    public void onLoad() {
        Injector injector = Injector.create(new PluginModule(this));
        injector.injectMembers(this);
    }

    @Override
    public void onEnable() {
        services.forEach(Service::start);
        getLogger().info("<yellow>Shrimp has been enabled!");
    }

    @Override
    public void onDisable() {
        services.forEach(Service::stop);
        getLogger().info("<red>Shrimp has been disabled!");
    }
}

