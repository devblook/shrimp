package team.devblook.shrimp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import team.devblook.shrimp.inject.PluginModule;
import team.devblook.shrimp.service.Service;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;

import java.util.Set;

public class ShrimpPlugin extends JavaPlugin {

  @Inject
  private Set<Service> services;

  @Override
  public void onLoad() {
    Injector.create(new PluginModule(this))
      .injectMembers(this);
  }

  @Override
  public void onEnable() {
    this.getComponentLogger()
      .info(Component.text("Initializing ShrimpPlugin", TextColor.fromHexString("#6aafff")));

    this.services.forEach(Service::start);
  }

  @Override
  public void onDisable() {

    this.services.forEach(Service::stop);
  }
}

