package team.devblook.shrimp.inject.command;

import bukkit.BukkitMapCommandManager;
import org.bukkit.plugin.Plugin;
import team.devblook.shrimp.command.MainCommand;
import team.devblook.shrimp.command.home.DelHomeCommand;
import team.devblook.shrimp.command.home.HomeCommand;
import team.devblook.shrimp.command.home.SetHomeCommand;
import team.unnamed.commandflow.CommandManager;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;
import team.unnamed.inject.Singleton;

public class CommandModule extends AbstractModule {

  @Singleton
  @Provides
  public CommandManager providesCommandManager(Plugin plugin) {
    return new BukkitMapCommandManager(plugin);
  }

  @Override
  protected void configure() {
    this.multibind(CommandClass.class)
      .asSet()
      .to(MainCommand.class)
      .to(HomeCommand.class)
      .to(SetHomeCommand.class)
      .singleton();
  }
}
