package team.devblook.shrimp.command;

import org.bukkit.command.CommandSender;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;

@Command(names = "shrimp")
public class MainCommand implements CommandClass {

  @Command(names = "reload")
  public void reload(CommandSender sender) {
    sender.sendMessage("Reloading...");
  }
}
