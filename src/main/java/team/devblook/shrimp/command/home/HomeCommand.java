package team.devblook.shrimp.command.home;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.home.HomePosition;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(
value = "home",
alias = {"home", "h"}
)
@Permission("shrimp.home")
public class HomeCommand extends BaseCommand {

  @Inject
  private UserHandler userHandler;
  @Inject
  @Named("messages")
  private BukkitConfiguration messages;


  @Default//@Command(names = "")
  public void mainCommand(Player player, @Suggestion("homes") String nameHome) {

    User user = userHandler.get(player.getUniqueId().toString());

    if (user == null) {
      throw new IllegalStateException("User is null");
    }

    if (nameHome.isEmpty()) {
      player.sendMessage(messages.getMessage("empty-name-homes"));
      return;
    }

    if (!user.hasHome(nameHome)) {
      player.sendMessage(messages.getMessage("home-dont-exist"));
      return;
    }

    Home home = user.home(nameHome);

    if (home == null) {
      throw new IllegalStateException("Home is null");
    }

    Location location = HomePosition.Positions.toLocation(home.position());
    player.teleportAsync(location);

    Component component = messages.getMessage("teleport-to-home").replaceText(builder -> builder.match("%home%").replacement(nameHome));
    player.sendMessage(component);

  }
}

