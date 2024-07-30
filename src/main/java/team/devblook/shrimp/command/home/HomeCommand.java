package team.devblook.shrimp.command.home;

import org.bukkit.command.CommandSender;
import team.devblook.shrimp.model.HomeModel;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;
import team.unnamed.commandflow.annotated.annotation.Sender;

@Command(names = "home")
public class HomeCommand implements CommandClass {

  @Command(names = "")
  public void home(@Sender CommandSender sender, HomeModel home) {
    sender.sendMessage("Teleporting to home " + home.getName());
  }
}
