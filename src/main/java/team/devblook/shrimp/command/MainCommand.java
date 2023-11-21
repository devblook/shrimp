package team.devblook.shrimp.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@InjectAll
@Command(
        value = "shrimp",
        alias = {"shrimp"}
)
@Permission("shrimp.op")
public class MainCommand extends BaseCommand {

  private BukkitConfiguration settings;
  @Named("messages")
  private BukkitConfiguration messages;

  @SubCommand("reload")
  public void reloadCommand(Player player) {
    this.settings.reload();
    this.messages.reload();
    player.sendMessage("Reloaded...");
  }

  @SubCommand("help")
  public void helpCommand(Player player) {
    List<String> help = new ArrayList<>(this.messages.get().getStringList("help-messages"));
    for (String message : help) {
      player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

  }
}
