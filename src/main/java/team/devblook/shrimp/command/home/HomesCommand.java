package team.devblook.shrimp.command.home;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitAudience;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Command(
value = "homes",
alias = {"homes"}
)
@Permission("shrimp.homes")
public class HomesCommand extends BaseCommand {
  @Inject
  private UserHandler userHandler;
  @Inject
  @Named("messages")
  private BukkitConfiguration messages;

  @Inject
  private Shrimp plugin;

  @Default
  public void getHomesListCommand(Player player) {
    User user = userHandler.get(player.getUniqueId().toString());
    BukkitAudience playerAudience = new BukkitAudience(plugin);
    if (user == null) {
      throw new IllegalStateException("User is null");
    }
    Collection<Home> homeCollection = user.homes();
    if (homeCollection.isEmpty()) {
      playerAudience.adventure().player(player).sendMessage(messages.getMessage("list-homes-empty"));
      return;
    }
    Component component = messages.getMessage("homes");
    for (Home home : homeCollection) {
      Component homeComponent = Component
      .text(home.name() + "  ")
      .color(NamedTextColor.AQUA)
      .decoration(TextDecoration.ITALIC, false)
      .hoverEvent(HoverEvent.showText(messages.getMessage("click-to-teleport")))
      .clickEvent(ClickEvent.runCommand("/shrimp:home " + home.name()));
      component = component.append(homeComponent);
    }
    playerAudience.adventure().player(player).sendMessage(component);
  }
}
