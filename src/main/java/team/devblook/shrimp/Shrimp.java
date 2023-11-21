package team.devblook.shrimp;

import org.bukkit.plugin.java.JavaPlugin;
import team.devblook.shrimp.module.PluginModule;
import team.devblook.shrimp.service.Service;
import team.devblook.shrimp.user.UserHandler;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.util.Set;

public class Shrimp extends JavaPlugin {

  @Inject
  private Set<Service> services;
  @Inject
  private UserHandler userHandler;

  @Override
  public void onLoad() {
    Injector injector = Injector.create(new PluginModule(this));
    injector.injectMembers(this);

  }

  @Override
  public void onEnable() {
    this.services.forEach(Service::start);
  }

  @Override
  public void onDisable() {
    this.services.forEach(Service::stop);
    this.userHandler.saveAll();
  }
}

