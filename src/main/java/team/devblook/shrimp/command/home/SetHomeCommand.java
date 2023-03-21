package team.devblook.shrimp.command.home;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Join;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(
value = "sethome",
alias = {"sethome", "sh"}
)
@Permission("shrimp.sethome")
public class SetHomeCommand extends BaseCommand {

  @Inject
  private BukkitConfiguration settings;
  @Inject
  private UserHandler userHandler;
  @Inject
  @Named("messages")
  private BukkitConfiguration messages;



  @Default
  public void homeCommandEmpty(Player player, @Join(",") String nameHome) {
    FileConfiguration settingsConfig = settings.get();

    if (nameHome.isEmpty() || nameHome.equals(" ")) {
      player.sendMessage(messages.getMessage("set-home-empty-name"));
      return;
    }

    int maxHomeAmount = settingsConfig.getInt("max-home-amount");
    User user = userHandler.get(player.getUniqueId().toString());

    if (user == null) {
      throw new IllegalStateException("User is null");
    }

    if (user.sizeHomes() >= maxHomeAmount) {
      Component component = messages.getMessage("limit-homes");
      player.sendMessage(component);
      return;
    }

    if (user.hasHome(nameHome)) {
      Component component = messages.getMessage("equals-homes-name")
      .replaceText(builder -> builder.match("%name%").replacement(nameHome));
      player.sendMessage(component);
      return;
    }

    user.addHome(nameHome, player.getLocation());
    userHandler.update(user);
    Component home = messages.getMessage("create-home").replaceText(builder -> builder.match("%name%").replacement(nameHome));
    player.sendMessage(home);
  }


}
