package team.devblook.shrimp.command.part;

import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.commandflow.CommandContext;
import team.unnamed.commandflow.annotated.part.PartFactory;
import team.unnamed.commandflow.exception.ArgumentParseException;
import team.unnamed.commandflow.part.ArgumentPart;
import team.unnamed.commandflow.part.CommandPart;
import team.unnamed.commandflow.stack.ArgumentStack;
import team.unnamed.inject.Inject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HomeListPart implements PartFactory {

  private final TypeRepository<UserModel> userRepository;

  @Inject
  public HomeListPart(final TypeRepository<UserModel> userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CommandPart createPart(final String name, final List<? extends Annotation> modifiers) {
    return new ArgumentPart() {
      @Override
      public List<?> parseValue(final CommandContext context, final ArgumentStack stack, final CommandPart caller)
        throws ArgumentParseException {

        if (stack.hasNext()) {
          final String name = stack.next();
          final UserModel user = userRepository.get(name);

          if (user == null) {
            throw new ArgumentParseException("User not found");
          }

          return List.of(user);
        }

        return List.of();
      }

      @Override
      public String getName() {
        return "";
      }

      @Override
      public List<String> getSuggestions(final CommandContext commandContext, final ArgumentStack stack) {
        final List<String> suggestions = new ArrayList<>();

        if (!stack.hasNext()) {
          return Collections.emptyList();
        }

        final String prefix = stack.next();
        final UserModel user = userRepository.get(prefix);

        if (user == null) {
          return Collections.emptyList();
        }

        final Set<HomeModel> homes = user.getHomes();

        if (homes.isEmpty()) {
          return Collections.emptyList();
        }

        homes.forEach(home -> {
          if (home.getName()
                .startsWith(prefix)) {
            suggestions.add(home.getName());
          }
        });
        return suggestions;
      }
    };
  }
}
