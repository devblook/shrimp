package team.devblook.shrimp.command;

import org.bukkit.command.CommandSender;
import team.devblook.shrimp.BukkitConfiguration;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Named;

@Command(names = "shrimp", permission = "shrimp.command.admin")
public class MainCommand implements CommandClass {

  @Inject
  @Named("messages")
  private BukkitConfiguration messagesFile;

  @Inject
  private BukkitConfiguration configurationFile;

  @Command(names = "", permission = "shrimp.command.admin")
  public void help(CommandSender sender) {
    this.messagesFile.getColoredComponents("administrator.help-messages")
      .forEach(sender::sendMessage);
  }

  @Command(names = "reload", permission = "shrimp.command.admin.reload")
  public void reload(CommandSender sender) {
    this.messagesFile.reload();
    this.configurationFile.reload();
    sender.sendMessage(this.messagesFile.getColoredComponent("administrator.reload"));
  }
}
