package team.devblook.shrimp.service;

import org.bukkit.plugin.Plugin;
import team.unnamed.commandflow.CommandManager;
import team.unnamed.commandflow.annotated.AnnotatedCommandTreeBuilder;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.part.PartInjector;
import team.unnamed.commandflow.annotated.part.defaults.DefaultsModule;
import team.unnamed.commandflow.bukkit.factory.BukkitModule;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;

import java.util.Set;

public class CommandService implements Service {

  @Inject
  private Set<CommandClass> commandClasses;

  @Inject
  private Injector injector;

  @Inject
  private CommandManager commandManager;

  @Inject
  private Plugin plugin;

  @Override
  public void start() {
    final PartInjector partInjector = PartInjector.create();
    partInjector.install(new DefaultsModule());
    partInjector.install(new BukkitModule());

    final AnnotatedCommandTreeBuilder treeBuilder = AnnotatedCommandTreeBuilder.create(
      partInjector,
      ((clazz, parent) -> this.injector.getInstance(clazz))
    );

    this.commandClasses.forEach(command -> this.commandManager.registerCommands(treeBuilder.fromClass(command)));
  }

  @Override
  public void stop() {
    this.commandManager.unregisterAll();
  }
}
