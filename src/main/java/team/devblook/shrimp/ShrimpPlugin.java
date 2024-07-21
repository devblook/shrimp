package team.devblook.shrimp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import team.devblook.shrimp.inject.PluginModule;
import team.unnamed.inject.Injector;

public class ShrimpPlugin extends JavaPlugin {

  @Override
  public void onLoad() {
    Injector.create(new PluginModule(this))
      .injectMembers(this);
  }

  @Override
  public void onEnable() {
    this.getComponentLogger().info(Component.text("Initializing ShrimpPlugin", TextColor.fromHexString("#6aafff")));
  }

  @Override
  public void onDisable() {
  }
}

