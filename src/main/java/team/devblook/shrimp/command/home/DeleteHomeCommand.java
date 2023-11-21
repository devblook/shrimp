package team.devblook.shrimp.command.home;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(
        value = "deletehome",
        alias = {"deletehome", "delhome", "dh"}
)
@Permission("shrimp.deletehome")
public class DeleteHomeCommand extends BaseCommand {

  @Inject
  private UserHandler userHandler;
  @Inject
  @Named("messages")
  private BukkitConfiguration messages;


  @Default
  public void deleteHome(Player player, @Suggestion("homes") String nameHome) {
    User user = this.userHandler.get(player.getUniqueId().toString());

    if (user == null) {
      throw new IllegalStateException("User is null");
    }

    if (!user.hasHome(nameHome)) {
      Component component = this.messages.getMessage("home-dont-exist")
              .replaceText(builder -> builder.match("%home%").replacement(nameHome));
      player.sendMessage(component);

      return;
    }

    user.removeHome(nameHome);
    this.userHandler.update(user);
    Component deteleComponent = this.messages.getMessage("delete-home")
            .replaceText(builder -> builder.match("%home%").replacement(nameHome));
    player.sendMessage(deteleComponent);
  }


}
