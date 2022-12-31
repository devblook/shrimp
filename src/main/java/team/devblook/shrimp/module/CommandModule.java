package team.devblook.shrimp.module;

import me.fixeddev.commandflow.annotated.CommandClass;
import team.devblook.shrimp.command.ShrimpCommand;
import team.unnamed.inject.AbstractModule;

public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        multibind(CommandClass.class).asSet()
                .to(ShrimpCommand.class);
    }
}

