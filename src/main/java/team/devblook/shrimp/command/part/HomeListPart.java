package team.devblook.shrimp.command.part;

import team.unnamed.commandflow.CommandContext;
import team.unnamed.commandflow.exception.ArgumentParseException;
import team.unnamed.commandflow.part.ArgumentPart;
import team.unnamed.commandflow.part.CommandPart;
import team.unnamed.commandflow.stack.ArgumentStack;

import java.util.List;

public class HomeListPart implements ArgumentPart {
  @Override
  public List<?> parseValue(
    final CommandContext commandContext,
    final ArgumentStack argumentStack,
    final CommandPart commandPart
  ) throws ArgumentParseException {
    return List.of();
  }

  @Override
  public String getName() {
    return "";
  }
}
