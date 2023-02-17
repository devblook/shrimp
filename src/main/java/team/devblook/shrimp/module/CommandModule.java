package team.devblook.shrimp.module;

import me.fixeddev.commandflow.annotated.CommandClass;
import team.devblook.shrimp.command.MainCommand;
import team.devblook.shrimp.command.home.DeleteHomeCommand;
import team.devblook.shrimp.command.home.HomeCommand;
import team.devblook.shrimp.command.home.SetHomeCommand;
import team.unnamed.inject.AbstractModule;

public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        multibind(CommandClass.class).asSet()
                .to(HomeCommand.class)
                .to(SetHomeCommand.class)
                .to(MainCommand.class)
                .to(DeleteHomeCommand.class);
    }
}

