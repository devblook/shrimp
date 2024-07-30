package team.devblook.shrimp.command.part;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.commandflow.CommandContext;
import team.unnamed.commandflow.annotated.part.PartFactory;
import team.unnamed.commandflow.bukkit.BukkitCommonConstants;
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

        final CommandSender sender = context.getObject(CommandSender.class, BukkitCommonConstants.SENDER_NAMESPACE);

        if (!(sender instanceof Player player)) {
          throw new ArgumentParseException("Only players can have homes");
        }

        final UserModel user = userRepository.get(player.getUniqueId()
                                                    .toString());

        if (user == null) {
          throw new ArgumentParseException("User not found");
        }

        if (stack.hasNext()) {
          final String prefix = stack.next();

          final Set<HomeModel> homes = user.getHomes();

          if (homes.isEmpty()) {
            return Collections.emptyList();
          }

          return homes.stream()
                   .filter(home -> home.getName()
                                     .equals(prefix))
                   .toList();
        }

        return Collections.emptyList();
      }

      @Override
      public String getName() {
        return "home-part";
      }

      @Override
      public List<String> getSuggestions(final CommandContext commandContext, final ArgumentStack stack) {
        final List<String> suggestions = new ArrayList<>();

        final CommandSender sender = commandContext.getObject(
          CommandSender.class,
          BukkitCommonConstants.SENDER_NAMESPACE);

        if (!(sender instanceof Player player)) {
          throw new ArgumentParseException("Only players can have homes");
        }

        final UserModel user = userRepository.get(player.getUniqueId()
                                                    .toString());

        if (user == null) {
          return Collections.emptyList();
        }

        if (!stack.hasNext()) {
          return Collections.emptyList();
        }

        final String prefix = stack.next();
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
