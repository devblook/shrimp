package team.devblook.shrimp.service;


import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.user.UserHandler;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandService implements Service {
  @Inject
  private Set<BaseCommand> commands;

  @Inject
  private Shrimp plugin;

  @Inject
  private UserHandler userHandler;

  @Override
  public void start() {
    BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(plugin);
    manager.registerSuggestion(SuggestionKey.of("homes"), (context, input) ->
    userHandler.get(Bukkit.getPlayerExact(context.getName()).getUniqueId().toString())
    .homes()
    .stream()
    .map(Home::name)
    .collect(Collectors.toList()));
    commands.forEach(manager::registerCommand);
  }


}
