package team.devblook.shrimp.service;

import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

import javax.inject.Inject;
import java.util.Set;

public class CommandService implements Service {
    @Inject
    private Set<CommandClass> commands;

    private CommandManager commandManager;

    @Override
    public void start() {
        commandManager = new BukkitCommandManager("Shrimp");
        PartInjector partInjector = PartInjector.create();
        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());
        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        commands.forEach(command -> commandManager.registerCommands(builder.fromClass(command)));
    }
    @Override
    public void stop() {
        commandManager.unregisterAll();
    }


}
